package com.xzy.result.util;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回json格式封装
 */
@Data
@NoArgsConstructor
public class ResultJson {
    //操作是否成功
    boolean success = true;
    //操作时间
    long serverTime = System.currentTimeMillis();
    //返回数据
    Object data;

    public ResultJson(Boolean suc, Object d) {
        this.success = suc;
        this.data = d;
    }

    public static ResultJson SUCCESS() {
        return new ResultJson(true, null);
    }

    public static ResultJson SUCCESS(Object data) {
        return new ResultJson(true, data);
    }

    public static ResultJson SUCCESS_LIST(List list) {
        long total = new PageInfo<>(list).getTotal();
        Map<String, Object> map = new HashMap();
        map.put("dataList", list);
        map.put("total", total);
        return new ResultJson(true, map);
    }

    public static ResultJson ERROR() {
        return new ResultJson(false, null);
    }

    public static ResultJson ERROR(Object data) {
        return new ResultJson(false, data);
    }


    public static ResultJson SUCCESSLIST(Object data) {
        return new ResultJson(true, data);
    }
}
