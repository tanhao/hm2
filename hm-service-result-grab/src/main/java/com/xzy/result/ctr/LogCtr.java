package com.xzy.result.ctr;


import com.xzy.result.service.ResultLogService;
import com.xzy.result.util.ResultJson;
import com.xzy.result.vo.ResultLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/log")
public class LogCtr {

    @Autowired
    private ResultLogService resultLogService;

    @GetMapping("/list/{day}")
    public ResultJson getResultLogList(@PathVariable("day") String day){
        List<ResultLogVo> resultLogVoList = resultLogService.findResultLogByDay(day);
        return ResultJson.SUCCESS(resultLogVoList);

    }

}
