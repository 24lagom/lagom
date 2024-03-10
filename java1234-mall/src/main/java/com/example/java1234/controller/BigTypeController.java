package com.example.java1234.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.java1234.entity.BigType;
import com.example.java1234.entity.Product;
import com.example.java1234.entity.R;
import com.example.java1234.entity.SmallType;
import com.example.java1234.service.IBigTypeService;
import com.example.java1234.service.IProductService;
import com.example.java1234.service.ISmallTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bigType")
public class BigTypeController {

    @Autowired
    private IBigTypeService bigTypeService;

    @Autowired
    private ISmallTypeService smallTypeService;

    @Autowired
    private IProductService productService;

    /**
     * 查询所有商品大类
     * @return
     */
    @RequestMapping("/findAll")
    public R findAll(){
        List<BigType> bigTypeList = bigTypeService.list(new QueryWrapper<BigType>().orderByAsc("id"));
        Map<String,Object> map=new HashMap<>();
        map.put("message",bigTypeList);
        return R.ok(map);
    }

    /**
     * 获取所有菜单信息
     * @return
     */
    @RequestMapping("/findCategories")
    public R findCategories(){
        List<BigType> bigTypeList = bigTypeService.list(new QueryWrapper<BigType>().orderByAsc("id"));
        for(BigType bigType:bigTypeList){
            List<SmallType> smallTypeList = smallTypeService.list(new QueryWrapper<SmallType>().eq("bigTypeId", bigType.getId()));
            bigType.setSmallTypeList(smallTypeList);
            for(SmallType smallType:smallTypeList){
                List<Product> productList = productService.list(new QueryWrapper<Product>().eq("typeId", smallType.getId()));
                smallType.setProductList(productList);
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("message",bigTypeList);
        return R.ok(map);
    }


}


