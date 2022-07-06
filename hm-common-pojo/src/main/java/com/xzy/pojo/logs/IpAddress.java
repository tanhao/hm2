package com.xzy.pojo.logs;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "t_ipaddress",schema = "hm_logs")
public class IpAddress implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String ip;
    private String address;
}
