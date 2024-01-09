package com.chord.lsr.controller.admin;

import com.chord.lsr.constant.MessageConstant;
import com.chord.lsr.pojo.result.Result;
import com.chord.lsr.properties.MediaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {

    @Autowired
    private MediaProperties mediaProperties;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);

        // 原始文件名
        String originalName = file.getOriginalFilename();
        String suffix = originalName.substring(originalName.lastIndexOf("."));

        // 使用UUID重新生成文件名，防止重复造成覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        try {
            File dir = new File(mediaProperties.imagePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file.transferTo(new File(mediaProperties.imagePath + fileName));
            log.info("上传到：{}", mediaProperties.imagePath + fileName);

            String url = mediaProperties.url;
            if (!(url.startsWith("http://") || url.startsWith("https://"))) {
                url = "http://" + url;
            }
            if (!url.endsWith("/")) {
                url = url + "/";
            }
            log.info("图片地址：{}", url + fileName);
            return Result.success(url + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
