package com.xzy.common.model.response;

import lombok.Data;

import java.util.List;

/**
 * 集合类型数据及总条数
 * @param <T>
 */
@Data
public class QueryResult<T> {
    //数据列表
    private List<T> list;
    //数据总数
    private long total;
}
