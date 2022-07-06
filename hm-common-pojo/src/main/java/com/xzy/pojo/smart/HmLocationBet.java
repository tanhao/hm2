package com.xzy.pojo.smart;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "hm_location_bet")
public class HmLocationBet implements Serializable {
    @Id
    private Integer planId;
    private Integer lotteryId;
    private Integer userId;
    private String betModel;
    private String wlChoice;
    private String fixedContent;
}
