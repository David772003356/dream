package com.bigdream.dream.controller.file;

import com.bigdream.dream.base.msg.ObjectRestResponse;
import com.bigdream.dream.service.file.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ObjectRestResponse upLoadFile(@RequestParam("file")MultipartFile file){
       return fileService.upLoadFile(file);
    }
}
