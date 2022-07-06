package com.xzy.user.util;


import com.xzy.common.exception.ExceptionCast;
import com.xzy.user.dao.UserMapper;
import com.xzy.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VerifyOrderUtil {

    @Autowired
    UserMapper userMapper;

    public void verifyToBet(Integer userId){
        Integer num =userMapper.verifyToBet(userId,DateUtil.getAccountDay());
        if(num >= 1){
            ExceptionCast.cast("当天下綫有注單不能更改！");
        }
    }


}
