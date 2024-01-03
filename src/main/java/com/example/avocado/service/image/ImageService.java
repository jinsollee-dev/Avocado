package com.example.avocado.service.image;

import com.example.avocado.dto.profile.ImageResponseDTO;
import com.example.avocado.dto.profile.ImageUploadDTO;

public interface ImageService {

    /**
     * 프로필 사진 upload
     * @param imageUploadDTO file
     * @param username 유저 정보
     */
    void upload(ImageUploadDTO imageUploadDTO, String username);

    /**
     * 이미지 url 조회
     * @param username 유저 정보
     * @return 이미지 url
     */
    ImageResponseDTO findImage(String username);
}
