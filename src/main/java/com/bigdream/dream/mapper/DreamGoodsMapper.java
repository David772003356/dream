package com.bigdream.dream.mapper;

import com.bigdream.dream.entity.DreamGoodsDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @description: 商品信息
 * @author: Wu Yuwei
 * @create: 2020-07-17 18:06
 **/
@Repository
public interface DreamGoodsMapper {

    DreamGoodsDO getGoodsById(@Param("goodsId") String goodsId);

    @Select("select * from dream_goods")
    DreamGoodsDO getGoodsByIds(@Param("goodsId") String goodsId);

}
