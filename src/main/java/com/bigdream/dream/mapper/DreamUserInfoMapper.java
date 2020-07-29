package com.bigdream.dream.mapper;

import com.bigdream.dream.dto.UserDTO;
import com.bigdream.dream.entity.DreamGoodsDO;
import org.springframework.stereotype.Repository;

/**
 * @description: 商品信息
 * @author: Wu Yuwei
 * @create: 2020-07-17 18:06
 **/
@Repository
public interface DreamUserInfoMapper {

    DreamGoodsDO saveUserInfo(UserDTO req);

}
