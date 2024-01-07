package com.example.avocado.service;


import com.example.avocado.dto.PageRequestDTO;
import com.example.avocado.dto.PageResponseDTO;
import com.example.avocado.dto.product.ReplyDTO;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    PageResponseDTO<ReplyDTO> getListOfBoard(Long pno, PageRequestDTO pageRequestDTO);

    PageResponseDTO<ReplyDTO> findByPnoAndReplyer(Long pno, String buyer, String writer, PageRequestDTO pageRequestDTO);
}
