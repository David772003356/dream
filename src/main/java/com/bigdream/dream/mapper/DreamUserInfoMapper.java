package com.bigdream.dream.mapper;

import com.bigdream.dream.entity.DreamUserInfoDO;
import org.springframework.stereotype.Repository;

/**
 * @description: 商品信息
 * @author: Wu Yuwei
 * @create: 2020-07-17 18:06
 **/
@Repository
public interface DreamUserInfoMapper {

    void saveUserInfo(DreamUserInfoDO userInfoDO);

}
