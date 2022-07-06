package com.xzy.pojo.user.request;

import com.xzy.pojo.user.HmUserBase;
import com.xzy.pojo.user.HmUserPrePt;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class AddUserRq extends HmUserBase {
    private String username;
    private String password;
    private String nickName;
    private String loginIpBound;
    private String userType;
    private Integer parentId;
    private String securityCode;
    private String siteName;
    private String secondary;
    private Boolean disk;
    private Boolean bhStatus;
    private List<HmUserPrePt> prePt;

}
