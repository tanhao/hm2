package com.xzy.result.ctr;


import com.xzy.result.util.RedisUtil;
import com.xzy.result.service.LinkService;
import com.xzy.result.util.Constants;
import com.xzy.result.util.ResultJson;
import com.xzy.result.vo.RedisThreadVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RestController
@RequestMapping("/thread")
public class RedisCtr {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private LinkService linkService;
    /**
     * 网址抓取网址的key
     * @param
     * @return
     */
    @GetMapping("/list")
    public ResultJson getThreadList(){
        List<Object> listIps = redisUtil.getIps();
        LinkedHashMap<String,Map<String, Object>> mapOut = new LinkedHashMap<>();

        Map<String,Object> map168 = redisUtil.getKeyAndValue("*:"+Constants.kai_168+":*");
        Map<String,Object> map163 = redisUtil.getKeyAndValue("*:"+Constants.kai_163+":*");
        Map<String,Object> mapZkc = redisUtil.getKeyAndValue("*:"+Constants.hm_zkc+":*");
//        Map<String,Object> mapkcw = redisUtil.getKeyAndValue("*:"+Constants.kcw+":*");
//        Map<String,Object> mapcp = redisUtil.getKeyAndValue("*:"+Constants.kai_cp+":*");
//        Map<String,Object> mapboyi = redisUtil.getKeyAndValue("*:"+Constants.bo_yi+":*");

        mapOut.put(Constants.kai_168,map168);
        mapOut.put(Constants.kai_163,map163);
        mapOut.put(Constants.hm_zkc,mapZkc);
//        mapOut.put(Constants.kcw,mapkcw);
//        mapOut.put(Constants.kai_cp,mapcp);
//        mapOut.put(Constants.bo_yi,mapboyi);

        Map<String,HashMap<Object,ArrayList<RedisThreadVo>>> mapNew = new LinkedHashMap<>();
        mapOut.forEach((k,v)->{
            LinkedHashMap<Object,ArrayList<RedisThreadVo>> objectMap =new LinkedHashMap<>();
            for (Map.Entry<String, Object> map:v.entrySet()) {
                String[] strings = map.getKey().split(":");
                RedisThreadVo redisThreadVo = new RedisThreadVo();
                redisThreadVo.setIpKey(strings[0]);
                redisThreadVo.setLotteryKey(strings[2]);
                redisThreadVo.setStatus(Integer.parseInt(map.getValue().toString()));
                if (objectMap.containsKey(strings[2])) {
                    objectMap.get(strings[2]).add(redisThreadVo);
                } else {
                    ArrayList<RedisThreadVo> list = new ArrayList<>();
                    list.add(redisThreadVo);
                    objectMap.put(strings[2],list);
                }
            }
            mapNew.put(k,objectMap);
        });
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("newMap",mapNew);
        objectMap.put("ips",listIps);
        return ResultJson.SUCCESS(objectMap);

    }

    private ExecutorService exec = Executors.newCachedThreadPool();

    /**
     * 启动或者暂停结果抓取线程
     * @param
     * @return
     */
    @PutMapping("/change")
    public ResultJson startThreadLotteryKey(@RequestBody Map map){
        String linkHttps = map.get("link").toString();
        String ip = map.get("ip").toString();
        String lotteryKey = map.get("lotteryKey").toString();
        Integer changeStatus = Integer.parseInt(map.get("status").toString());
        String redisKey = ip+":"+linkHttps+":"+lotteryKey;
        Integer runStatus = Integer.parseInt(redisUtil.getKey(redisKey).toString());
        //如果线程正在运行 则不能直接修改为2，如果线程正在休眠，休眠后执行重启或者关闭。
        if(changeStatus==5){
            redisUtil.getAndSetValue(redisKey,5);
        }else{
            if(runStatus==1){
                redisUtil.getAndSetValue(redisKey,0);
            }else{
                redisUtil.getAndSetValue(redisKey,changeStatus);
            }
        }
        return ResultJson.SUCCESS(map);
    }

    /**
     * 保存 key集合
     * @param
     * @return
     */
    @GetMapping("/ip/key")
    public void getLinuxKeyList(){
        List<Object> listIps = redisUtil.getIps();
        listIps.forEach(key->{
            linkService.saveLinuxKey(key.toString());
        });
    }

}
