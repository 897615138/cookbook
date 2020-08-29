package com.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页 封装对象
 */
@ToString
public class Page {
    @Getter
    @Setter
    private int currPage;//当前页
    @Getter
    @Setter
    private int pageSize;//一页显示的条数
    @Getter
    @Setter
    private int totalSize;//总条数
    @Setter
    private int totalPage;//总页数
    @Setter @Getter
    private String id;
    @Setter @Getter
    private int type;
    @Setter @Getter
    private int sortord;

    @Setter @Getter
    private String text;
    {
        this.currPage = 1;
        this.pageSize = 6;
    }
    //获取总页数  只需要 总条数  和  页数多少
    public int getTotalPage() {
        return totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
    }
}
