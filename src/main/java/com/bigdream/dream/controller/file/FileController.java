package com.bigdream.dream.controller.file;

import com.alibaba.fastjson.JSONObject;
import com.bigdream.dream.base.msg.ObjectRestResponse;
import com.bigdream.dream.service.file.impl.FileServiceImpl;
import com.bigdream.dream.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @description: 文件
 * @author: Wu Yuwei
 * @create: 2020-07-31 16:13
 **/
@RequestMapping("dream/file")
@RestController
public class FileController {

    @Autowired
    private FileServiceImpl fileService;

    /**
    * @Description: 文件上传
    * @Author: Wu Yuwei
    * @Date: 2020/8/6 14:54
    */
    @PostMapping("/upLoadFile")
    @ResponseBody
    public ObjectRestResponse upLoadFile(@RequestParam("file")MultipartFile file){
       return fileService.upLoadFile(file);
    }

    /**
    * @Description: 文件下载
    * @Author: Wu Yuwei
    * @Date: 2020/8/6 14:54
    */
    @PostMapping("/downLoadFile")
    @ResponseBody
    public HttpServletResponse downLoadFile(@RequestBody JSONObject jsonObject, HttpServletResponse response){
        try {
            // path是指欲下载的文件的路径。
            String path = (String) jsonObject.get("path");
            if (StringUtils.isEmpty(path)){
                return null;
            }
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
