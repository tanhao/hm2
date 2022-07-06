
package com.xzy.pojo.ctrl.vo;

import java.io.Serializable;
import java.util.List;

import com.xzy.pojo.ctrl.UserNoticeDO;

import com.xzy.pojo.ctrl.bo.UserNoticeBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NoticeVO extends UserNoticeDO implements Serializable {
    private static final long serialVersionUID = -8784438271641393111L;
    private Integer page;
    private Integer size;
    private Integer total;
    private List<UserNoticeBO> notices;

}