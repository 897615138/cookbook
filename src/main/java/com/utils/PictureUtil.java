package com.utils;

import com.entity.DishInfo;
import com.entity.PicturesInfo;
import com.entity.UserInfo;
import com.service.imp.PicturesServiceImp;
import net.coobird.thumbnailator.Thumbnails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 图片压缩工具
 * @author: JIll Wang
 * @create: 2020-08-17 22:31
 **/
public class PictureUtil {
    private static final PicturesServiceImp picturesServiceImp;
    static {
        picturesServiceImp=new PicturesServiceImp();
    }


    public static void picCompress(String sourceFile, String desFile) throws IOException {
        Thumbnails.of(sourceFile).size(512, 512).toFile(desFile);
    }

    public static DishInfo getPic(HttpServletRequest req) {
        DishInfo dishInfo = BeanUtil.parseRequest(req, DishInfo.class);
        System.out.println(dishInfo);
        dishInfo.setDishId(EncryptionUtil.uuid());
        PicturesInfo picturesInfo = new PicturesInfo();
        picturesInfo.setDishId(dishInfo.getDishId());
        String dishText = dishInfo.getDishText();
        System.out.println(dishText);
        String img = "img src=";
        Pattern pattern = Pattern.compile(img);
        Matcher matcher = pattern.matcher(dishText);
        ArrayList<Integer> integers = new ArrayList<>();
        while (matcher.find()) {
            integers.add(matcher.start());
        }
        //System.out.println(integers);
        String r = "(?<=\").*?(?=\")";
        Pattern compile = Pattern.compile(r);
        Matcher m = compile.matcher(dishText);
        while (m.find()) {
            //System.out.println("我找到啦！");
            int start = m.start();
            //System.out.println(start);
            if (integers.contains(start - 9)) {
                int end = m.end();
                String pic = dishText.substring(start, end);
                //System.out.println(substring);
                picturesInfo.setPicAddress(pic);
                picturesServiceImp.increasePictures(picturesInfo);
//                PictureUtil.picCompress(pic, pic);
            }
        }
        HttpSession session = req.getSession();
        UserInfo user = (UserInfo) session.getAttribute(ConstEnum.LOGIN_USER.getMsg());
        if (!Objects.isNull(user)) {
            dishInfo.setUserId(user.getUserId());
        }
        return dishInfo;
    }
    public static DishInfo getPic2(HttpServletRequest req) {
        DishInfo dishInfo = BeanUtil.parseRequest(req, DishInfo.class);
        PicturesInfo picturesInfo = new PicturesInfo();
        picturesInfo.setDishId(dishInfo.getDishId());
        String dishText = dishInfo.getDishText();
        System.out.println(dishText);
        String img = "img src=";
        Pattern pattern = Pattern.compile(img);
        Matcher matcher = pattern.matcher(dishText);
        ArrayList<Integer> integers = new ArrayList<>();
        while (matcher.find()) {
            integers.add(matcher.start());
        }
        //System.out.println(integers);
        String r = "(?<=\").*?(?=\")";
        Pattern compile = Pattern.compile(r);
        Matcher m = compile.matcher(dishText);
        while (m.find()) {
            //System.out.println("我找到啦！");
            int start = m.start();
            //System.out.println(start);
            if (integers.contains(start - 9)) {
                int end = m.end();
                String pic = dishText.substring(start, end);
                //System.out.println(substring);
                picturesInfo.setPicAddress(pic);
                picturesServiceImp.increasePictures(picturesInfo);
//                PictureUtil.picCompress(pic, pic);
            }
        }
        HttpSession session = req.getSession();
        UserInfo user = (UserInfo) session.getAttribute(ConstEnum.LOGIN_USER.getMsg());
        if (!Objects.isNull(user)) {
            dishInfo.setUserId(user.getUserId());
        }
        return dishInfo;
    }
    public static void compressAndSizeWidth(InputStream src, OutputStream target, float quality, int width, String format) throws IOException {
        Thumbnails.of(src).width(width).keepAspectRatio(true)
                .outputFormat(format)
                .outputQuality(quality).toOutputStream(target);
    }

    //    public static void compressPic(File executeFile){
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Image srcImage = toolkit.getImage(executeFile.getAbsolutePath());
//        int width = -1;
//        int height = -1;
//        boolean flag = true;
//        //Toolkit加载是异步的,它有一个观察器,要等待它回加载完成才能再draw出去。
//        while (flag) {
//            width = srcImage.getWidth(null); // 得到源图宽
//            height = srcImage.getHeight(null); // 得到源图长
//            if (width > 0 && height > 0) {
//                flag = false;
//            } else {
//                try {
//                    Thread.sleep(100);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }}
//        BufferedImage bufferedImage = new BufferedImage(300, 400, BufferedImage.TYPE_INT_RGB);
//        Graphics graphics = bufferedImage.getGraphics();
//
//        graphics.drawImage(srcImage,0,0,300,400,null);
//        NativeString suffix = null;
//        ImageIO.write(bufferedImage,suffix.substring(1, NativeString.length()),executeFile1);
//        srcImage.flush();
//        bufferedImage.flush();
//        graphics.dispose();
    public static void main(String[] args) {
        try {
            PictureUtil.picCompress("target/project_cookbook/static/images/bg.jpg",
                    "target/project_cookbook/static/images/bg.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
