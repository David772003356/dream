package com.bigdream.dream.service.login;

import com.bigdream.dream.dto.UserDTO;
import com.bigdream.dream.mapper.DreamUserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-29 16:53
 **/
@Service
@Slf4j
public class UserService {

    @Autowired
    private DreamUserInfoMapper dreamUserInfoMapper;

    public void register(UserDTO req){
        dreamUserInfoMapper.saveUserInfo(req);
    }
}
