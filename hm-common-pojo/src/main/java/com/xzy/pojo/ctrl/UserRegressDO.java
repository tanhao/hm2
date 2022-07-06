package com.xzy.pojo.ctrl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserRegressDO extends UserKindDO  implements Serializable {
    protected Double regressA;
    protected Double regressB;
    protected Double regressC;
    protected Double regressD;
}