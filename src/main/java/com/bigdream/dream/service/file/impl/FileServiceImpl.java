package com.bigdream.dream.service.file.impl;

import com.bigdream.dream.base.msg.ObjectRestResponse;
import com.bigdream.dream.service.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-31 16:16
 **/
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public ObjectRestResponse upLoadFile(MultipartFile file) {
        try {
            if (file.isEmpty()){
                return new ObjectRestResponse().status(301).message("文件为空");
            }
            String filename = file.getOriginalFilename();
            String suffixName = filename.substring(filename.lastIndexOf("."));
            log.info("上传的文件名称：filename:{}",filename+"后缀名：suffixName:{}",suffixName);
            String filePath="D:\\resource";
            String path=filePath+filename;
            File generatorFile = new File(path);
            // 检测是否存在目录
            if (generatorFile.getParentFile().exists()){
                generatorFile.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(generatorFile);// 文件写入
            return new ObjectRestResponse().status(200).message("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ObjectRestResponse().status(301).message("上传失败");
    }
}
