package com.zhlzzz.together.chat_room;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chat_room")
public class ChatRoom {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column
    @Convert(converter = String.class, attributeName = ",")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "open_ids")
    private Set<String> openIds = new HashSet<String>();

    @Getter @Setter
    @Column
    private String name;

    @Getter @Setter
    @Column
    private LocalDateTime createTime;

}
