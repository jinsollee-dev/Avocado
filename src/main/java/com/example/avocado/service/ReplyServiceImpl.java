package com.example.avocado.service;


import com.example.avocado.domain.Product;
import com.example.avocado.domain.Reply;
import com.example.avocado.domain.ReplyRoom;
import com.example.avocado.domain.User;
import com.example.avocado.dto.PageRequestDTO;
import com.example.avocado.dto.PageResponseDTO;
import com.example.avocado.dto.product.AnswerReplyDTO;
import com.example.avocado.dto.product.ReplyDTO;
import com.example.avocado.repository.ProductRepository;
import com.example.avocado.repository.ReplyRepository;
import com.example.avocado.repository.ReplyRoomRepository;
import com.example.avocado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ReplyRoomRepository replyRoomRepository;
    private final UserRepository userRepository;


    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = modelMapper.map(replyDTO, Reply.class);
        User user = userRepository.findByNickname(replyDTO.getReplyer());
        Product product = productRepository.findByPno(replyDTO.getPno());
        ReplyRoom replyRoom = replyRoomRepository.findByReplyerandPno(user, replyDTO.getPno());

        // replyroom이 존재하지 않는다면 생성
        if (replyRoom == null) {
            replyRoom = ReplyRoom.createReplyRoom(product, user);
            replyRoomRepository.save(replyRoom);
        }
        reply.setWriter(product.getWriter());
        reply.setReplyRoom(replyRoom);
        Long rno = replyRepository.save(reply).getRno();

        return rno;
    }

    @Override
    public ReplyDTO read(Long rno) {
        Optional<Reply> result = replyRepository.findById(rno);
        Reply reply = result.get();
        ReplyDTO dto = modelMapper.map(reply, ReplyDTO.class);
        return dto;
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        Reply reply = replyRepository.findById(replyDTO.getRno()).get();
        reply.changeText(replyDTO.getReplyText());
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);

    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long pno, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.listOfBoard(pno, pageable);
        List<ReplyDTO> dtoList = result.getContent().stream()
                .map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<ReplyDTO> findByPnoAndReplyer(Long pno, String buyer, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.findByPnoAndReplyer(pno, buyer, pageable);
        List<ReplyDTO> dtoList = result.getContent().stream()
                .map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<ReplyDTO> findByPnoAndReplyerAndSeller(Long pno, String repleyr, String writer, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.findByPnoAndReplyerOrwriter(pno, repleyr, writer, pageable);
        List<ReplyDTO> dtoList = result.getContent().stream()
                .map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }




    public List<ReplyRoom> replyelist(Long pno){
        List<ReplyRoom> replyRooms = replyRoomRepository.findByPno(pno);
        return  replyRooms;

    }



    @Override
    public Long registeranswer(AnswerReplyDTO answerReplyDTO) {
        Reply reply = modelMapper.map(answerReplyDTO, Reply.class);
        User user = userRepository.findByNickname(answerReplyDTO.getReplyer());
        Product product = productRepository.findByPno(answerReplyDTO.getPno());
        ReplyRoom replyRoom = replyRoomRepository.findById(answerReplyDTO.getRid()).get();

        reply.setWriter(product.getWriter());
        reply.setReplyRoom(replyRoom);
        Long rno = replyRepository.save(reply).getRno();

        return rno;
    }

 @Override
    public PageResponseDTO<ReplyDTO> findByrid(Long rid, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.findByRid(rid, pageable);
        List<ReplyDTO> dtoList = result.getContent().stream()
                .map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();

    }
}


