package com.xzy.result.ctr;


import com.xzy.result.service.LotteryService;
import com.xzy.result.util.ResultJson;
import com.xzy.result.vo.LotteryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/lot")
public class LotteryCtr {


    @Autowired
    private LotteryService lotteryService;

    /**
     * 修改排序
     * @param
     * @return
     */
    @PutMapping("/change")
    public ResultJson changeLotterySort(@RequestBody LotteryVo lotteryVo){
        return ResultJson.SUCCESS(lotteryService.updateLotterySort(lotteryVo));
    }


}
