package com.example.avocado.domain;

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
  private String hope_location;
  @Column(nullable = false)
  private String deal_method;
  @Column(length = 45)
  private String deal_status;

  private String writer;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "username")
  private User user;

  public void change(String pname, String content, Long price, String area, String hope_location) {
    this.pname = pname;
    this.content = content;
    this.price = price;
    this.area = area;
    this.hope_location = hope_location;
  }
}
