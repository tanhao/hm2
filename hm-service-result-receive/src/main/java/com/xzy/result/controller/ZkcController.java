package com.xzy.result.controller;

import cn.hutool.core.util.ObjectUtil;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.pojo.result.request.ResultRq;
import com.xzy.result.dao.HmResultMapper;
import com.xzy.result.vo.ZkcVo;
import com.xzy.utils.NumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ZkcController extends BaseController {
    @Autowired
    HmResultMapper hmResultMapper;

    @PostMapping("/zkc")
    public ResponseResult findList(@RequestBody ZkcVo zkcVo) {
        if(!getLoginExt().getHouTong()){
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        List<ZkcVo> zkcVos= new ArrayList<>();
        List<String> nums;
        if ("hmpk10".equals(zkcVo.getLotteryKey())) {
            nums = NumUtil.hmpk10Number();
            nums.forEach(num -> {
                zkcVo.setResultStr(num);
                hmResultMapper.spPayoutPk10Zk(zkcVo);
                zkcVos.add(ObjectUtil.cloneByStream(zkcVo));
            });
        } else if (("hmcqssc".equals(zkcVo.getLotteryKey()))) {
            nums = NumUtil.hmsscNumber();
            nums.forEach(num -> {
                zkcVo.setResultStr(num);
                hmResultMapper.spPayoutSscZk(zkcVo);
                zkcVos.add(ObjectUtil.cloneByStream(zkcVo));
            });
        }
        return ResponseData.SUCCESS(zkcVos);
    }


}
