package com.example.java1234.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_product_swiper_image")
@Data
public class ProductSwiperImage {
    private Integer id;

    private String image;

    private Integer sort;

    private Integer productId; //所属产品
}
