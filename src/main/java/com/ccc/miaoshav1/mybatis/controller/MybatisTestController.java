package com.ccc.miaoshav1.mybatis.controller;


import com.ccc.miaoshav1.dao.GoodsDao;
import com.ccc.miaoshav1.result.Result;
import com.ccc.miaoshav1.vo.GoodsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mybatisTest")
@Tag(name = "Mybatis测试所用",description = "Mybatis测试所用")
public class MybatisTestController {

    @Autowired
    private GoodsDao goodsDao;

    @GetMapping("/testGoods")
    @Operation(summary = "测试索取商品接口",description = "测试索取商品接口")
    public Result<List<GoodsVo>> testMyGoods() {
        List<GoodsVo> goodsList = goodsDao.listGoodsVo();
        return Result.success(goodsList);
    }
}
