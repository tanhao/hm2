package com.xzy.pojo.logs.request;

import com.xzy.pojo.logs.HmLogs;
import lombok.Data;

import java.util.List;

@Data
public class HmLogsList {
    List<HmLogs> hmLogs;
}
