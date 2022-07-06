package com.xzy.socket.tio;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xzy.ctrl.NowOrderService;
import com.xzy.ctrl.UserNoticeService;
import com.xzy.pojo.ctrl.vo.NowOrderVO;
import com.xzy.pojo.tio.entity.SendInfo;
import com.xzy.pojo.tio.entity.TIOUser;
import com.xzy.pojo.tio.utils.CodeUtil;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.socket.TioSocketService;
import com.xzy.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class MyWebSocketMsgHandler implements IWsMsgHandler {
    @Resource
    @Qualifier(value = "LoginUtil")
    private LoginUtil loginUtil;
    @DubboReference
    UserNoticeService userNoticeService;
    @Autowired
    TioSocketService tioSocketService;
    @DubboReference
    private NowOrderService nowOrderService;

    private final String TIO_USER="TIO_USER";
    private final String LAST_SELECT_LOTTERY="LAST_SELECT_LOTTERY";


    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) {
        String uid = httpRequest.getParam("uid");
        try {
            if (StrUtil.isNotBlank(uid)) {
                String loginExt = loginUtil.selectToken(uid);
                if (StrUtil.isNotBlank(loginExt)) {
                    UserLogin userLogin = JSON.parseObject(loginExt, UserLogin.class);
                    //绑定用户
                    Tio.bindUser(channelContext, userLogin.getUserId().toString());
                    Tio.bindToken(channelContext, uid);

                    TIOUser user=new TIOUser();
                    user.setUserId(userLogin.getUserId());
                    user.setUsername(userLogin.getUsername());
                    user.setUserType(userLogin.getUserType());
                    user.setPrimaryId(userLogin.getPrimaryId());
                    List<String> ids=Arrays.asList( StrUtil.format("{},{}",StrUtil.sub(userLogin.getParentPath(),1,-1),userLogin.getPrimaryId()).split(","));
                    user.setUserIds(ids);
                    if("MEMBER".equals(userLogin.getUserType())){
                        user.setMarket(userLogin.getMarketOpen());
                    }
                    channelContext.setAttribute(TIO_USER,user);
                    log.info("[{}] {} userId:{},primaryId:{},ids:{} 链接成功...",userLogin.getUserType(),userLogin.getUsername(),user.getUserId(), StringUtils.join(ids,":"));
                    return httpResponse;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpResponse.setStatus(HttpResponseStatus.getHttpStatus(401));
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) {

    }

    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) {
        return null;
    }

    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) {
        TIOUser tuser=(TIOUser)channelContext.getAttribute(TIO_USER);
        log.info("[{}] {} 断开链接...",tuser.getUserType(),tuser.getUsername());
        return null;
    }

    /**
     * 接受到的信息
     * @param wsRequest
     * @param text
     * @param channelContext
     * @return
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) {
        try {
            SendInfo sendInfo = JSON.parseObject(text, SendInfo.class);
            TIOUser user=null;
            String groupName=null;
            JSONObject json=null;
            switch  (sendInfo.getCode()){
                case CodeUtil.MSG_PING:
                    Tio.send(channelContext, wsResponse(text));
                    break;
                case CodeUtil.MSG_ODDS_LOTTERY:
                    json=(JSONObject)sendInfo.getContent();
                    Integer newLotteryId=json.getInteger("lotteryId");
                    String newLotteryKey=json.getString("lotteryKey");
                    user=(TIOUser)channelContext.getAttribute(TIO_USER);
                    Long tsl=(Long)channelContext.getAttribute(LAST_SELECT_LOTTERY);
                    Long now=System.currentTimeMillis();
                    if(tsl!=null&&now-tsl<1000&&user.getLotteryId()!=null&&newLotteryId.equals(user.getLotteryId())){
                        log.info("===>>>select lottery break:{}<<<===",newLotteryId);
                        break;
                    }
                    channelContext.setAttribute(LAST_SELECT_LOTTERY,now);
                    if("AGENT".equals(user.getUserType())){
                        String market=json.getString("market");
                        Integer userId=json.getInteger("userId");
                        String buhuo=json.getString("buhuo");
                        NowOrderVO nowOrderVO=  nowOrderService.getNowOrderVO(user.getPrimaryId(),userId,newLotteryId,market,null,buhuo);
                        user.setMarket(nowOrderVO.getMarket());
                        newLotteryId=nowOrderVO.getLottery().getLotteryId();
                        newLotteryKey=nowOrderVO.getLottery().getLotteryKey();
                        SendInfo memberInfo=new SendInfo(CodeUtil.MSG_ODDS_INIT, nowOrderVO);
                        Tio.send(channelContext, wsResponse(JSON.toJSONString(memberInfo, SerializerFeature.WriteNonStringKeyAsString)));
                    }else if("MEMBER".equals(user.getUserType())){
                        NowOrderVO nowOrderVO=  nowOrderService.getFrontOdds(user.getUserId(),newLotteryId);
                        newLotteryId=nowOrderVO.getLottery().getLotteryId();
                        newLotteryKey=nowOrderVO.getLottery().getLotteryKey();
                        SendInfo memberInfo=new SendInfo(CodeUtil.MSG_ODDS_INIT, nowOrderVO);
                        Tio.send(channelContext, wsResponse(JSON.toJSONString(memberInfo,SerializerFeature.WriteNonStringKeyAsString)));
                    }

                    //退出所有GROUP
                    Tio.unbindGroup(channelContext);
                    user.setLotteryId(newLotteryId);
                    user.setLotteryKey(newLotteryKey);
                    groupName="L"+newLotteryId;
                    log.info("[{}] {} 加入GROUP [ {} ]",user.getUserType(),user.getUsername(),groupName);
                    Tio.bindGroup(channelContext,groupName);

                     groupName="L"+newLotteryKey;
                    log.info("[{}] {} 加入GROUP [ {} ]",user.getUserType(),user.getUsername(),groupName);
                    Tio.bindGroup(channelContext,groupName);

                    for(String userId: user.getUserIds()){
                        groupName= StrUtil.format("{}-{}",user.getLotteryId(),userId);
                        log.info("[{}] {} 加入GROUP [ {} ]",user.getUserType(),user.getUsername(),groupName);
                        Tio.bindGroup(channelContext,groupName);

                        groupName= StrUtil.format("{}-{}-{}",user.getLotteryId(),userId, user.getMarket());
                        log.info("[{}] {} 加入GROUP [ {} ]",user.getUserType(),user.getUsername(),groupName);
                        Tio.bindGroup(channelContext,groupName);
                    }
                    break;
                case CodeUtil.MSG_ODDS_MARKET:
                    user=(TIOUser)channelContext.getAttribute(TIO_USER);
                    if(!"AGENT".equals(user.getUserType())){
                        break;
                    }
                    json=(JSONObject)sendInfo.getContent();
                    String market=json.getString("market");
                    if(StrUtil.isEmpty(market)){
                        break;
                    }
                    for(String userId: user.getUserIds()){
                        groupName= StrUtil.format("{}-{}-{}",user.getLotteryId(),userId, user.getMarket());
                        log.warn("[{}] {} 退出GROUP [ {} ]",user.getUserType(),user.getUsername(),groupName);
                        Tio.unbindGroup(groupName,channelContext);

                        groupName= StrUtil.format("{}-{}-{}",user.getLotteryId(),userId, market);
                        log.info("[{}] {} 加入GROUP [ {} ]",user.getUserType(),user.getUsername(),groupName);
                        Tio.bindGroup(channelContext,groupName);
                    }
                    break;
                case CodeUtil.MSG_ODDS_UNLOTTERY:
                    //退出所有GROUP
                    Tio.unbindGroup(channelContext);
                    /*
                    Integer oldLotteryId=user.getLotteryId();
                    if(oldLotteryId!=null){
                        user.getUserIds().forEach(userId->{
                            String groupName= StrUtil.format("{}-{}-{}",oldLotteryId,userId,user.getMarket());
                            log.warn("[{}] {} 退出GROUP [ {} ]",user.getUserType(),user.getUsername(),groupName);
                            Tio.bindGroup(channelContext,groupName);
                        });
                    }
                     */
                    break;
            }
        }catch (Exception e){
            SendInfo sendInfo = new SendInfo(CodeUtil.MSG_ERROR,StrUtil.format(e.getMessage()));
            Tio.send(channelContext, wsResponse(JSONObject.toJSONString(sendInfo)));
        }
        return null;
    }

    public WsResponse wsResponse(String text){
        WsResponse wsResponse = WsResponse.fromText(text, CharsetUtil.UTF_8);
        return wsResponse;
    }

}
 