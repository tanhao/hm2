package com.xzy.logs.util;

import com.xzy.logs.dao.IpAddressMapper;
import com.xzy.pojo.logs.IpAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@Lazy(false)
@EnableAsync
@EnableScheduling
public class StatsSchedule {

    @Autowired
    private IpAddressMapper ipAddressMapper;
    @Autowired
    IPUtil ipUtil;
    //每10秒执行一次
    @Scheduled(fixedDelay = 20000)
    public void excute() {
        List<IpAddress> ipList = ipAddressMapper.selectAddressIsNull();
        if(ipList.size()>0){
            for (IpAddress ipaddress : ipList){
                ipaddress.setAddress(ipUtil.getIpRegion(ipaddress.getIp()));
                ipAddressMapper.updateIpAddress(ipaddress);
            }
        }
    }

}
