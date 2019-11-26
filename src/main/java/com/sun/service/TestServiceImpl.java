package com.sun.service;

import com.sun.dataaop.Master;
import com.sun.mapper.UserBaseDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements ITestService {

    @Autowired
    private UserBaseDOMapper userBaseDOMapper;


    @Override
    public Object read() {
        return userBaseDOMapper.select();
    }

    @Master
    @Override
    public Object write() {
        return userBaseDOMapper.select2();
    }
}
