// pages/search/index.js
// 导入request请求工具方法
import {getBaseUrl, requestUtil} from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    productList:[], //商品数组
    inputValue:"",
    isFocus:false//取消 按钮 是否显示
  },
  TimeoutId:-1,
  //处理input事件
  handleInput(e){
    const {value}=e.detail;
    console.log(value);
    if(!value.trim()){
      this.setData({
        productList:[],
        isFocus:false
      })
      return;
    }
    this.setData({
      isFocus:true
    })
    clearTimeout(this.TimeoutId);
    this.TimeoutId=setTimeout(()=>{
      this.search(value)
    },1000)
  
  },
    // 发送请求获取结果
  async search(q){
    const res=await requestUtil({url:"/product/search",data:{q}});
    console.log(res);
    this.setData({
      productList:res.message
    })
  },
  // 点击 取消按钮
  handleCancel(){
    this.setData({
      inputValue:"",
      isFocus:false,
      productList:[]
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})