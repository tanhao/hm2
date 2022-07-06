package com.xzy.smart.vo;

import com.xzy.pojo.smart.HmPlan;
import lombok.Data;

import java.util.List;

@Data
public class SmartVo  extends HmPlan {
    private List<LocationPlanVo> locations;
    private String betModel;
    private String wlChoice;
    private String fixedContent;

}
