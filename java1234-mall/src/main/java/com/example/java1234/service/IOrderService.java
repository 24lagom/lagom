package com.example.java1234.service;

import org.apache.ibatis.annotations.Mapper;

        import com.baomidou.mybatisplus.extension.service.IService;
        import com.example.java1234.entity.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
        import java.util.Map;


/**
 * 订单主表Service接口
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 9:03
 */
@Mapper
@Component
@Service
public interface IOrderService extends IService<Order> {

    /**
     * 根据条件 分页查询订单
     * @param map
     * @return
     */
    public List<Order> list(Map<String,Object> map);

    /**
     * 根据条件，查询订单总记录数
     * @param map
     * @return
     */
    public Long getTotal(Map<String,Object> map);

}

