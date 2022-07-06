package com.xzy.result.service;


import cn.hutool.core.util.ObjectUtil;
import com.xzy.result.dao.LinkMapper;
import com.xzy.result.util.DateUtil;
import com.xzy.result.vo.LinkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LinkService {

    @Autowired
	LinkMapper linkMapper;

	public List<LinkVo> findLinkByAll() {
		return linkMapper.selectAll();
	}

	public void saveLinuxKey(String k) {
		LinkVo linkVo = new LinkVo();
		linkVo.setLinkKey(k);
		LinkVo selectOne = linkMapper.selectOne(linkVo);
		if(ObjectUtil.isNull(selectOne)){
			String date = DateUtil.format(new Date(),DateUtil.DATEFORMATMINUTE);
			linkVo.setLinkName("未命名_"+date);
			linkMapper.insertSelective(linkVo);
		}
	}
}
