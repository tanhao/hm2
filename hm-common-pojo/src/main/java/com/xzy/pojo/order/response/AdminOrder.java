package com.xzy.pojo.order.response;

import com.xzy.pojo.order.HmOrder;
import lombok.Data;

@Data
public class AdminOrder extends HmOrder {
    private String ticketDetail;
}
