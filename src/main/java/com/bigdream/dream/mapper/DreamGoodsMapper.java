package com.bigdream.dream.mapper;

import com.bigdream.dream.dto.GoodsDTO;
import com.bigdream.dream.entity.DreamGoodsDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @description: 商品信息
 * @author: Wu Yuwei
 * @create: 2020-07-17 18:06
 **/
@Repository
public interface DreamGoodsMapper {

    int saveGoods(GoodsDTO req);

    DreamGoodsDO getGoodsById(@Param("goodsId") String goodsId);


}
