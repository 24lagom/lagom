package com.example.java1234.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.java1234.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 订单主表Mapper接口
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-23 8:53
 */
@Component
@Service
public interface OrderMapper extends BaseMapper<Order> {

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

