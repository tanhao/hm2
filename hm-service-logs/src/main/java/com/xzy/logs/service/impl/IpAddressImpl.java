package com.xzy.logs.service.impl;

import com.xzy.logs.IpaddressService;
import com.xzy.logs.dao.IpAddressMapper;
import com.xzy.pojo.logs.IpAddress;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@DubboService(interfaceClass = IpaddressService.class)
public class IpAddressImpl implements IpaddressService {

    @Autowired
    IpAddressMapper mapper;


    public List<IpAddress> selectByIps(String[] ips){
        return mapper.selectByIps(ips);
    }


}
