package com.xzy.pojo.ctrl;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserNoticeDO  implements Serializable {
    protected Integer id;
    protected Integer userId;
    protected Integer startTime;
    protected Integer endTime;
    protected String content;
    protected Boolean isAlert;
    protected Boolean isAgent;
    protected Boolean isMember;
    protected Boolean isOpen;
    protected Integer ordering;
}