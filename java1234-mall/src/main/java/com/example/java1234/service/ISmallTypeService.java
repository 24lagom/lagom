package com.example.java1234.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.java1234.entity.SmallType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 商品小类Service接口
 */
@Component
@Service
public interface ISmallTypeService extends IService<SmallType> {

    /**
     * 根据条件 分页查询商品小类
     * @param map
     * @return
     */
    public List<SmallType> list(Map<String,Object> map);

    /**
     * 根据条件，查询商品小类总记录数
     * @param map
     * @return
     */
    public Long getTotal(Map<String,Object> map);

    /**
     * 添加商品小类
     * @param smallType
     * @return
     */
    public Integer add(SmallType smallType);

    /**
     * 修改商品小类
     * @param smallType
     * @return
     */
    public Integer update(SmallType smallType);

}
