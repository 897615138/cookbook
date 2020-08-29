

(function f() {
    //每一个页面加载的时候，就会判断一个这个用户是否已经登录，如果已经登录了，那么就显示用户的名字和下拉的
    //内容，
    window.onload = function () {
        let show = ''
        $.ajax({
            url: 'users/getLoginUser',
            method: 'GET',
            dataType: 'JSON',
            success(data) {

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

     $('.searchimg').on('click',function () {
        console.log("图标被点击啦!")
        let value= $('.searchinput').val();
        console.log("内容是-->"+value)
        if(value!=''){
            let t="search?dishName="+value;
            window.location.href=t
        }
        return false;
    })

    $('.tt').on('click',function () {
        console.log("图标被点击啦!")
        let value= $('.searchinput').val();
        console.log("内容是-->"+value)
        if(value!=''){
            let t="search?dishName="+value;
            window.location.href=t
        }
        return false;
    })

    $('.searchinput').on('keydown',function (e) {
       /* console.log("e.keyCode"+e.keyCode)*/
        if(e.keyCode==13){
            let value= $('.searchinput').val();
            if(value!=''){
                window.location.href=`search?dishName=${value}`
            }
            return false;
        }

    })


    $('#isLogin').on('click','#logout',function () {
        console.log("正在退出....")
        $.ajax({
            url:'users/logout',
            dataType:'JSON',
            success(data){
                if(data.result){

                    toast(data.msg+" 3秒之后自动跳转",'success')
                    setTimeout(
                        function () {
                            window.location.href="index"
                        },3000)

                }else {
                    setTimeout(toast(data.msg))
                }
            }
        })
    }

    )


})()