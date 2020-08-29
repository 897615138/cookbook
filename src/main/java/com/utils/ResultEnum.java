package com.utils;

public enum ResultEnum {
    //系統操作
    SUCCESS("操作成功"),
    SYS_ERROR("网络异常,请求稍候重试"),
    SYS_MISTAKE("网络出了小差呢"),
    //登錄
    LOGIN_SUCCESS("登陆成功"),
    LOGIN_ERROR("用户名或者密码错误"),
    LOGIN_WARNING("没有登陆！"),
    //注冊
    REPEAT_USER("用户名重复"),
    REPEAT_MAIL("邮箱重复"),
    USABLE_MAIL("邮箱可用"),
    USABLE_USERNAME("用户名可用"),
    SUCCESS_REGISTER("注册成功"),
    LOGOUT_SUCCESS("登出成功"),
    //更新
    UPDATE_SUCCESS("更新成功"),
    //用戶
    NONE_USER("没有用户"),
    //菜谱
    FIND_SUCCESS("成功查询"),
    NONE_DISH("没有菜谱"),
    DISH_INSERT_FAIL("菜谱插入失败"),
    FIND_ERROR("查询没有结果"),
    DISH_LIKE("以点赞"),
    DISH_UNLIKE("未点赞"),
    DELETE_FAIL("删除失败"),
    DISH_UPDATE_FAIL("菜谱修改失败"),
    //评论
    COMMENT_SUCCESS("有评论!"),
    COMMENT_ERROR("当前菜谱还无评论~"),
    CHILD_COMMENT_ERROR("当前评论还无子评论~"),
    INSERT_COMMENT_SUCCESS("发布评论成功!"),
    INSERT_COMMENT_ERROR("发布评论失败"),
    //收藏夹
    INSERT_SUCCESS("创建成功"),
    FULL_MENU_ERROR("您的收藏夹已经上限了"),
    DELETE_SUCCESS("删除成功"),
    UPDATEMENUNAME_SUCCESS("修改成功"),
    COLLECT_SUCCESS("成功收藏"),
    COLLECT_FAIL("您已收藏该菜谱 请勿重复收藏"),
    FAVORITE_IS_EMPTY("收藏夹为空"),
    //关注与粉丝
    FOLLOW_SUCCESS("关注成功!"),

    FOLLOW_ERROR("暂无关注"),

    UNFOLLOW_SUCCESS("取消关注成功"),

    FAN_ERROR("暂无粉丝"),

    ;

    private String msg;

    ResultEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
