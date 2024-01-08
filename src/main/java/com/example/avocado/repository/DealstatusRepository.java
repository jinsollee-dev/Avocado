package com.example.avocado.repository;



import com.example.avocado.domain.Dealstatus;
import com.example.avocado.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DealstatusRepository extends JpaRepository<Dealstatus, Long> {


    @Query("Select d from Dealstatus d where d.buyer =:buyer")
    List<Dealstatus> findByBuyer(String buyer);


}
