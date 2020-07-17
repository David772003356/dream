package com.bigdream.dream.service;

import com.bigdream.dream.entity.DreamGoodsDO;
import com.bigdream.dream.mapper.DreamGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-17 18:15
 **/
@Service
public class DreamGoodsService {
    @Autowired
    private DreamGoodsMapper dreamGoodsMapper;

    public DreamGoodsDO getGoodsById(String goodsId){
        DreamGoodsDO goodsById = dreamGoodsMapper.getGoodsById(goodsId);
        return goodsById;
    }
}
