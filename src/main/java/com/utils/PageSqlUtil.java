package com.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description:
 * @author: JIll Wang
 * @create: 2020-08-07 22:29
 **/
@Data
@ToString
public class PageSqlUtil {
    private int startPage;
    private int itemNum;
    private String id;
    @Setter @Getter
    private int type;
    private String text;
    private int sortord;

    public static PageSqlUtil getInstance(Page page) {
        PageSqlUtil pageSqlUtil = new PageSqlUtil();
        pageSqlUtil.setStartPage((page.getCurrPage() - 1) * page.getPageSize());
        pageSqlUtil.setItemNum(page.getPageSize());
        pageSqlUtil.setId(page.getId());
        pageSqlUtil.setType(page.getType());
        pageSqlUtil.setText(page.getText());
        pageSqlUtil.setSortord(page.getSortord());
        return pageSqlUtil;
    }


}
