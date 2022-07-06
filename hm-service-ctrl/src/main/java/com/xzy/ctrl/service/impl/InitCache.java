package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitCache implements ApplicationRunner {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @DubboReference
    private GroupService groupService;
    @DubboReference
    private KindService kindService;
    @DubboReference
    private CategoryService categoryService;
    @DubboReference
    private LotteryService lotteryService;
    @DubboReference
    private QuickSettingService quickSettingService;
    @DubboReference
    private OddsService oddsService;
    @DubboReference
    private PlayService playService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("===>>>清空缓存<<<===");
        groupService.delGroupForRedis();
        kindService.delKindForRedis();
        categoryService.delCategoryForRedis();
        oddsService.delOddsForRedis();
        quickSettingService.delQuickSettingForRedis();
        playService.delPlayForRedis();


        logger.info("===>>>开始缓存<<<===");
        groupService.saveGroupToRedis();
        logger.info("group 加载到Redis成功！！！");
        kindService.saveKindToRedis();
        logger.info("kind 加载到Redis成功！！！");
        categoryService.saveCategoryToRedis();
        logger.info("category 加载到Redis成功！！！");
        //lotteryService.saveLotteryToRedis();
        //logger.info("lottery 加载到Redis成功！！！");
        oddsService.saveOddsToRedis();
        logger.info("odds 加载到Redis成功！！！");
        quickSettingService.saveQuickSettingToRedis();
        logger.info("quicksetting 加载到Redis成功！！！");
        playService.savePlayToRedis();
        logger.info("play 加载到Redis成功！！！");

        logger.info("===>>>缓存结束<<<===");
    }

}