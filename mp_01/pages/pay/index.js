// pages/pay/index.js
// 导入request请求工具方法
import {getBaseUrl, requestUtil,getLogin,getUserProfile, requestPay} from "../../utils/requestUtil.js";
import regeneratorRuntime from '../../lib/runtime/runtime';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    address:{},
    cart:[],
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
//  处理订单支付
async handleOrderPay(){
  // wx.login({
  //   timeout:5000,
  //   success: (res) => {
  //     console.log(res.code)
  //   },
  // })
  // let res = await getWxLogin();
  // console.log(res.code)

  // wx.getUserProfile({
  //   desc: '获取用户信息',
  //   success:(res)=>{
  //     console.log(res.userInfo.nickName,res.userInfo.avatarUrl)
  //   }
  // })

  // let res2=await wx.getUserProfile();
  // console.log(res2.userInfo.nickName,res2.userInfo.avatarUrl)

  const token = wx.getStorageSync('token');
  if(!token){
    Promise.all([getWxLogin(),wx.getUserProfile()]).then((res)=>{
      console.log(res[0].code);
      console.log(res[1].userInfo.nickName,res[1].userInfo.avatarUrl);
      let loginParam={
        code:res[0].code,
        nickName:res[1].userInfo.nickName,
        avatarUrl:res[1].userInfo.avatarUrl
      }
      console.log(loginParam)
      wx.setStorageSync('userInfo', res[1].userInfo);
      this.wxlogin(loginParam);
    })
  }else{
    console.log("token存在:"+token);
    console.log("创建订单");
    this.createOrder();
  }
},

// 请求后端获取用户token
  async wxogin(loginParam){
    const result = await requestUtil({url:"/user/wxlogin",data:loginParam,method:"post"});
    console.log(result)
    // wx.setStorageSync('userInfo', res[1].userInfo);
    // this.wxlogin(loginParam);

    const token=result.token;
    if(result.code==0){
      wx.setStorageSync('token', token);
      console.log("支付继续走，创建订单");
      this.createOrder();
    }
    
  },
  /**
   * 创建订单
   */
  async createOrder(token){
    try{
      // const header={Authorization:token};  // 请求头参数
       const totalPrice=this.data.totalPrice; // 请求体 总价
       const address=this.data.address.provinceName+this.data.address.cityName+this.data.address.countyName+this.data.address.detailInfo; // 请求体 收货地址
       const consignee=this.data.address.userName; // 请求体 收货人
       const telNumber=this.data.address.telNumber; // 请求体 联系电话
       let goods=[];
       this.data.cart.forEach(v=>goods.push({
         goodsId:v.id,
         goodsNumber:v.num,
         goodsPrice:v.price,
         goodsName:v.name,
         goodsPic:v.proPic
       }))
       console.log(goods)
       //  发送请求 创建订单
       const orderParams={
         totalPrice,
         address,
         consignee,
         telNumber,
         goods
       }
       const res=await requestUtil({url:"/my/order/create",method:"POST",data:orderParams});
     
       console.log(res.orderNo);
       let orderNo=res.orderNo;
       console.log("orderNo:"+orderNo);
      //  调用统一下单，预支付
       const preparePayRes=await requestUtil({url:"/my/order/preparePay",method:"POST",data:orderNo});
       console.log("preparePayRes:"+preparePayRes)
       let payRes=await requestPay(preparePayRes);
       // 删除缓冲中 已经支付的商品
       let newCart=wx.getStorageSync('cart');
       newCart=newCart.filter(v=>!v.checked);
 
       wx.setStorageSync('cart', newCart);
 
       wx.showToast({
         title: '支付成功',
         icon:'none'
       });
 
       wx.navigateTo({
         url: '/pages/order/index?type=0',
       })
 
 
    }catch(error){
     console.log(error);
     wx.showToast({
       title: '支付失败',
       icon:'none'
     })
 
    }
 
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