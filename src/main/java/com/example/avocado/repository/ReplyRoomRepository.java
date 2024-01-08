package com.example.avocado.repository;


import com.example.avocado.domain.ReplyRoom;
import com.example.avocado.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRoomRepository extends JpaRepository<ReplyRoom,Long> {

    @Query("select m from ReplyRoom m where m.user =:replyer")
    ReplyRoom findByReplyer(String replyer);

    @Query("select m from ReplyRoom m where m.product.pno=:pno")
    List<ReplyRoom> findByPno(Long pno);
    @Query("select m from ReplyRoom m where m.user =:user AND m.product.pno=:pno")
    ReplyRoom findByReplyerandPno(User user, Long pno);





}
