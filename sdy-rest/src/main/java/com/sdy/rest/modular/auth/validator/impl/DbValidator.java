package com.sdy.rest.modular.auth.validator.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sdy.rest.common.persistence.dao.UserMapper;
import com.sdy.rest.common.persistence.model.User;
import com.sdy.rest.modular.auth.validator.IReqValidator;
import com.sdy.rest.modular.auth.validator.dto.Credence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账号密码验证
 */
@Service
public class DbValidator implements IReqValidator {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean validate(Credence credence) {
        List<User> users = userMapper.selectList(new EntityWrapper<User>().eq("userName", credence.getCredenceName()));
        if (users != null && users.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
