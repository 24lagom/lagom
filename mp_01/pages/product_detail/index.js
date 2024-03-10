// 导入request请求工具方法
import {getBaseUrl, requestUtil} from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    productObj:{},
    baseUrl:'',
    activeIndex:0
  },
  productInfo:{

  },
  
    // 生命周期函数--监听页面加载
   
  onLoad: function (options) {
    const baseUrl = getBaseUrl();
    this.setData({
      baseUrl
    })
    console.log(baseUrl + "")
    this.getProductDetail(options.id)
  },
  // tab点击事件
  handleItemTap(e){
    console.log(e)
    const {index}=e.currentTarget.dataset;
    console.log(index);
    this.setData({
      activeIndex:index
    })
  },
  // 获取产品详情
  async getProductDetail(id){
    const result=await requestUtil({
      url: "/product/detail",
      data:{id},
      method:"GET"
    });
    this.productInfo=result.message;
    this.setData({
      productObj:result.message
    })

    console.log(result)
  },

  // 点击事件 商品加入购物车
  handleCartAdd(){
    this.setCartadd();

    wx.showToast({
      title: '加入成功',
      icon:'success',
      mask:true
    })
  },

  //加入购物车
  setCartadd(){
    let cart= wx.getStorageSync('cart')||[];// 从缓存中获取购物车数据，如果不存在则初始化为空数组
    console.log("cart="+cart);// 打印购物车数据
    let index=cart.findIndex(v=>v.id===this.productInfo.id);// 查找购物车中是否已存在当前商品的索引
    if(index===-1){ // 如果购物车中不存在当前商品
       this.productInfo.num=1;// 设置商品数量为1
       this.productInfo.checked=true;
       cart.push(this.productInfo);// 将商品信息添加到购物车数组中
    }else{ // 如果购物车中已存在当前商品
        cart[index].num++;// 增加该商品的数量
    }
    wx.setStorageSync('cart', cart) // 将购物车数据保存到缓存中
  },

// 点击 立即购买
handleBuy(){
  this.setCartadd();
  wx.switchTab({
    url: '/pages/cart/index',
  })
},
 

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})