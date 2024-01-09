package com.example.avocado.repository;



import com.example.avocado.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r where r.replyRoom.product.pno=:pno")
    Page<Reply> listOfBoard(Long pno, Pageable pageable);

    @Query("SELECT r FROM Reply r WHERE r.replyRoom.product.pno =:pno and r.replyer=:buyer")
    Page<Reply> findByPnoAndReplyer(Long pno, String buyer, Pageable pageable);

    @Query("SELECT r FROM Reply r WHERE r.replyRoom.product.pno =:pno and (r.replyer=:buyer or r.writer=:writer)")
    Page<Reply>   findByPnoAndReplyerOrwriter(Long pno, String buyer, String writer, Pageable pageable);

    @Query("SELECT r FROM Reply r WHERE r.replyRoom.rid =:rid")
    Page<Reply>   findByRid(Long rid, Pageable pageable);



}
