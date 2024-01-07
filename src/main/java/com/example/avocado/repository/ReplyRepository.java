package com.example.avocado.repository;



import com.example.avocado.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r where r.product.pno=:pno")
    Page<Reply> listOfBoard(Long pno, Pageable pageable);

    @Query("SELECT r FROM Reply r WHERE r.product.pno = :pno AND (r.replyer = :buyer OR r.replyer = :writer)")
    Page<Reply> findByPnoAndReplyer(@Param("pno") Long pno,
                                                  @Param("buyer") String buyer,
                                                  @Param("writer") String writer,
                                                  Pageable pageable);

}
