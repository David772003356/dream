package com.bigdream.dream.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 用户信息
 * @author: Wu Yuwei
 * @create: 2020-07-29 16:50
 **/
@Data
public class DreamUserInfoDO implements Serializable {
    private static final long serialVersionUID = 7474659531999966996L;
    private String id;                  //主键
    private String userId;              //用户id
    private String userName;            //用户名称
    private String nickName;            //昵称
    private String password;            //密码
    private String email;               //邮箱
    private String mobile;              //电话
    private Integer accountStatus;       //账号状态（0-未激活，1-激活）
    private Date crtTime;               //创建时间
    private Date updTimie;              //更新时间
}
