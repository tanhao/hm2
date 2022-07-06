package com.xzy.common.model.response;

import com.github.pagehelper.PageInfo;
import com.xzy.common.model.code.CommonCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResponseData extends ResponseResult {
    Object data;

    public ResponseData(ResultCode resultCode, Object object) {
        super(resultCode);
        this.data = object;
    }

    public static ResponseData SUCCESS(Object object) {
        return new ResponseData(CommonCode.SUCCESS, object);
    }

    public static ResponseData SUCCESS(String ksy, Object object) {
        Map<String, Object> map = new HashMap();
        map.put(ksy, object);
        return new ResponseData(CommonCode.SUCCESS, map);
    }
    public static ResponseData ERROR(Object object){
        return new ResponseData(CommonCode.INVALID_PARAM, object);
    }
    public static ResponseData SUCCESS_LIST(List list) {
        long total = new PageInfo<>(list).getTotal();
        Map<String, Object> map = new HashMap();
        map.put("dataList", list);
        map.put("total", total);
        return new ResponseData(CommonCode.SUCCESS, map);
    }
}
