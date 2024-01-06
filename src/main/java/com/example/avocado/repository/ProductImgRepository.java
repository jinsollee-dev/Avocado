package com.example.avocado.repository;

import com.example.avocado.domain.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
  @Query("select p from ProductImg p where p.product.pno=:pno")
  List<ProductImg> findByFileList(Long pno);


  @Query("select p from ProductImg p where p.product.pno=:pno order by p.product.pno")
  List<ProductImg> findByProductOrderByFnoAsc(Long pno);

  @Query("select p from ProductImg p where p.product.pno=:pno")
  List<ProductImg> findByFnobypno(Long pno);
}
