package com.xzy.pojo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xzy.enums.UserEnums;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="hm_user")
public class HmUser implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userId;
    @JsonIgnore
    private String password;
    private String username;
    private String parentPath;
    private String parentName;
    private Integer parentId;
    private String nickName;
    private Integer userLevel;
    private String userType;
    private String status;
    private Date created;
    private Date updated;
    private String loginIpBound;
    private Integer oddsAgentId;
    private Integer companyId;
    private String secondary;
    private Integer resetPassword;
    private Boolean disk;
    private Boolean bhStatus;
    private UserEnums.JumpType jumpType;
    private Integer passwordError;
    private Integer countError;
    private Integer unlockCount;

}
