package com.example.avocado.service.image;


import com.example.avocado.domain.User;
import com.example.avocado.domain.UserImage;
import com.example.avocado.dto.image.ImageResponseDTO;
import com.example.avocado.dto.image.ImageUploadDTO;
import com.example.avocado.repository.ImageRepository;
import com.example.avocado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Value("${file.profileImagePath}")
    private String uploadFolder;

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
}
