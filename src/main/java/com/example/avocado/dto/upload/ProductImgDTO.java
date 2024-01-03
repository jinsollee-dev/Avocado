package com.example.avocado.dto.upload;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
@Builder
public class ProductImgDTO {
    private String title;
    private String content;
    private List<MultipartFile> files;
}
