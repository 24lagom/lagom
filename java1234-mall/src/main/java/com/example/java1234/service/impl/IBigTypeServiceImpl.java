package com.example.java1234.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.java1234.entity.BigType;
import com.example.java1234.mapper.BigTypeMapper;
import com.example.java1234.service.IBigTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//商品Service实现类
@Service("bigTypeService")
public class IBigTypeServiceImpl extends ServiceImpl<BigTypeMapper,BigType> implements IBigTypeService{
    @Autowired
    private BigTypeMapper bigTypeMapper;
}