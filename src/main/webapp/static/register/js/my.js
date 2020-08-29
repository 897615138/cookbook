function toast(message, type) {
    bootoast.toast({
        message: message,
        type: type,
        position: 'top-center',
        icon: null,
        timeout: 2,
        animationDuration: 300,
        dismissible: true
    });
}

// // 登录判定
// function checkLog() {
//     const user = localStorage.getItem('loginUser')
//     let show = ''
//     if (user) {//登录
//         const login = JSON.parse(user)//将user转换为json对象
//         show = `<li class="dropdown" id="userInfo">
//                     <a href="#" class="dropdown-toggle" data-toggle="dropdown">
//                             <span class="glyphicon glyphicon-user"></span>${login.userAccount}
//                             <b class="caret"></b>
//                         </a>
//                          <ul class="dropdown-menu">
//                             <li><a href="javascript:">个人信息</a></li>
//                             <li><a href="cart.html">购物车</a></li>
//                             <li><a href="javascript:" id="logout">退出登录</a></li>
//                         </ul>
//                     </li>`
//     } else {//未登录
//         show = ` <li><a href="./register.html"><span class="glyphicon glyphicon-user"></span> 注册</a></li>
//                     <li><a href="./login.html"><span class="glyphicon glyphicon-log-in"></span> 登录</a></li>
//                 `
//     }
//     $('#isLogin').empty().html(show)
// }
//
//
// // 登出
//
// function logout() {
//     setTimeout(() => {
//         localStorage.removeItem('loginUser')
//         window.location.href='index.html'
//     }, 500)
// }
//
// if (!localStorage.getItem('data')) {
//     const products = Mock.mock({
//         "product|6": [{
//             'productNo|0-100': 100,
//             'productName': '@string("lower", 5)',   //中文名称
//             'productDesc': '@city(true)',
//             'productUrl': '../images/shoes.jpg',
//             'productDetail': '@string("lower", 20)',
//             'productPrice|100-1000': 1000
//         }]
//     })
//     localStorage.setItem('data', JSON.stringify(products.product))
// }
//
// /**
//  * 查找对应商品对象
//  * @param e
//  * @returns {*|jQuery|number|bigint}
//  */
// function findByProductNo(e) {
//     const data = JSON.parse(localStorage.getItem('data'))
//     return data.find(el => el.productNo == e)
// }
//
// function addToCart(userAccount, productNo) {
//     // 获取用户对应 购物车
//     const queue = getCart(userAccount)
//     //
//     const pro = queue.findIndex(el => el.productNo == productNo)
//     if (pro == -1) {
//         const product = findByProductNo(productNo)
//         // 默认选中
//         product.isSelect = 0;
//         queue.push(product)
//     }
//     console.log(queue)
//     // 放入 ls
//     localStorage.setItem(userAccount, JSON.stringify(queue))
// }
//
// function removeFromCart(userAccount, productNo) {
//     // 获取用户对应 购物车
//     const queue = getCart(userAccount)
//     //
//     const pro = queue.findIndex(el => el.productNo == productNo)
//     queue.splice(pro, 1)
//     // 放入 ls
//     localStorage.setItem(userAccount, JSON.stringify(queue))
// }
//
// function getLoginUser() {
//     return localStorage.getItem('loginUser') ? JSON.parse(localStorage.getItem('loginUser')) : undefined
// }
//
// function getCart(userAccount) {
//     const queue = localStorage.getItem(userAccount) ? JSON.parse(localStorage.getItem(userAccount)) : []
//     return queue
// }
//
// function getProductFromCart(productNo, userAccount) {
//     const cart = getCart(userAccount)
//     return cart.find(el => el.productNo == productNo)
// }
//
// /**
//  * 获取 总金额
//  * @param userAccout
//  * @returns {number}
//  */
// function getTotalPrice(userAccout) {
//     const cart = getCart(userAccout)
//     let total =0;
//     cart.forEach(el => {
//         if(el.isSelect==0){
//             total += el.productPrice
//         }
//     })
//     return total
// }
