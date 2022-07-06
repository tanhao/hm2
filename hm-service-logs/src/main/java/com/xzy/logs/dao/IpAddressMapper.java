package com.xzy.logs.dao;

import com.xzy.pojo.logs.IpAddress;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface IpAddressMapper extends Mapper<IpAddress> {

    List<IpAddress> selectByIps(@Param("items") String[] items);

    Integer updateIpAddress(IpAddress ipaddress);

    List<IpAddress> selectAddressIsNull();
}
