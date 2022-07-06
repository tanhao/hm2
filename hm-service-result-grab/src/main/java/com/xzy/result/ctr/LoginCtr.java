package com.xzy.result.ctr;

import cn.hutool.core.util.ObjectUtil;
import com.xzy.result.service.UserService;
import com.xzy.result.util.ResultJson;
import com.xzy.result.vo.UserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginCtr {


    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public ResultJson login(@RequestBody Map maps,HttpSession session) throws Exception{
        String username = maps.get("username").toString();
        String password = maps.get("password").toString();
        String md5Pwd = DigestUtils.md5Hex(password.getBytes());
        UserVo userVo =  userService.findByUser(username);
        if(ObjectUtil.isNull(userVo)){
            return ResultJson.ERROR( "帐号或者密码错误！！！");
        }
        if(ObjectUtil.equal(username,userVo.getUserName())&&ObjectUtil.equal(md5Pwd,userVo.getUserPwd())) {
            session.setAttribute("user", username);
            return ResultJson.SUCCESS(userVo);
        }else {
            return ResultJson.ERROR("帐号或者密码错误！！！");
        }
    }

    @RequestMapping("/logout")
    public ResultJson logout() {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return ResultJson.SUCCESS( "登出成功");
    }

    @RequestMapping("/info")
    public ResultJson getLoginUser() {
        Object user = request.getSession().getAttribute("user");
        UserVo userVo =  userService.findByUser(user.toString());
        return ResultJson.SUCCESS(userVo);
    }

    @RequestMapping("/change")
    public ResultJson saveUser(@RequestBody UserVo addUser) {
        if(ObjectUtil.isNull(addUser.getId())){
            UserVo getUser = userService.findByUser(addUser.getUserName());
            if(ObjectUtil.isNotNull(getUser)) return ResultJson.ERROR("用户名已存在，请修改用户名 ！");
            addUser.setUserPwd(DigestUtils.md5Hex(addUser.getUserPwd().getBytes()));
            userService.addUser(addUser);
            return ResultJson.SUCCESS("添加成功");
        }
        UserVo updateUser = userService.findByUserId(addUser.getId());
        if(!addUser.getUserName().equals(updateUser.getUserName())){
            UserVo getUser = userService.findByUser(addUser.getUserName());
            if(ObjectUtil.isNotNull(getUser)) return ResultJson.ERROR("用户名已存在，用户名 ！");
        }
        if(!addUser.getUserPwd().equals(updateUser.getUserPwd())){
            addUser.setUserPwd(DigestUtils.md5Hex(addUser.getUserPwd().getBytes()));
            addUser.setPwdInit(0);
        }
        addUser.setPwdInit(1);
        userService.updateUser(addUser);
        return ResultJson.SUCCESS("修改成功");
    }

    @RequestMapping("/list")
    public ResultJson userList() {
        Object user = request.getSession().getAttribute("user");
        userService.findByList(user.toString());
        return ResultJson.SUCCESS(userService.findByList(user.toString()));
    }

    @PutMapping("/change/pwd")
    public ResultJson changePassword(@RequestBody Map<String, Object> map){
        String oldPwd = DigestUtils.md5Hex(map.get("oldPwd").toString());
        Integer userId = Integer.parseInt(map.get("id").toString());
        UserVo updateUser = userService.findByUserId(userId);
        if(!oldPwd.equals(updateUser.getUserPwd())){
            return ResultJson.ERROR(" 原始密码错误 ！");
        }
        String newPwd = DigestUtils.md5Hex(map.get("newPwd").toString());
        updateUser.setUserPwd(newPwd);
        userService.updateUser(updateUser);
        return ResultJson.SUCCESS("修改成功");
    }

}
