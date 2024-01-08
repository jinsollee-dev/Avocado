package com.example.avocado.service;


import com.example.avocado.domain.ReplyRoom;
import com.example.avocado.dto.PageRequestDTO;
import com.example.avocado.dto.PageResponseDTO;
import com.example.avocado.dto.product.ReplyDTO;

import java.util.List;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    PageResponseDTO<ReplyDTO> getListOfBoard(Long pno, PageRequestDTO pageRequestDTO);

    PageResponseDTO<ReplyDTO> findByPnoAndReplyer(Long pno, String buyer, PageRequestDTO pageRequestDTO);
     List<ReplyRoom> replyelist(Long pno);

}
