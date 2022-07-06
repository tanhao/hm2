package com.xzy.user.controller;


import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.pojo.user.HmWebSite;
import com.xzy.user.UserService;
import com.xzy.user.WebSiteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("website")
@CrossOrigin
public class WebSiteController extends BaseController{

    @DubboReference
    WebSiteService webSiteService;
    @DubboReference
    UserService userService;

    @GetMapping("/sitename")
    public ResponseResult getCompanyName() {
        HmWebSite hmWebSite = webSiteService.getCompanyName(getLoginExt().getCompanyId());
        if (null == hmWebSite || !hmWebSite.getSiteCorp()) {
            hmWebSite = webSiteService.selectByPrimaryKey(0);
        }
        return ResponseData.SUCCESS(hmWebSite.getSiteName());
    }

    /**
     * 查询公司营业时间
     * @return
     */
    @GetMapping("/toStartTime/{userId}")
    public ResponseResult toStartTime(@PathVariable Integer userId) {
        return ResponseData.SUCCESS(userService.toStartTime(userId));
    }

    /**
     * 查询公司营业时间
     * @return
     */
    @PutMapping("/updateTime")
    public ResponseResult toUpdateTime(@RequestBody HmWebSite hmWebSite) {
        hmWebSite.setUserId(getLoginExt().getPrimaryId());
        webSiteService.updateTime(hmWebSite,getLoginExt());
        return ResponseData.SUCCESS();
    }


    /**
     *
     * @param code
     * @param type
     * @return
     */
    @GetMapping("/siteCode/{code}/{type}")
    public ResponseResult selSiteCode(@PathVariable String code,@PathVariable String type){
        return ResponseData.SUCCESS( webSiteService.selSiteCode(code,type));
    }

}
