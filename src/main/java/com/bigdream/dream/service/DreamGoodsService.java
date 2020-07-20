package com.bigdream.dream.service;

import com.bigdream.dream.dto.GoodsDTO;
import com.bigdream.dream.entity.DreamGoodsDO;
import com.bigdream.dream.mapper.DreamGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-17 18:15
 **/
@Service
public class DreamGoodsService {
    @Autowired
    private DreamGoodsMapper dreamGoodsMapper;

    public boolean saveGoods(GoodsDTO req){
        Boolean reuslt=false;
        String uuid = UUID.randomUUID().toString().replace("-", "");
        req.setGoodsId(uuid);
        req.setCurrentTime(new Date());
        int i = dreamGoodsMapper.saveGoods(req);
        if (i>0){
            reuslt=true;
        }
        return reuslt;
    }

    public DreamGoodsDO getGoodsById(String goodsId){
        DreamGoodsDO goodsById = dreamGoodsMapper.getGoodsById(goodsId);
        return goodsById;
    }
}
