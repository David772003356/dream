package com.bigdream.dream.service.file;

import com.bigdream.dream.base.msg.ObjectRestResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-31 16:14
 **/
@Service
public interface FileService {

    ObjectRestResponse upLoadFile(MultipartFile file);

}
