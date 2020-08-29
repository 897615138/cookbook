package com.controller;

import com.alibaba.fastjson.JSON;
import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.PathFormat;
import com.utils.JSONUtil;
import com.utils.PictureUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@WebServlet({"/ueditor", "/ueditor/uploadImage"})
public class UEditorServlet extends HttpServlet {
    //服务器关闭之后丢失
    private static final String savePath = "jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}";

    public UEditorServlet() {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder path0 = new StringBuilder(req.getServletContext().getRealPath("/"));
        path0.append("static\\ueditor");
        String action = req.getParameter("action");

        //上传图片 图片回显
        if ("uploadimage".equals(action)) {
            picBack(req, resp);
        } else {
            String exec = new ActionEnter(req, path0.toString(), "/config/config.json").exec();
            System.out.println(exec);
            JSONUtil.write(JSON.parse(exec), resp);
        }
    }
//
//    private void insertDish(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        //StringBuilder path0 = new StringBuilder(req.getServletContext().getRealPath("/"));
//        //String exec = new ActionEnter(req, path0.toString(), "/config/config.json").exec();
//        HttpSession session = req.getSession();
//        UserInfo user = (UserInfo) session.getAttribute(ConstEnum.LOGIN_USER.getMsg());
//        ResultEntity resultEntity;
//        DishInfo dishInfo = new DishInfo();
//        dishInfo.setUserId(user.getUserId());
//        dishInfo.setDishId(EncryptionUtil.uuid());
//
//        try {
//            dishServiceImp.dishInsert(dishInfo);
//            resultEntity = ResultEntity.SUCCESS(ResultEnum.SUCCESS);
//        } catch (AppException e) {
//            resultEntity=ResultEntity.ERROR(e);
//            e.printStackTrace();
//        }
//        System.out.println(resultEntity);
//        JSONUtil.write(resultEntity,resp);
//    }

    private void picBack(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        resp.setCharacterEncoding("utf-8");
        String save = "";
        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            save = PathFormat.parse(savePath) + ".png";
            String finalSave = PathFormat.format(req.getServletContext()
                    .getRealPath("/") + PathFormat.format(save));

            fileItems.forEach(el -> {
                try {
                    File file = new File(finalSave);
                    String parent = file.getParent();
                    File file1 = new File(parent);
                    if (!file1.exists()) {
                        //多级创建文件夹
                        boolean mkdir = file1.mkdirs();
                        if (mkdir) {
                            System.out.println("创建文件夹成功");
                        }
                    }
                    IOUtils.copy(el.getInputStream(), new FileOutputStream(file));
                    String path = file.getPath();
                    PictureUtil.picCompress(path,path);
                    el.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        JSONUtil.writeBase(save, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
