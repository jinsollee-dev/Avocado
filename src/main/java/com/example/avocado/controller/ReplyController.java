package com.example.avocado.controller;


import com.example.avocado.domain.User;
import com.example.avocado.dto.PageRequestDTO;
import com.example.avocado.dto.PageResponseDTO;
import com.example.avocado.dto.product.ProductDTO;
import com.example.avocado.dto.product.ReplyDTO;
import com.example.avocado.dto.user.UserResponseDTO;
import com.example.avocado.repository.UserRepository;
import com.example.avocado.service.ProductService;
import com.example.avocado.service.ReplyService;
import com.example.avocado.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final UserService userService;
    private final ProductService productService;


    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@Valid @RequestBody ReplyDTO replyDTO,
                                                      BindingResult bindingResult)
            throws BindException {
        log.info("댓글입력확인");
        log.info(replyDTO);
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }
        Map<String, Long> resultMap = new HashMap<>();
        Long rno = replyService.register(replyDTO);
        resultMap.put("rno", rno);
        return resultMap;
    }


    //GET 선택 -> localhost:8086/replies/list/103
    @GetMapping("/list/{pno}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("pno") Long pno,
                                             PageRequestDTO pageRequestDTO,
                                             Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserResponseDTO userResponseDTO = userService.findUser(userDetails.getUsername());
        String buyer = userResponseDTO.getNickname();
        ProductDTO productDTO =productService.getProductDetail(pno);
        String writer = productDTO.getWriter();
        PageResponseDTO<ReplyDTO> responseDTO = replyService.findByPnoAndReplyer(pno, buyer, writer, pageRequestDTO);
        return responseDTO;
    }

    @GetMapping("/{rno}")
    public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno){
        log.info(rno);
        ReplyDTO replyDTO = replyService.read(rno);
        log.info(replyDTO);
        return replyDTO;
    }

    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modify(@PathVariable("rno") Long rno, @RequestBody ReplyDTO replyDTO){
        replyDTO.setRno(rno);
        replyService.modify(replyDTO);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", rno);
        return resultMap;
    }


    @DeleteMapping("/{rno}")
    public Map<String, Long> remove(@PathVariable("rno") Long rno){
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", rno);
        replyService.remove(rno);
        return resultMap;
    }
}
