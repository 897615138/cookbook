// 本js文件用于个人主页动态展示
function toast(message, type) {
    bootoast.toast({
        message: message,
        type: type,
        position: 'top-center',
        icon: null,
        timeout: 1,
        animationDuration: 0,
        dismissible: true
    });
}

//选定导航栏变色
function changeActive(containerName, className) {
    $(`${containerName}`).on('click', `${className}`, function () {
        //console.log(this)
        $(`${className}`).removeClass('active')
        //console.log(`${className}`)
        this.classList.add("active")
    })
}

//锚点修改
function changeSection() {
    const sections = $('.secTo')
    //console.log(sections)
    $('.section').on('click', function () {
        //console.log($('.section'))
        sections.css('display', "none")
        const section = $(this).data('section')
        $(`#${section}`).css('display', 'block')
        //console.log(sections)
    })
}

//用户更新事件
function readyToUpdate() {
    $('.changeUserInfo').on('click', function () {
        // bootstrap提供的模态框 方法
        // 获取用户编号
        const userNo = this.dataset.userNo
        $.ajax({
            url: 'users/getLoginUser',
            dataType: 'JSON',
            success(data) {
                //console.log(data)
                const user = data.t
                // 回显 数据
                $('#updateForm').find('[type=text],[type=hidden]').each((index, el) => {
                    el.value = user[el.name] || ''
                })
                $('#updateForm').find('input[type=radio]').each((index, el) => {
                    console.log(user[el.name])
                    el.checked = el.value == user[el.name]
                })
                $('#updateForm').find('img').attr('src', data.t.userPicture)
                //console.log(user.userBirthday)
                //console.log(dataFormat(user.userBirthday))
                //$('#updateForm').find('[type=date]').val(dataFormat(user.userBirthday))
            }

        })
        // $('#changeUserInfo').modal('show')
    })
}

//用户信息更新
function updateUser() {
    $('#update').on('click', function () {
        //获取 更新表单的所有数据
        const formData = $('#updateForm').serialize()
        console.log(formData)
        //发起ajax 请求 将数据传到后端
        $.ajax({
            url: 'users/update',
            method: 'POST',
            data: formData,
            dataType: 'JSON',
            success(data) {
                if (data.result) {
                    showUserData()
                    $('#updateForm')[0].reset()
                    $('#changeUserInfo').modal('hide')
                }
            }
        })
        showUserData()
        return false
    })
}

//用户信息展示
function showUserData() {
    $.ajax({
        url: 'users/getLoginUser',
        method: 'get',
        success(data) {
            if (data.result) {
                $('#user-picture').attr('src', `${data.t.userPicture}`)
                $('#nickname').empty().html(`${data.t.userNickname}`)
                $('#user-email').empty().html(`${data.t.userMail}`)
            }
        }
    })
}

//注销用户
function deleteUser() {
    $('#deleteUser').on('click', function () {
        $.ajax({
            url: 'users/delete',
            method: 'post',
            success(data) {
                if (data.result) {
                    toast('删除用户成功', 'success')
                    window.location.href = 'index'
                }
            }
        })
    })
}

//收藏夹信息展示
function showUserMenu() {
    $.ajax({
        url: 'menus/getLoginUserMenus',
        method: 'get',
        success(data) {
            if (data.result) {
                let content = ``
                data.data.forEach(el => {
                    content += `
                    <li class="chooseCollection chooseMenu" data-menu-id="${el.menuId}" data-menu-name="${el.menuName}"><a href="javascript:">${el.menuName}</a></li>
                    `
                })
                $('#menuList').empty().html(content)
                showMenuDish(1, data.data[0].menuId)
            }
        }
    })
}

//新建收藏夹
function createUserMenu() {
    $('#menuCreate').on('click', function () {
        const menuInfo = $('#menuCreateForm').serialize();
        $.ajax({
            url: 'menus/insert',
            data: menuInfo,
            dataType: 'json',
            method: 'post',
            success(data) {
                if (data.result) {
                    const msg = data.msg;
                    toast(msg, 'success')
                    showUserMenu()
                    $('#menuCreateForm')[0].reset()
                    $('#createMenuInfo').modal('hide')
                } else {
                    toast(data.msg, 'warning')
                }
            }
        })
    })
}

//删除收藏夹
function deleteUserMenu() {
    //监听修改按钮
    $('.choCollectionContainer').on('click', '#deleteMenu', function () {
        //获取当前收藏夹信息
        const menuId = $('#menuList').data('currMenuId')
        const menuName = $('#menuList').data('currMenuName')
        console.log(menuId)
        if (menuId === undefined) {
            toast('当前未选择收藏夹', 'warning')
        } else {
            toast(`当前选择收藏夹为${menuName}`, 'success')
            $('#menuDelete').on('click', function () {
                $.ajax({
                    url: 'menus/delete',
                    data: {menuId},
                    dataType: 'json',
                    method: 'post',
                    success(data) {
                        if (data.result) {
                            toast(data.msg, 'success')
                            showUserMenu()
                            $('#deleteMenuInfo').modal('hide')
                        }
                    }
                })
            })
        }
    })
}

//收藏夹内菜谱信息展示
function showMenuDish(page, menuId) {
    page = page || {currPage: 1}
    $.ajax({
        url: 'menus/getMenuContentById',
        method: 'get',
        data: {currpage: page, id: menuId},
        success(data) {
            if (data.result) {
                let content = ''
                data.data.forEach(el => {
                    content += `
                    <div class="item">
                                    <div class="menu-banner menu-dish-item" data-dish-id="${el.dishId}" style="background-image: url(${el.picturesInfoList[0].picAddress})">
                                        
                                        <div class="menu-main">
                                            <div class="menu-info">
                                                <div class="menu-title">
                                                    ${el.dishName}
                                                </div>
                                                <div class="menu-ext">
                                                    <span class="menu-discuss">${el.likeNumber}喜欢</span>
                                                    <span class="read">${el.collectNumber}收藏</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="glyphicon glyphicon-remove-circle remove-dish-from-menu" style="float: right;margin-right: -16px;margin-top: -10px"
                                        data-dish-id = "${el.dishId}" data-menu-id="${data.page.id}"
                                        ></div>
                    `
                })
                $('.menu-container').empty().html(content)
                //拼装page
                let page = `
                <li><a href="javascript:"
                       data-curr-page="${data.page.currPage - 1 > 0 ? data.page.currPage - 1 : 1}"
                       class="menu-page">&laquo;</a></li>
                <li><a href="javascript:" data-curr-page="1" class="menu-page">首页</a></li>
                <li><a href="#" readonly="true" data-curr-page="">当前第
                    <sapn>${data.page.currPage}</sapn>
                    页</a></li>
                <li><a href="javascript:" data-curr-page="${data.page.totalPage}"
                       class="menu-page">尾页</a></li>
                <li><a href="javascript:"
                       data-curr-page="${data.page.currPage + 1 > data.page.totalPage ? data.page.totalPage : data.page.currPage + 1}"
                       class="menu-page">&raquo;</a></li>
                `
                $('#menu-page').empty().html(page)
            } else {
                toast('收藏夹无内容', 'error')
                $('.menu-container').empty()
                $('#menu-page').empty()
            }
        }
    })
}

//监听收藏page按钮点击
function clickPage() {
    $('#menu-page').on('click', '.menu-page', function () {
        const currPage = this.dataset.currPage
        const menuId = $('.choCollectionContainer').dataset.currMenuId ? $('.choCollectionContainer').dataset.currMenuId : 0
        showMenuDish(currPage, menuId)
    })
}

//监听收藏按钮点击
function chooseMenu() {
    $('#menuList').on('click', '.chooseMenu', function () {
        //获取点击的是哪个收藏夹
        const menuId = this.dataset.menuId
        const menuName = this.dataset.menuName
        //将这个值赋值给导航容器，方便之后调用
        $('#menuList').data('currMenuId', menuId)
        $('#menuList').data('currMenuName', menuName)
        //调用展示收藏夹内菜谱信息方法
        showMenuDish(1, menuId)
    })
}

//回显被修改收藏夹信息
function readyToUpdateMenu() {
    //监听修改按钮
    $('#updateMenu').on('click', function () {
        //获取当前收藏夹信息
        const menuId = $('#menuList').data('currMenuId')
        const menuName = $('#menuList').data('currMenuName')
        console.log(menuId)
        if (menuId === undefined) {
            toast('当前未选择收藏夹', 'warning')
            $('#changeMenuInfo').modal('show')
        } else {
            toast(`当前选择收藏夹为${menuName}`, 'success')
            $.ajax({
                url: 'menus/id',
                data: {menuId},
                dataType: 'json',
                method: 'get',
                success(data) {
                    const menu = data.t
                    // 回显 数据
                    $('#menuUpdateForm').find('[type=text],[type=hidden]').each((index, el) => {
                        el.value = menu[el.name] || ''
                    })
                }
            })
        }
    })
}

//修改收藏夹请求
function updateMenu() {
    //监听修改提交按钮
    $('#menuUpdate').on('click', function () {
        //获取收藏夹修改表单数据
        const menuInfo = $('#menuUpdateForm').serialize();
        $.ajax({
            url: 'menus/update/name',
            data: menuInfo,
            dataType: 'json',
            method: 'post',
            success(data) {
                if (data.result) {
                    showUserMenu()
                    $('#menuUpdateForm')[0].reset()
                    $('#changeMenuInfo').modal('hide')
                }
            }
        })
    })
}

//个人菜谱展示
function showUserDishesWithOutClick(page) {
    console.log('page')
    page = page || {currPage: 1}
    console.log('page')
    $.ajax({
        url: 'dishes/dishesNumber',
        method: 'post',
        data: {currpage: page},
        success(data) {
            if (data.result) {
                let content = ''
                data.data.forEach(el => {
                    content += `
                        <div class="item">
                            <div class="menu-banner dish-item" data-dish-id = "${el.dishId}" style="background-image: url(${el.picturesInfoList[0].picAddress})">
                                <div class="menu-main">
                                    <div class="menu-info">
                                        <div class="menu-title">
                                            ${el.dishName}
                                        </div>
                                        <div class="menu-ext">
                                            <span class="menu-discuss">${el.likeNumber}喜欢</span>
                                            <span class="read">${el.collectNumber}收藏</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `
                })
                $('#dish-container').empty().html(content)
                //拼装page
                const page = `
                <li><a href="javascript;"
                       data-curr-page="${data.page.currPage - 1 > 0 ? data.page.currPage - 1 : 1}"
                       class="dish-page">&laquo;</a></li>
                <li><a href="javascript:" data-curr-page="1" class="dish-page">首页</a></li>
                <li><a href="#" readonly="true" data-curr-page="">当前第
                    <sapn>${data.page.currPage}</sapn>
                    页</a></li>
                <li><a href="javascript:" data-curr-page="${data.page.totalPage}"
                       class="dish-page">尾页</a></li>
                <li><a href="javascript:"
                       data-curr-page="${data.page.currPage + 1 > data.page.totalPage ? data.page.totalPage : data.page.currPage + 1}"
                       class="dish-page">&raquo;</a></li>
                `
                $('#dish-page').empty().html(page)
            } else {
                toast(data.msg, 'warning')
                $('#dish-container').empty().html('')
            }
        }
    })
}

function showUserDishes(page) {
    $('#myDish').on('click', function () {
        showUserDishesWithOutClick(page)
    })
}

//监听我的收藏按钮点击
function clickMenuButton() {
    $('#myMenu').on('click',function () {
        showUserMenu()
    })
}

//从收藏夹中移除菜谱
function removeDishFromMenu() {
    $('.menu-container').on('click','.remove-dish-from-menu',function() {
        //e.stopPropagation()
        const dishId = $(this).data('dishId')
        const menuId = $(this).data('menuId')
        $.ajax({
            url:'menus/deleteMenuDish',
            data:{dishId:dishId,menuId:menuId},
            method:'post',
            success(data) {
                toast(data.msg,'success')
                showMenuDish(1, menuId)
            }
        })
    })
}

//菜谱分页按钮监听
function clickDishPage() {
    $('#dish-page').on('click','.dish-page' ,function () {
        const currPage = $(this).data('currPage')
        showUserDishesWithOutClick(currPage)
    })
}

//获取粉丝
function showFan() {
    $('#showFan').on('click', function () {
        $.ajax({
            url: 'users/getFans',
            method: 'post',
            success(data) {
                if (data.result) {
                    let content = ''
                    data.data.forEach(el => {
                        content += `
                    <div class="circle fun-item other-user"
                         style="border: 1px solid #21272b;border-radius: 50%;width: 90px;height: 90px;" data-user-id="${el.userId}">
                        <img class="img-circle" style="width: 100%;height: 100%" data-user-picture="${el.userPicture}" src="${el.userPicture}">
                        <span style="display: block;margin-top: 10px;margin-left: 25px"
                              align="center">${el.userNickname}</span>
                    </div>
                    `
                    })
                    $('#fanContainer').empty().html(content)
                } else {
                    toast(data.msg, 'warning')
                }
            }
        })
    })
}

//获取关注
function showFollower() {
    $('#showFollower').on('click', function () {
        $.ajax({
            url: 'users/getFollows',
            method: 'post',
            success(data) {
                if (data.result) {
                    let content = ''
                    data.data.forEach(el => {
                        content += `
                    <div class="circle follower-item other-user"
                         style="border: 1px solid #21272b;border-radius: 50%;width: 90px;height: 90px;" id="followerId" data-user-id="${el.userId}">
                        <img class="img-circle" style="width: 100%;height:100%" id="followerPicture" data-user-picture="${el.userPicture}" src="${el.userPicture}">
                        <span style="display: block;margin-top: 10px;margin-left: 25px"
                              align="center" id="followerName">${el.userNickname}</span>
                    </div>
                    `
                    })
                    $('#followerContainer').empty().html(content)
                } else {
                    toast(data.msg, 'warning')
                }
            }
        })
    })
}

//判断是否登录
function isLoginUser() {
    let show = ''
    $.ajax({
        url: 'users/getLoginUser',
        method: 'GET',
        dataType: 'JSON',
        success(data) {
            //如果已经登录的话
            if (data.result) {
                show = `<li class="dropdown loginlist" id="userInfo" style="width: 120px;">
                        <a href="#" style="position:relative;border-bottom: 5px solid transparent;padding-right: 90px;" class="dropdown-toggle" data-toggle="dropdown">
                            <span class="glyphicon glyphicon-user"></span><span style="margin-top: 10px;position:absolute;top:10px;width:100px">${data.t.userName}<b class="caret"></b></span>
                        </a>
                         <ul class="dropdown-menu" style="position: absolute; left: -1px;  background-color: #0000004d;">
                            <li style="border-bottom: 0 solid #fff;"><a href="userPage" style="color: #fff">个人中心</a></li>
               
                            <li style="border-bottom: 0 solid #fff;"><a href="menuManage" style="color: #fff">发布菜谱</a></li>
                   
                            <li style="border-bottom: 0 solid #fff;"><a href="index" id="logout" style="color: #fff">退出登录</a></li>
                        </ul>
                    </li>`
            } else {
                show = `   <li><a href="login">登录/注册</a></li>     `
            }
            $('#isLogin').empty().html(show)

        }
        // }
    })
}

//解析URL字符串参数
function getQueryStringArgs() {
    //取得查询字符串问号后参数字符串
    const qs = location.search.length > 0 ? location.search.substring(1) : ""
    //定义保存数据对象
    let args = {}
    //取得每一项
    const items = qs.length ? qs.split('&') : []
    let item = null
    let name = null
    let value = null
    items.forEach(el => {
        item = el.split('=')
        name = decodeURIComponent(item[0])
        value = decodeURIComponent(item[1])
        if (name.length) {
            //将参数封装成键值对
            args[name] = value
        }
    })
    //返回封装好的参数map
    return args
}

//增加菜谱
function addDish() {
    $('#addDish').on('click', function () {
        window.location.href = 'menuManage'
    })
}

//删除菜谱
function deleteDish() {
    $('#deleteDish').on('click', function () {
        isUpdateDish = false
        isDeleteDish = true
        isChooseDish = false
    })
}

//修改菜谱
function updateDish() {
    $('#updateDish').on('click', function () {
        isUpdateDish = true
        isDeleteDish = false
        isChooseDish = false
    })
}

//选择菜谱
function chooseDish() {
    $('#chooseDish').on('click', function () {
        isUpdateDish = false
        isDeleteDish = false
        isChooseDish = true
    })
}

//监听菜谱点击，执行具体操作
function dishOp() {
    $('#dish-container').on('click', '.dish-item', function () {
        const dishId = $(this).data('dishId')
        if (isUpdateDish) {
            window.location.href = `menuManage34?dishId=${dishId}`
        } else if (isDeleteDish) {
            $.ajax({
                url: 'dishes/delete',
                data: {dishId},
                dataType: 'json',
                method: 'post',
                success(data) {
                    if (data.result) {
                        toast('删除成功', 'success')
                        showUserDishesWithOutClick()
                    } else {
                        toast('删除失败', 'warning')
                    }
                }
            })
        } else if (isChooseDish) {
            window.location.href = `menuDetail?dishId=${dishId}`
        }
    })
}

//查看他人个人页面
function viewOtherUser() {
    $('.otherContainer').on('click', '.other-user', function () {
        const userId = $(this).data('userId')
        window.location.href = `userPage2?userId = ${userId}`
    })
}

//监听收藏夹页面菜谱点击
function clickMenuDish() {
    $('.menu-container').on('click', '.menu-dish-item', function () {
        console.log(this)
        const dishId = $(this).data('dishId')
        window.location.href = `menuDetail?dishId=${dishId}`
    })
}