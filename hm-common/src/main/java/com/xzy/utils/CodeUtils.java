package com.xzy.utils;

import com.xzy.common.model.response.ResultCode;

import java.util.Arrays;

public class CodeUtils {
    public static <T extends ResultCode> T getByCode(Class<T> enumClass, int code) {
        return Arrays.asList(enumClass.getEnumConstants()).stream().filter(enumItem->enumItem.code()==code).findFirst().get();
    }

}
