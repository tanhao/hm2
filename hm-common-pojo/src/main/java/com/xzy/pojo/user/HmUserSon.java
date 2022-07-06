package com.xzy.pojo.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "hm_user_son")
public class HmUserSon  implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer sonId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer primaryId;
    private String username;
    private String status;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String nickName;
    private Integer sonLevel;
    private Date created;
    private Date updated;
    private String loginIpBound;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer companyId;
    private Integer resetPassword;
    @Transient
    private List<Integer> menus;
    private Integer passwordError;
    private Integer countError;
    private Integer unlockCount;
}
