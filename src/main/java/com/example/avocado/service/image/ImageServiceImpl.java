package com.example.avocado.service.image;


import com.example.avocado.domain.ProductImg;
import com.example.avocado.domain.User;
import com.example.avocado.domain.UserImage;
import com.example.avocado.dto.profile.ImageResponseDTO;
import com.example.avocado.dto.profile.ImageUploadDTO;
import com.example.avocado.repository.ImageRepository;
import com.example.avocado.repository.ProductImgRepository;
import com.example.avocado.repository.UserRepository;
import com.example.avocado.service.FileService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ProductImgRepository productImgRepository;
    private  final FileService fileService;

    @Value("${file.profileImagePath}")
    private String uploadFolder;

    @Value("${file.boardImagePath}")
    private String uploadFolder2;


    @Override
    public void upload(ImageUploadDTO imageUploadDTO, String username) {
        User user = userRepository.findByUsername(username);
        MultipartFile file = imageUploadDTO.getFile();

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + file.getOriginalFilename();

        File destinationFile = new File(uploadFolder + imageFileName);

        try {
            file.transferTo(destinationFile);

            UserImage image = imageRepository.findByUser(user);
            if (image != null) {
                // 이미지가 이미 존재하면 url 업데이트
                image.updateUrl("/profileImages/" + imageFileName);
            } else {
                // 이미지가 없으면 객체 생성 후 저장
                image = UserImage.builder()
                        .user(user)
                        .url("/profileImages/" + imageFileName)
                        .build();
            }
            imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ImageResponseDTO findImage(String username) {
        User user = userRepository.findByUsername(username);
        UserImage image = imageRepository.findByUser(user);

        String defaultImageUrl = "/profileImages/anonymous.png";

        if (image == null) {
            return ImageResponseDTO.builder()
                    .url(defaultImageUrl)
                    .build();
        } else {
            return ImageResponseDTO.builder()
                    .url(image.getUrl())
                    .build();
        }

    }
    // 상품 이미지 수정
    public void updateProductImg(ProductImg productImg, MultipartFile itemImgFile) {
        log.info(productImg.getFno());

        AtomicBoolean isFirstFile = new AtomicBoolean(true);

            // 상품 이미지를 수정했다면
            if (!itemImgFile.isEmpty()) {
                log.info("확인333");
                String originalFileName = itemImgFile.getOriginalFilename();
                log.info(originalFileName);
                //uploadPath=uploadPath+"\\"+getFolder();
                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(uploadFolder2, uuid + "_" + originalFileName);
                String filename = uuid + "_" + originalFileName;
                log.info(filename);
                try {
                    itemImgFile.transferTo(savePath);
                    File thumbFile = new File(uploadFolder2, "s_" + uuid + "_" + originalFileName);
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;

                String imgName = "";
                String imgUrl = "";
                imgUrl = "/images/product/" + filename;
                log.info(imgUrl);

                    ProductImg productImg2 = ProductImg.builder()
                            .fno(productImg.getFno())
                            .product(productImg.getProduct())
                            .filename(filename)
                            .uuid(uuid)
                            .imgUrl(imgUrl)
                            .repimgYn(productImg.getRepimgYn())
                            .build();
                    productImgRepository.save(productImg2);
                }

            };

        }
