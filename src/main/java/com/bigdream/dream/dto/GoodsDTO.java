package com.bigdream.dream.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-20 20:06
 **/
@Data
public class GoodsDTO implements Serializable {
    private static final long serialVersionUID = 3010897983946169391L;

    private String goodsId;
    private String goodsName;
    private BigDecimal goodsPrice;
    private Integer goodsStatus;
    private String description;
    private String userId;
    private String userName;
    private Date currentTime;

}
