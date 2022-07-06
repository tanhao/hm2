package com.xzy.common.model.response;

import lombok.Data;

/**
 * 集合类型返回格式
 */
@Data
public class QueryResponseResult<T> extends ResponseResult {

    QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode, QueryResult queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }

}
