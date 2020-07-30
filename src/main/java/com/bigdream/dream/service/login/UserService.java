package com.bigdream.dream.service.login;

import com.bigdream.dream.base.msg.ObjectRestResponse;
import com.bigdream.dream.dto.UserDTO;
import com.bigdream.dream.entity.DreamUserInfoDO;
import com.bigdream.dream.mapper.DreamUserInfoMapper;
import com.bigdream.dream.utils.AECUtil;
import com.bigdream.dream.utils.CopyUtils;
import com.bigdream.dream.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

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

    public boolean register(UserDTO req){
        Boolean result =false;
        DreamUserInfoDO userInfoDO = new DreamUserInfoDO();
        CopyUtils.copyProperties(req,userInfoDO);
        userInfoDO.setId(UUID.randomUUID().toString().replace("-",""));
        String userId = UUIDUtils.getSnowflakeId();
        userId=userId.substring(0,6);
        userInfoDO.setUserId("DR"+userId);
        userInfoDO.setPassword(AECUtil.encrypt(req.getPassword()));
        userInfoDO.setAccountStatus(1);
        userInfoDO.setCrtTime(new Date());
        userInfoDO.setUpdTimie(new Date());
        dreamUserInfoMapper.saveUserInfo(userInfoDO);
        result=true;
        return result;
    }

    public ObjectRestResponse login(UserDTO userDTO){
        if (StringUtils.isEmpty(userDTO.getPassword())){
            return new ObjectRestResponse().message("登录密码不能为空").status(200);
        }
        if (StringUtils.isEmpty(userDTO.getUserName())){
            return new ObjectRestResponse().message("登录账号不能为空").status(200);
        }
        userDTO.setPassword(AECUtil.encrypt(userDTO.getPassword()));
        DreamUserInfoDO user = dreamUserInfoMapper.getUserByAccountAndPassword(userDTO);
        if (null==user){
            return new ObjectRestResponse().message("用户不存在").status(200);
        }
        return new ObjectRestResponse().data(user).message("登录成功").status(200);
    }
}
