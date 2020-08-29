package com.filter;

import com.utils.ConstEnum;
import com.utils.FileUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ranxuma
 * @version 1.0
 * @date 2020/8/17 4:54 下午
 */
@WebFilter({"/*"})
public class AppFilter extends HttpFilter {
    /*
    过滤规则:
    1. 所有页面默认放行某些页面不放行
    2.如果是黑名单 登陆的正常运行 不登陆的跳转
    3. 如果登陆 要访问登陆黑名单直接进行跳转

     */
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //拿到每一个path
        String servletPath = req.getServletPath();
        System.out.println(servletPath + "被拦截--");
        //用户的登陆状态
        Object attribute = req.getSession().getAttribute(ConstEnum.LOGIN_USER.getMsg());
        //如果登陆
        if (attribute != null) {
            //如果登陆了 并且在登陆黑名单中 那么直接给他跳转
            if (pathFilter(servletPath, FileUtil.loginBlackList)) {
                res.sendRedirect("index");
            } else {
                if (pathFilter(servletPath, FileUtil.allHtmlList)) {
                    req.getRequestDispatcher(servletPath + ".html").forward(req, res);
                } else {
                    chain.doFilter(req, res);
                }
            }


            //如果登陆了并且不是登陆黑名单,那么直接内部跳转 所有可以访问的list +html进行访问
            //其他放行
            //如果未登录

        } else {
            //如果未登录并且是黑名单 直接给他送到login
            if (pathFilter(servletPath, FileUtil.blackList)) {
                res.sendRedirect("login");
            } else {
                if (pathFilter(servletPath, FileUtil.allHtmlList)) {
                    req.getRequestDispatcher(servletPath + ".html").forward(req, res);
                } else {
                    chain.doFilter(req, res);
                }
            }

            //如果未登录并且是List里面 那么 +html进行访问
            //如果未登录并且不是黑名单 放行
        }
    }


    /**
     * 过滤文件path系统
     *
     * @param path 所要过滤的文件path
     * @return 返回 是否以这个结尾
     */
    public static Boolean pathFilter(String path, List<String> filter) {
        for (String s : filter) {
            if (path.startsWith(s)) { //只要找到一个path包含list中的任何一个后缀结尾或者包含它那么就返回true 不需要过滤
                return true;
            }
        }
        return false;
    }


}
