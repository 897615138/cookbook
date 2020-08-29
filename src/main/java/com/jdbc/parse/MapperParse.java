package com.jdbc.parse;


import com.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wj
 * @create 2020-08-08 增加limit
 */
public class MapperParse {
    private static final Map<String, MapperStatement> statementMap = new HashMap<>();
    private static final Map<String, ResultMap> resultMaps = new HashMap<>();

    static {
        String config =
                Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("config/mapper")).getPath();
        //需要注意这里的路径是否为空
        File file = new File(config);
//        //创建后缀选择器
//        SuffixFileFilter suffixFileFilter = new SuffixFileFilter(".xml");
//        //列出xml文件
//        Collection<File> files = FileUtils.listFiles(file, suffixFileFilter, null);//!!!!
        List<File> files = Arrays.asList(Objects.requireNonNull(file.listFiles((dir, name) -> name.endsWith(".xml"))));
//        System.out.println("文件列表为:" + files);
        SAXReader saxReader = new SAXReader();
        //开始解析所有的xml文件
        files.forEach(f -> {
            try {
                Document document = saxReader.read(f);
                //根标签
                Element rootElement = document.getRootElement();
                String namespace = rootElement.attribute("namespace").getValue().trim();
//                System.out.println("namespace:"+namespace);
                AssertUtil.isBlank(namespace);//namespace不能为空 或者""
                List<Element> resultMapElements = rootElement.elements("resultMap");

                resultMapElements.forEach(e1 -> {  //第一次循环解析resutltMap
                    //如果是resultmap  就new一个resultmao
                    //如果是select之类的 就是一个mapperstatement
                    e1.getName();
//                    System.out.println("标签名:"+name);
                    //解析resultMap标签
                    ResultMap resultMap = new ResultMap();//创建结果集对象用于接收结果集映射
                    String id = e1.attribute("id").getValue().trim();
//                    System.out.println("id:"+id);
                    String className = e1.attribute("class").getValue().trim();
//                    System.out.println("className:"+className);
                    AssertUtil.isAnyBlank(id, className);
                    resultMap.setId(id);//把id值放进去
                    resultMap.setClassname(className);//把className放进去
                    //e1的子标签都是result
                    List<Element> results = e1.elements("result");
                    Map<String, Result> map = new HashMap<>();//列明封装的一条信息
                    //根据列民,封装Map对象
                    results.forEach(r -> {
                        //根据子标签生成result对象
                        Result result = new Result();
                        String property = r.attribute("property").getValue().trim();
                        String column = r.attribute("column").getValue().trim();
                        String javaType = r.attribute("javaType").getValue().trim();
                        AssertUtil.isAnyBlank(property, column, javaType);
                        result.setFieldName(property);
                        result.setColumnName(column);
                        try {
                            Class<?> aClass = Class.forName(javaType);
                            result.setFieldType(aClass);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            throw new RuntimeException("javaType异常");
                        }
                        //以上创建一个result对象
                        //把
                        map.put(column, result);
                    });
                    resultMap.setMap(map);//做好映射
                    resultMaps.put(resultMap.getId(), resultMap);//最终封装的信息是一个id对应的一个结果集
                    //还是存在一个问题,就是必须xml文件里面的resultMap信息必须放在前面,不然会出现错误
//                    System.out.println(resultMap.getId());
                });
                //以上解析的是resultMap

                //开始解析所有的CURD标签   分开查询和修改
                List<Element> elements = rootElement.elements();//所有的子标签
                elements.forEach(e1 -> {
                    if (!"resultMap".equals(e1.getName())) {//映射所有的CRUD标签
                        MapperStatement mapperStatement = new MapperStatement();
                        String id = e1.attribute("id").getValue().trim();
                        //    System.out.println("id"+id);
                        AssertUtil.isAnyBlank(id);
                        //这样其实是类.方法名
                        mapperStatement.setMapperName(namespace + "." + id);//2.设置类.方法名
                        Attribute rs = e1.attribute("resultMap");//防止resultMap为空
//                        System.out.println("rs"+rs);
                        AssertUtil.isNull(rs);
                        //select必须要有映射关系?  select有返回的结果
//                        if ("select".equals(e1.getName())) {
                        String resultMap = rs.getValue().trim();
                        ResultMap map = resultMaps.get(resultMap);//获取查询返回的映射对象
                        if (Objects.isNull(map)) {
                            throw new RuntimeException("resultMap错误");
                        }
                        mapperStatement.setResultMap(map);//设置了select语句的resultMap
//
                        mapperStatement.setSqlType(e1.getName());//  1.sql的类型 根据标签名
                        //还剩下parameterType,sql,fileNames没有解析
                        Attribute parameterType = e1.attribute("parameterType");
                        if (Objects.nonNull(parameterType)) {
                            String trim = parameterType.getValue().trim();
                            mapperStatement.setParameterType(trim);//解析参数类型
                        }
                        //修改8.8
                        //最重要的解析  查询参数   和执行的sql语句
                        //select *  from xxx where =#{}
                        String sql = e1.getTextTrim();//3.sql语句
                        if (StringUtils.isBlank(sql)) {
                            throw new RuntimeException("sql解析异常");
                        }
                        String pattern = "#\\{\\w*}";
                        Matcher matcher = Pattern.compile(pattern).matcher(sql);
                        List<String> fields = new ArrayList<>();
                        while (matcher.find()) {
                            String group = matcher.group();
                            String fieldName = group.substring(2, group.length() - 1);
//                            System.out.println("fieldName:"+fieldName); //4.获得是的sno,也就是查询参数的名字
                            fields.add(fieldName);
                        }
                        /*   System.out.println(fields);*/
                        sql = matcher.replaceAll("?");//3.2替换后的sql语句
                        mapperStatement.setSql(sql);

                        mapperStatement.setFieldNames(fields);//4.查询参数的名字
//                        System.out.println("厕所2"+mapperStatement);
                        statementMap.put(mapperStatement.getMapperName(), mapperStatement);//方法全民放入 值就是mapperStatement
//                        System.out.println("C测试"+statementMap.get(mapperStatement.getMapperName()));
                    }
                });
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
    }


    public static MapperStatement get(String key) {
        return statementMap.get(key);
    }

//   public static void main(String[] args) {
//        statementMap.forEach((k, v) -> {
//            System.out.print(k);
//            System.out.println(v);
//        });
//    }

}
