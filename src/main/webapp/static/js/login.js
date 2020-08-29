
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
(function f() {
    //1：正确      0：错误
    let flag_username = 0
    let flag_email = 0
    let flag_password = 0
    let flag_repassword = 0

    $("#Mymodal").click(function () {
        $("#new").modal("show")
    })
    //轮播图
    $(document).ready(function () {
        $('#circleContent').carousel({interval: 5000});//每隔5秒自动轮播
    });
    // //将注册、登录、忘记密码进行切换
    $(document).on('click', '.toolbar a[data-target]', function (e) {
        e.preventDefault();
        const target = $(this).data('target');
        $('.widget-box.visible').removeClass('visible');//hide others
        $(target).addClass('visible');//show target
    });

    //判断是否登录成功

    const buttonlogin = document.querySelector('#buttonlogin')
    buttonlogin.onclick = function () {
        const formData = $('#loginform').serialize()
        console.log(formData)
        //JQuery封装的xhr
        setTimeout(function () {
            $.ajax({
                url: 'users/login',//请求的url
                method: 'post',//请求的方式
                data: formData,//发送的数据
                dataType: 'JSON',//返回的数据格式
                success(data) {//请求成功执行的函数
                    console.log(data)
                    if (data.result) {
                        loginOk()
                        toast("登录成功,2秒后自动跳转到首页", "success")
                        setTimeout(function () {
                                //首页的html页面
                                window.location.href = "index"

                            }
                            , 3000)
                    } else {
                        toast(data.msg, "error")

                    }

                },
                error(error) {//请求失败执行的函数
                }
            })
        },1000)

        return false

    }

    function loginOk() {
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
    //在注册页面上，对每一个输入的用户名进行验证，是否在数据库里存在
    $('#userNameid').on('change', function () {
        let userName = $('#userNameid').val();
        const pattern = /^\w{8,12}$/
        if (!pattern.test(userName)) {
            document.querySelector('#nameerror1').innerHTML = '用户名的长度是8~12位（字母数字下划线）';
            $('#nameerror1').css('color','red')
            if($('.userNametip').hasClass('glyphicon-ok')){
                $('.userNametip').removeClass("glyphicon-ok")
            }
            $('.userNametip').addClass("glyphicon-remove")
            $('.userNametip').css('color','red')

            flag_username = 0;
        } else {
            flag_username = 1;
            document.querySelector('#nameerror1').innerHTML = ''
            $('#nameerror1').css('color','green')


            // document.querySelector('.userNametip').classList.remove("glyphicon-ok")
            if($('.userNametip').hasClass('glyphicon-remove')){
                $('.userNametip').removeClass("glyphicon-remove")
            }
            $('.userNametip').addClass("glyphicon-ok")
            $('.userNametip').css('color','green')
            console.log("开始进行验证是否存在该用户啦！")
            //进行到数据库里判断是否有该用户，用户名是唯一的
            $.ajax({
                url: 'users/repeat/name',
                data: {userName},
                dataType: 'JSON',
                method: 'POST',
                success(data) {
                    console.log("data:" + data)
                    if (!data.result) {
                        // setTimeout(function (){
                        //     toast(data.msg, 'error')
                        // },5000)
                        $('#nameerror1').html(data.msg);
                        $('#nameerror1').css('color','red')
                        // document.querySelector('.userNametip').classList.remove("glyphicon-remove")
                        if($('.userNametip').hasClass('glyphicon-ok')){
                            $('.userNametip').removeClass("glyphicon-ok")
                        }
                        $('.userNametip').addClass("glyphicon-remove")
                        $('.userNametip').css('color','red')
                        flag_username = 0;
                    } else {
                        // setTimeout(function (){
                        //     toast(data.msg, 'success')
                        // },5000)
                        $('#nameerror1').html(data.msg);
                        $('#nameerror1').css('color','green')

                        // document.querySelector('.userNametip').classList.remove("glyphicon-ok")
                        if($('.userNametip').hasClass('glyphicon-remove')){
                            $('.userNametip').removeClass("glyphicon-remove")
                        }
                        $('.userNametip').addClass("glyphicon-ok")
                        $('.userNametip').css('color','green')
                        flag_username = 1;

                    }
                }

            })

        }
    })

    //在注册页面上，对每一个输入的邮箱进行验证，是否在数据库里存在
    $('.userMail').on('change', function () {
        let userMail = $('.userMail').val();
        const pattern = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
        if (!pattern.test(userMail)) {
            document.querySelector('#emailerror1').innerHTML = '格式错误，应为xxx@xx.xx';
            $('#emailerror1').css('color','red')
             // document.querySelector('.userNametip').classList.remove("glyphicon-remove")
            if($('.userMailtip').hasClass('glyphicon-ok')){
                $('.userMailtip').removeClass("glyphicon-ok")
            }
            $('.userMailtip').addClass("glyphicon-remove")
            $('.userMailtip').css('color','red')
            flag_email = 0;
        } else {
            flag_email = 1;
            document.querySelector('#emailerror1').innerHTML = ''
            $('#emailerror1').css('color','green')

            // document.querySelector('.userNametip').classList.remove("glyphicon-ok")
            if($('.userMailtip').hasClass('glyphicon-remove')){
                $('.userMailtip').removeClass("glyphicon-remove")
            }
            $('.userMailtip').addClass("glyphicon-ok")
            $('.userMailtip').css('color','green')
            console.log("开始进行验证是否存在该用户啦！")
            //进行到数据库里判断是否有该用户，用户名是唯一的
            $.ajax({
                url: 'users/repeat/mail',
                data: {userMail},
                dataType: 'JSON',
                success(data) {
                    console.log("data:" + data)
                    //为true,就说明存在该邮箱
                    if (!data.result) {
                        // toast(data.msg, 'error')

                        $('#emailerror1').html(data.msg);
                        $('#emailerror1').css('color','red')
                        // document.querySelector('.userNametip').classList.remove("glyphicon-remove")
                        if($('.userMailtip').hasClass('glyphicon-ok')){
                            $('.userMailtip').removeClass("glyphicon-ok")
                        }
                        $('.userMailtip').addClass("glyphicon-remove")
                        $('.userMailtip').css('color','red')

                        flag_email = 0;
                    } else {
                        // toast(data.msg, 'success')

                        $('#emailerror1').html(data.msg);
                        $('#emailerror1').css('color','green')

                        // document.querySelector('.userNametip').classList.remove("glyphicon-ok")
                        if($('.userMailtip').hasClass('glyphicon-remove')){
                            $('.userMailtip').removeClass("glyphicon-remove")
                        }
                        $('.userMailtip').addClass("glyphicon-ok")
                        $('.userMailtip').css('color','green')
                        flag_email = 1;
                    }
                }

            })

        }
    })


    $('#userPasswordid').on('change', function () {
        let userPasswordid = $('#userPasswordid').val();
        const pattern = /^\w{8,12}$/
        if (!pattern.test(userPasswordid)) {
            document.querySelector('.pwderror').innerHTML = '密码的长度是8~12位（字母数字下划线）';
            $('.pwderror').css('color','red')
            if($('.userPasswordtip').hasClass('glyphicon-ok')){
                $('.userPasswordtip').removeClass("glyphicon-ok")
            }
            $('.userPasswordtip').addClass("glyphicon-remove")
            $('.userPasswordtip').css('color','red')
            flag_password = 0;
        } else {
            flag_password = 1;
            document.querySelector('.pwderror').innerHTML = ''


             if($('.userPasswordtip').hasClass('glyphicon-remove')){
                $('.userPasswordtip').removeClass("glyphicon-remove")
            }
            $('.userPasswordtip').addClass("glyphicon-ok")
            $('.userPasswordtip').css('color','green')
        }
    })

    $('#repasswordid').on('change', function () {
        let repasswordid = $('#repasswordid').val();
        let userPasswordid = $('#userPasswordid').val();

        if (repasswordid != userPasswordid) {
            document.querySelector('.repwderror').innerHTML = '密码前后不一致';
            $('.repwderror').css('color','red')
            if($('.userRePasswordtip').hasClass('glyphicon-ok')){
                $('.userRePasswordtip').removeClass("glyphicon-ok")
            }
            $('.userRePasswordtip').addClass("glyphicon-remove")
            $('.userRePasswordtip').css('color','red')
            flag_repassword = 0
        } else {
            flag_repassword = 1
            document.querySelector('.repwderror').innerHTML = ''

            if($('.userRePasswordtip').hasClass('glyphicon-remove')){
                $('.userRePasswordtip').removeClass("glyphicon-remove")
            }
            $('.userRePasswordtip').addClass("glyphicon-ok")
            $('.userRePasswordtip').css('color','green')
        }
    })


    //判断邮箱验证码的长度是否为4
    const emailinfor = document.querySelector('.emailinfor')
    const emailcheckerror = document.querySelector(".emailcheckerror");
    emailinfor.change = function () {
        //获得输入框内的值
        const value = emailinfor.value;
        //获得正则表达式的对象
        const pattern = /^[0-9]{4}$/
        if (!value) {
            emailcheckerror.innerHTML = '邮箱验证码不能为空'
        } else {
            if (pattern.test(value)) {
                emailcheckerror.innerHTML = ''
            } else {
                emailcheckerror.innerHTML = '邮箱验证码是4位！！'
            }
        }
    }
    // document.querySelector('#signup-button').setAttribute("disabled", true)

    //判断是否满足条件，满足条件之后是注册成功
    const signupbutton = document.querySelector('#signup-button')
    const checkboxinfo = document.querySelector(".checkboxinfo")

    signupbutton.onclick = function () {

        //根据输入框里里面的内容不为空，并且错误提示框都不为空，则为正确 然后就注册成功
        // console.log("flag_username==" + flag_username)
        // console.log("flag_password==" + flag_password)
        // console.log("flag_repassword==" + flag_repassword)
        // console.log("flag_email==" + flag_email)
        // console.log("checkboxinfo.checked==" + (checkboxinfo.checked == true))
        if (flag_username === 1 && flag_password === 1 && flag_repassword === 1 && flag_email === 1 && checkboxinfo.checked) {
            // document.querySelector('#signup-button').removeAttribute("disabled")
            //将表单数据进行序列化
            const formData = $('#registerform').serialize();
            console.log(formData)
            let isRegister=false
            //此时是已经满足条件，可以发送注册的请求
               if(!isRegister){
                   isRegister=true
                   $.ajax({
                       url: 'users/register',
                       data: formData,
                       dataType: 'JSON',
                       method: 'POST',
                       success(data) {
                           // 插入成功
                           if (data.result) {
                               $('#registerform')[0].reset()
                               toast(data.msg + " 3秒之后自动跳转", 'success')

                               setTimeout(function () {
                                   window.location.href = "login"
                                   isRegister=false
                               }, 3000)
                           }
                           else {
                               toast(data.msg, 'error')
                               isRegister=false
                           }

                       },
                       error(data){
                           isRegister=false
                           toast('未收到请求','error')

                       }
                   })
               }
          }
        return false;
    }


})()