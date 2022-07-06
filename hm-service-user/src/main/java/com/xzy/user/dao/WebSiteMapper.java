package com.xzy.user.dao;

import com.xzy.pojo.user.HmUserSon;
import com.xzy.pojo.user.HmWebSite;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface WebSiteMapper extends Mapper<HmWebSite> {

    @Select("SELECT * from hm_website where site_code=#{siteCode}")
    HmWebSite selectSiteCode(@Param("siteCode") String siteCode);

}
