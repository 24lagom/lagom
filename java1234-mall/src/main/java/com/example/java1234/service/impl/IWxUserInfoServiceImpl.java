package com.example.java1234.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.java1234.entity.ProductSwiperImage;
import com.example.java1234.entity.WxUserInfo;
import com.example.java1234.mapper.ProductSwiperImageMapper;
import com.example.java1234.mapper.WxUserInfoMapper;
import com.example.java1234.service.IWxUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//微信用户信息Service实现类
@Service("wxUserInfoService")
public class IWxUserInfoServiceImpl extends ServiceImpl<WxUserInfoMapper, WxUserInfo> implements IWxUserInfoService {

    @Autowired
    private WxUserInfoMapper wxUserInfoMapper;
}




