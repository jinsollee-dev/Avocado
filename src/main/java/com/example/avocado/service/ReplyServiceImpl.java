package com.example.avocado.service;


import com.example.avocado.domain.Product;
import com.example.avocado.domain.Reply;
import com.example.avocado.dto.PageRequestDTO;
import com.example.avocado.dto.PageResponseDTO;
import com.example.avocado.dto.product.ReplyDTO;
import com.example.avocado.repository.ProductRepository;
import com.example.avocado.repository.ReplyRepository;
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
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;


    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = modelMapper.map(replyDTO, Reply.class);
        Product product = productRepository.findByPno(replyDTO.getPno());

        reply.setProduct(product);
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
                pageRequestDTO.getPage() <= 0 ? 0:pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());

        Page<Reply> result=replyRepository.listOfBoard(pno, pageable);
        List<ReplyDTO> dtoList=result.getContent().stream()
                        .map(reply-> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<ReplyDTO> findByPnoAndReplyer(Long pno, String buyer, String writer, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() <= 0 ? 0:pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("rno").descending());

        Page<Reply> result=replyRepository.findByPnoAndReplyer(pno, buyer, writer, pageable);
        List<ReplyDTO> dtoList=result.getContent().stream()
                .map(reply-> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
      }
}