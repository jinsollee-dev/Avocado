package com.example.avocado.controller;


import com.example.avocado.dto.profile.ImageUploadDTO;
import com.example.avocado.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequiredArgsConstructor
@RequestMapping("/images")
@Log4j2
public class ImageController {

    @Value("${file.profileImagePath}")
    private String uploadFolder1;

    @Value("${file.boardImagePath}")
    private String uploadFolder2;

    private final ImageService imageService;


    @PostMapping("/upload")
    public String upload(@ModelAttribute ImageUploadDTO imageUploadDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        imageService.upload(imageUploadDTO, userDetails.getUsername());

        return "redirect:/user/profile";
    }

    @GetMapping("/display/{filename}")
    @ResponseBody
    public ResponseEntity<byte[]> getProfile(@PathVariable("filename") String filename){
        log.info("fileName:"+filename);
        File file = new File(uploadFolder1,filename);
        log.info(file);

        ResponseEntity<byte[]> result=null;
        try{
            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result=new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),headers,
                    HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
        }
        log.info(result);
        return result;
    }


    @GetMapping("/product/{filename}")
    @ResponseBody
    public ResponseEntity<byte[]> getMainFile(@PathVariable("filename") String filename){
        log.info("fileName:"+filename);
        File file = new File(uploadFolder2,filename);
        log.info(file);

        ResponseEntity<byte[]> result=null;
        try{
            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result=new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),headers,
                    HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
        }
        log.info(result);
        return result;
    }
}
