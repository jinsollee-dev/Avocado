package com.example.avocado.domain;

import com.example.avocado.dto.product.ProductDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dealstatus")
public class Dealstatus extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long dno;

  @Column(length = 200, nullable = false)
  private String pname; //상품명

  private Long pno;

  private String seller;

  private String buyer;



}
