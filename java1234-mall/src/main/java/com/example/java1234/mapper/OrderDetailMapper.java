package com.example.java1234.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.java1234.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Mapper
@Component
@Service
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {


}