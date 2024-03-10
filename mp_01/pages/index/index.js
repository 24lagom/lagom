// index.js
// 导入request请求工具类
import {getBaseUrl,requestUtil} from '../../utils/requestUtil';
import regeneratorRuntime from '../../lib/runtime/runtime';

Page({
  data: {
  //  轮播图数组
  swiperList:[],
  baseUrl:'',
  bigTypeList:[],
  bigTypeList_row1:[],
  bigTypeList_row2:[],
  hotProductList:[],
    },
onLoad:function(options){
//  发送异步请求，获取后端数据
  // wx.request({
  //   url: 'http://localhost:8080/product/findSwiper',
  //   method:"GET",
  //   success:(result)=>{
  //     console.log(result)
  //     this.setData({
  //       swiperList:result.data.message
  //     })
    // }
  // })
  this.getSwiperList();
  this.getBigTypeList();
  this.getHotProductList();
},

// 大类点击事件 跳转 商品分类页面
  handleTypeJump(e){
    console.log(e)
    const {index}=e.currentTarget.dataset;

    const app=getApp();
    app.globalData.index=index;

    wx.switchTab({
      url:'/pages/category/index'
    })
  },
// 获取轮播图数据
  async getSwiperList(){
  // requestUtil({url: '/product/findSwiper',method:"GET"}).then(result=>{
  //   const baseUrl=getBaseUrl();
  //   this.setData({
  //     swiperList:result.message,
  //     baseUrl
  //   })
  // })
    const result=await requestUtil({url:'/product/findSwiper',method:"GET"});
    const baseUrl=getBaseUrl();
    this.setData({
        swiperList:result.message,
        baseUrl
    })
},
    // 获取商品大类数据
  async getBigTypeList(){
    const result=await requestUtil({url: "/bigType/findAll"});
    console.log(result)
    const bigTypeList_row1=result.message.filter((item,index)=>{
      return index<5;
    })
    const bigTypeList_row2=result.message.filter((item,index)=>{
      return index>=5;
    })
    this.setData({
      bigTypeList: result,
      bigTypeList_row1,
      bigTypeList_row2
    })
  },
  // 获取热卖商品
  async getHotProductList(){
    const result=await requestUtil({
      url: "/product/findHot",
      method:"GET"
    });
    this.setData({
      hotProductList: result.message
    })
  },
})
