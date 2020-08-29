package com.dao;

import com.entity.TagInfo;

import java.util.List;

public interface ITagInfoMapper {

    /**
     * 查询所有标签 wj
     *
     * @return List
     */
    List<TagInfo> queryAllTag();

    /**
     * 通过id查询标签 wj
     *
     * @param tag 菜
     * @return List
     */
    List<TagInfo> queryTag(TagInfo tag);

}
