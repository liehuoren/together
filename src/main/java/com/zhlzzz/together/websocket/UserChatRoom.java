package com.zhlzzz.together.websocket;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_cart_room", uniqueConstraints = {
        @UniqueConstraint(name = "openId_udx", columnNames = {"openId"})
})
public class UserChatRoom {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column
    private String openId;

    @Getter @Setter
    @Column
    private String roomId;

    @Getter @Setter
    @Column
    private Long matchId;

}
