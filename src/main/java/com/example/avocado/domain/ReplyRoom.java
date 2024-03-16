package com.example.avocado.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "replyroom")
@Getter
@Setter
@ToString
public class ReplyRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long rid;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pno")
    private Product product;  //product_pno

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    public static ReplyRoom createReplyRoom(Product product, User user) {
        ReplyRoom replyRoom = new ReplyRoom();
        replyRoom.setProduct(product);
        replyRoom.setUser(user);
        return replyRoom;
    }
}
