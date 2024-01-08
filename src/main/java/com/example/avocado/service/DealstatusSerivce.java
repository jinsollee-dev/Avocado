package com.example.avocado.service;


import com.example.avocado.domain.Dealstatus;
import com.example.avocado.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DealstatusSerivce {


    private final UserRepository userRepository;
    private final ProductRepository repository;
    private final DealstatusRepository dealstatusRepository;


    public List<Dealstatus> findmylist(String buyer){
        return  dealstatusRepository.findByBuyer(buyer);

    }

}
