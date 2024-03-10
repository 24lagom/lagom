package com.example.java1234.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.java1234.entity.OrderDetail;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.java1234.service.IOrderDetailService;

@Component
@Service
public interface IOrderDetailService extends IService<OrderDetail> {


}
