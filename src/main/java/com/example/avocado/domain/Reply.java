package com.example.avocado.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="reply", indexes = {
        @Index(name="idx_reply_product_pno", columnList="product_pno")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    @ManyToOne(fetch = FetchType.LAZY)
    private ReplyRoom replyRoom;
    private String replyText;
    private String replyer;  //구매자
    private String writer; // 판매자



    public void changeText(String text){
        this.replyText = text;
    }

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="user_id")
    //private MemberEntity member;


}
