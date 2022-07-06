package com.xzy.pojo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "hm_website")
public class HmWebSite   implements Serializable{
    @Id
    private Integer userId;
    private String siteName;
    private String siteCode;
    private String siteUrls;
    private Boolean siteCorp;
    private String startTime;
    private String endTime;

}
