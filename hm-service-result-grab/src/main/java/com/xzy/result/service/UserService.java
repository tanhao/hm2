package com.xzy.result.service;


import cn.hutool.core.util.StrUtil;
import com.xzy.result.dao.UserMapper;
import com.xzy.result.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

;

@Service
public class UserService {

    @Autowired
	UserMapper userMapper;

	public void addUser(UserVo userVo) {
		userMapper.insertSelective(userVo);
	}

	public void updateUser(UserVo userVo) {
		userMapper.updateByPrimaryKeySelective(userVo);
	}

	public UserVo findByUser(String userName) {
		if(StrUtil.isEmpty(userName)){
			return null;
		}
		UserVo init = new UserVo();
		init.setUserName(userName);
		return userMapper.selectOne(init);
	}

	public UserVo findByUserId(Integer id) {
		UserVo init = new UserVo();
		init.setId(id);
		return userMapper.selectOne(init);
	}

	public List<UserVo> findByList(String userName) {
		Example example = new Example(UserVo.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andNotEqualTo("userName", userName);
		return userMapper.selectByExample(example);
	}

}
