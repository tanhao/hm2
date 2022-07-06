package com.xzy.pojo.user;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "hm_user_pre_pt")
public class HmUserPrePt  implements Serializable {
    private Integer userId;
    private Integer lotteryId;
    private Integer ptPct;
    private Integer ptMinPct;
    private Integer ptMaxPct;
    private Boolean ptBh;
    private Integer prePct;
    private Integer preMinPct;
    private Integer preMaxPct;

}
