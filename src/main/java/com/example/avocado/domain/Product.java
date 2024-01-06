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
@Table(name = "product")
public class Product extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pno;

  @Column(length = 200, nullable = false)
  private String pname; //상품명
  @Column(nullable = false)
  private String content;
  @Column(nullable = false)
  private Long price;
  @Column(length = 200, nullable = false)
  private String area;
  @Column(length = 200)
  private String hopelocation;
  @Column(nullable = false)
  private String dealmethod;
  @Column(length = 45)
  private String dealstatus;

  private String writer;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "username")
  private User user;

  public void change(ProductDTO productDTO) {
    this.pname = productDTO.getPname();
    this.content = productDTO.getContent();
    this.price = productDTO.getPrice();
    this.area = productDTO.getArea();
    this.hopelocation = productDTO.getHopelocation();
  }
}
