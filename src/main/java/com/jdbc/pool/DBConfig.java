package com.jdbc.pool;
/*
这个类主要是为了封装数据的配置参数备用
 */


import com.utils.AssertUtil;
import lombok.Data;
import lombok.ToString;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author ranxuma
 * @version 1.0
 * @date 2020/7/24 7:29 下午
 */
@Data
@ToString
public class DBConfig {
    private String driver;
    private String url;
    private String userName;
    private String password;
    private Integer maxActive;
    private Integer initialSize;
    private Integer minActive;
    //两个long型的时间量
    private Long idleTimeOut;
    private Long maxWaitTime;


    public static DBConfig parseConfig() {
        DBConfig dbConfig = null;//我们要返回的是DBConfig的对象
        //拿到db文件
        InputStream in = DBConfig.class.getClassLoader().getResourceAsStream("config/pool.xml");
        //使用dom4j解析
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(in);
            //拿到要进行数据配置的类
            Element rootElement = document.getRootElement();
            Element bean = rootElement.element("bean");
            //拿到哪一个配置
            //bean.attribute("id")
            //解析xml的bean标签 拿到一个全类名 用于生产反射对象 用于后期初始化
            Attribute beanName = bean.attribute("class");
            AssertUtil.isNull(bean);//异常判断
            String aClass = beanName.getValue().trim();
            Class<?> aClass1 = Class.forName(aClass);
            //用反射new 一个对象
            dbConfig = (DBConfig) aClass1.getDeclaredConstructor().newInstance();
            List<Element> properties = bean.elements("property");
            //用一个map来装
            HashMap<String, String> configMap = new HashMap<>();
            properties.forEach(e -> {
                Attribute name1 = e.attribute("name");
                AssertUtil.isNull(name1);
                String name = name1.getValue().trim();
                Attribute value1 = e.attribute("value");
                AssertUtil.isNull(value1);
                String value = value1.getValue().trim();
                configMap.put(name, value);
            });
            List<Field> fields = Arrays.asList(aClass1.getDeclaredFields());
            DBConfig finalDbConfig = dbConfig;
            fields.forEach(field -> {
                String name = field.getName();
                Class<?> type = field.getType();
                Object o;
                //set方法
                String setName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                try {
                    Method setMethod = aClass1.getDeclaredMethod(setName, type);
                    String value = configMap.get(name);
                    if (type == String.class) {
                        o = value;
                    } else {
                        //ValueOf方法
                        Method valueOfMethod = type.getDeclaredMethod("valueOf", String.class);
                        o = valueOfMethod.invoke(null, value);
                    }
                    setMethod.invoke(finalDbConfig, o);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });


        } catch (DocumentException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }


        return dbConfig;
    }
}
