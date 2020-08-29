package com.service.imp;

import com.dao.ITagInfoMapper;
import com.entity.TagInfo;
import com.exception.AppException;
import com.jdbc.parse.MapperProxy;
import com.service.TagService;
import com.utils.ResultEnum;

import java.util.List;

/**
 * @Author WonderFour
 * @Date 2020/8/8 15:35
 * @slogan Just do it continually
 */
public class TagServiceImp implements TagService {

    private static final MapperProxy<ITagInfoMapper> iTagInfoMapperMapperProxy = new MapperProxy<>();
    private static final ITagInfoMapper mapper = iTagInfoMapperMapperProxy.getMapper(ITagInfoMapper.class);


    @Override
    public List<TagInfo> queryAllTag() throws AppException {
        System.out.println("开始查询数据库");
        List<TagInfo> tagInfos = mapper.queryAllTag();
        System.out.println(tagInfos);
        if (tagInfos.isEmpty()) {
            throw new AppException(ResultEnum.FIND_ERROR);
        }
        System.out.println("查询结束");
        return tagInfos;
    }

    @Override
    public TagInfo queryTag(TagInfo tag) throws AppException {
        System.out.println("进入queryTag");
        List<TagInfo> tagInfos = mapper.queryTag(tag);
        if (tagInfos.isEmpty()) {
            throw new AppException(ResultEnum.FIND_ERROR);
        } else {
            return tagInfos.get(0);
        }
    }

//    public static void main(String[] args) {
//        TagInfo tagInfo = new TagInfo();
//        tagInfo.setTagId("1");
//        List<TagInfo> tagInfos = mapper.queryTag(tagInfo);
//        System.out.println(tagInfos.get(0));
//    }


    //查询所有标签测试(成功)
    /*public static void main(String[] args) {
        List<TagInfo> tagInfos = mapper.queryAllTag();
        for (TagInfo tagInfo : tagInfos) {
            System.out.println(tagInfo);
        }
    }*/


    //根据标签查询菜谱名称(成功)
   /* public static void main(String[] args) {
        TagInfo tagInfo = new TagInfo();
        tagInfo.setTagId("22222222222");
        List<TagInfo> tagInfos = mapper.queryTag(tagInfo);
        for (TagInfo info : tagInfos) {
            System.out.println(info);
        }
    }*/
}
