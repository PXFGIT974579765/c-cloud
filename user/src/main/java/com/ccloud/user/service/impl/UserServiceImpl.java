package com.ccloud.user.service.impl;

import com.ccloud.user.dataobject.UserInfo;
import com.ccloud.user.repository.UserInfoRepository;
import com.ccloud.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:04
 * @description：用户服务实现类
 * @modified By：
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserInfo findByPhone(String phone) {
        return repository.findByPhone(phone);
    }
}
