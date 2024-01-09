package com.example.avocado.service;

import com.example.avocado.domain.User;
import com.example.avocado.domain.UserImage;
import com.example.avocado.dto.user.UserDTO;
import com.example.avocado.dto.user.UserResponseDTO;
import com.example.avocado.dto.user.UserUpdateDTO;
import com.example.avocado.repository.ImageRepository;
import com.example.avocado.repository.UserRepository;
import com.example.avocado.service.image.ImageService;
import com.example.avocado.service.image.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService{
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);


    @Value("${file.profileImagePath}")
    private String uploadFolder;


    @Transactional
    @Override
    public Long join(UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                .nickname(userDTO.getNickname())
                .name(userDTO.getName())
                .password(userDTO.getPassword())
                .phone(userDTO.getPhone())
                .deleteCheck(true)
                .role("USER")
                .build();


            userRepository.save(user);

            log.info("======파일테스트");
            log.info(userDTO.getFile());

        MultipartFile file = userDTO.getFile();
        String filename = file.getOriginalFilename();
        log.info(filename);

            if(filename == ""){
                UserImage image = UserImage.builder()
                        .url("basicprofile.png")
                        .user(user)
                        .build();

                imageRepository.save(image);
            } else if (filename != ""){

                UUID uuid = UUID.randomUUID();
                String imageFileName = uuid + "_" + file.getOriginalFilename();

                File destinationFile = new File(uploadFolder + imageFileName);

                try {
                    file.transferTo(destinationFile);

                    UserImage image = imageRepository.findByUser(user);

                    if (image != null) {
                        // 이미지가 이미 존재하면 url 업데이트
                        image.updateUrl(imageFileName);
                        imageRepository.save(image);

                    } else {
                        // 이미지가 없으면 객체 생성 후 저장
                        image = UserImage.builder()
                                .user(user)
                                .url(imageFileName)
                                .build();
                        imageRepository.save(image);

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        return userRepository.save(user).getId();
    }

    /* 회원가입 시, 유효성 및 중복 검사 */
    @Transactional(readOnly = true)
    @Override
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    @Override
    public UserResponseDTO findUser(String username) {

        User user = userRepository.findByUsername(username);
        UserImage userImage = imageRepository.findByUser(user);


        UserResponseDTO result = UserResponseDTO.builder()
                .user(user)
                .userImage(userImage)
                .build();

        return result;
    }

    @Override
    public UserResponseDTO findbyWriter(String writer) {
        User user = userRepository.findByNickname(writer);
        UserImage userImage = imageRepository.findByUser(user);

        UserResponseDTO result = UserResponseDTO.builder()
                .user(user)
                .userImage(userImage)
                .build();
        return result;
    }

   @Override
    public void update(String username, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findByUsername(username);
        user.setName(userUpdateDTO.getName());
        user.setNickname(userUpdateDTO.getNickname());
        user.setPassword(userUpdateDTO.getPassword());
        user.setPhone(userUpdateDTO.getPhone());


        log.info("======파일테스트");
        log.info(userUpdateDTO.getFile());

        MultipartFile file = userUpdateDTO.getFile();
        String filename = file.getOriginalFilename();
        log.info(filename);

        if (filename != ""){

            UUID uuid = UUID.randomUUID();
            String imageFileName = uuid + "_" + file.getOriginalFilename();

            File destinationFile = new File(uploadFolder + imageFileName);

            try {
                file.transferTo(destinationFile);

                UserImage image = imageRepository.findByUser(user);

                if (image != null) {
                    // 이미지가 이미 존재하면 url 업데이트
                    image.updateUrl(imageFileName);
                    imageRepository.save(image);

                } else {
                    // 이미지가 없으면 객체 생성 후 저장
                    image = UserImage.builder()
                            .user(user)
                            .url(imageFileName)
                            .build();
                    imageRepository.save(image);

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        userRepository.save(user);

    }

    @Override
    public boolean withdrawal(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (passwordEncoder.matches(password, user.getPassword())) {
            userRepository.delete(user);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<User> userList() {
        return userRepository.findAll();
    }

    @Override
    public void userUpdate(Long id, User user) {
        User tempUser = userRepository.findById(id).get();
        tempUser.setRole(user.getRole());

        userRepository.save(tempUser);
    }

    @Override
    public User finduid(Long id){
        User user = userRepository.findById(id).get();
        return user;
    }

}
