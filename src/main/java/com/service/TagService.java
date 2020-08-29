package com.service;

import com.entity.TagInfo;
import com.exception.AppException;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 15:34
 * @slogan Just do it continually
 */
public interface TagService {

    List<TagInfo> queryAllTag() throws AppException;

    TagInfo queryTag(TagInfo tag) throws AppException;

}
