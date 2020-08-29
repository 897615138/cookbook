package com.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/*
wj bean封装
wj20200806
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagInfo implements Serializable {
    //标签id
    private String tagId;
    //标签名称
    private String tagName;
    //标签简介
    private String tagIntro;
    //标签是否逻辑删除
    private Integer tagDelete;

//     private static final long serialVersionUID = 6L;
}