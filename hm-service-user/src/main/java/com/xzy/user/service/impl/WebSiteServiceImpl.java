package com.xzy.user.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.pojo.user.HmWebSite;
import com.xzy.pojo.user.code.UcenterCode;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.user.WebSiteService;
import com.xzy.user.dao.UserMapper;
import com.xzy.user.dao.WebSiteMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class WebSiteServiceImpl implements WebSiteService {

    @Autowired
    WebSiteMapper webSiteMapper;
    @Autowired
    UserMapper userMapper;

    public HmWebSite getCompanyName(Integer id) {
        return webSiteMapper.selectByPrimaryKey(id);
    }

    public HmWebSite selectByPrimaryKey(Integer id) {
        return webSiteMapper.selectByPrimaryKey(1);
    }

    /**
     * 修改公司营业时间
     *
     * @return
     */
    public void updateTime(HmWebSite hmWebSite, UserLogin userLogin) {
        HmWebSite webSite = userMapper.getHmConfigUserId(userLogin.getParentId());
        if (webSite != null) {//查询是后桶数据
            if (hmWebSite.getStartTime() != null) {
                Integer start = hmWebSite.getStartTime().compareTo(webSite.getStartTime());
                Integer end = hmWebSite.getEndTime().compareTo(webSite.getEndTime());
                if (start < 0) {//小于0 是7点之前的日期
                    ExceptionCast.cast(CommonCode.START_TIME_CLOSED);
                }
                if (end > 0) {//小于0 是6点之前的日期
                    ExceptionCast.cast(CommonCode.END_TIME_CLOSED);
                }
            }
            webSiteMapper.updateByPrimaryKeySelective(hmWebSite);
        } else {
            ExceptionCast.cast(CommonCode.SERVER_ERROR);
        }

    }


    /**
     * 按SITE_CODE 查询
     *
     * @param siteCode
     * @param type
     * @return
     */
    public JSONArray selSiteCode(String siteCode, String type) {
        HmWebSite webSite = webSiteMapper.selectSiteCode(siteCode);
        if (null == webSite) {
            ExceptionCast.cast(UcenterCode.UCENTER_SITE_CODE_ERROR);
        }
        JSONArray jsonArray = new JSONArray();
        JSONArray sites = JSONArray.parseArray(webSite.getSiteUrls());
        for (Object object : sites) {
            JSONObject json = (JSONObject) object;
            if ("MEMBER".equals(type) || "ALL".equals(type)) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("type", "会员PC");
                jsonObject1.put("url", json.get("memberPcUrl"));
                jsonArray.add(jsonObject1);
            }
            if ("H5".equals(type) || "ALL".equals(type)) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("type", "会员H5");
                jsonObject1.put("url", json.get("memberH5Url"));
                jsonArray.add(jsonObject1);
            }
            if ("AGENT".equals(type) || "ALL".equals(type)) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("type", "代理PC");
                jsonObject1.put("url", json.get("agentPcUrl"));
                jsonArray.add(jsonObject1);
            }
        }
        return jsonArray;
    }


    public HmWebSite selectByCode(String code) {
        HmWebSite hmWebSite = new HmWebSite();
        hmWebSite.setSiteCode(code);
        return webSiteMapper.selectOne(hmWebSite);
    }
}
