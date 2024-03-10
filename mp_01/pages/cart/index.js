// pages/cart/index.js
// 导入request请求工具类
import {getBaseUrl,requestUtil} from '../../utils/requestUtil.js';
import regeneratorRuntime from '../../lib/runtime/runtime';

Page({

  /**
   * 页面的初始数据
   */
  data: {
    address:{},
    cart:[],
    baseUrl:'',
    allChecked:false,
    totalPrice:0,
    totalNum:0
  },
    // 点击 获取收货地址
  handleChooseAddress(){
 
    wx.chooseAddress({
      success: (result) => {
        console.log(result)
        wx.setStorageSync('address', result)
      },
    })
  },
// 单项选择影响全选：商品选中事件处理
handleItemChange(e){
  // 获取被修改的商品的id
  const id=e.currentTarget.dataset.id;
  console.log(id);
  // 获取购物车数组
  let {cart}=this.data;
  // 找到被修改的商品对象
  let index=cart.findIndex(v=>v.id===id);
  console.log(index)
  // 选中状态取反
  cart[index].checked=!cart[index].checked;
  
  this.setCart(cart);

},

// 全选影响单项选择：商品全选事件处理
handleItemAllCheck(){
  let {cart,allChecked}=this.data;
  console.log(cart,allChecked)
  allChecked=!allChecked;
  cart.forEach(v=>v.checked=allChecked);
  this.setCart(cart);
},

   // 商品数量的编辑功能
   handleItemNumEdit(e){
    const {id,operation}=e.currentTarget.dataset;
    console.log(id,operation)
    let {cart}=this.data;
    let index=cart.findIndex(v=>v.id===id);
    if(cart[index].num===1 && operation === -1){
      wx.showModal({
        title: '系统提示',
        content:'您是否要删除？',
        cancelColor: 'cancelColor',
        success:(res)=>{
          if(res.confirm){
            cart.splice(index,1);
            this.setCart(cart);
          }
        }
      })
    }else{
      cart[index].num+=operation;
      this.setCart(cart);
    }
   } ,
  //  点击 结算
  handlePay(){
    const {address,totalNum}=this.data;
    if(!address){
      wx.showToast({
        title: '您还没有选择收获地址',
        icon:'none'
      })
      return;
    }
    if(totalNum === 0){
      wx.showToast({
        title: '您还没有选购商品',
        icon:'none'
      })
      return;
    }
    wx.navigateTo({
      url: '/pages/pay/index'
    })
  },
  // 设置购物车状态 同时 重新计算 底部工具栏的数据 全选 总价格 购买的数量
   setCart(cart){
    let allChecked=true;
    // 1 总价格 总数量
    let totalPrice=0;
    let totalNum=0;
    cart.forEach(v=>{
      if(v.checked){
        totalPrice+=v.num*v.price;
        totalNum+=v.num;
      }else{
        allChecked=false;
      }
    })
    // 判断cart数组是否位空
    allChecked=cart.length!=0?allChecked:false;
    // 2 给data赋值
    this.setData({
      cart,
      allChecked,
      totalPrice,
      totalNum
    })
    // cart设置到缓存中
    wx.setStorageSync('cart', cart);
},
  /**
   * 生命周期函数--监听页面加载
   */
  // getBaseUrl()函数的作用是获取基础URL并将其设置为当前对象的属性
  onLoad(options) {
    const baseUrl = getBaseUrl();
    this.setData({
      baseUrl
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    console.log("show")
    const address=wx.getStorageSync('address');
    const cart=wx.getStorageSync('cart')||[];
    
    this.setData({
      address
    })
    this.setCart(cart);
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