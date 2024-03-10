package com.example.java1234.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.java1234.entity.BigType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//商品大类Service接口
@Component
@Service
@Mapper
public interface IBigTypeService extends IService<BigType> {
}
