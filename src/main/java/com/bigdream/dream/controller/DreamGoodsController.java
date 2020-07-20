package com.bigdream.dream.controller;

import com.bigdream.dream.dto.GoodsDTO;
import com.bigdream.dream.entity.DreamGoodsDO;
import com.bigdream.dream.service.DreamGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-17 18:14
 **/
@RequestMapping("dream/goods")
@RestController
public class DreamGoodsController {
    @Autowired
    private DreamGoodsService dreamGoodsService;

    @PostMapping("/saveGoods")
    @ResponseBody
    public boolean saveGoods(@RequestBody GoodsDTO req){
        return dreamGoodsService.saveGoods(req);
    }


    @GetMapping("/getGoodsById")
    @ResponseBody
    public DreamGoodsDO getGoodsById(@RequestParam("goodsId") String goodsId){
       return dreamGoodsService.getGoodsById(goodsId);
    }
}
