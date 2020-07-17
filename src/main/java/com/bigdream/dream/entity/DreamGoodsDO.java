package com.bigdream.dream.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 商品信息
 * @author: Wu Yuwei
 * @create: 2020-07-17 18:09
 **/
@Data
public class DreamGoodsDO implements Serializable {
    private static final long serialVersionUID = 4003152722956090083L;
    private String goodsId;
    private String goodsName;
    private BigDecimal goodsPrice;
    private Integer goodsStatus;
    private String description;
    private String crtUserName;
    private String crtUserId;
    private String crtTime;
    private String updUserName;
    private String updUserId;
    private String updTimie;
}
