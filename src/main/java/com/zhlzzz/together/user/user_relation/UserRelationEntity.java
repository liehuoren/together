package com.zhlzzz.together.user.user_relation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_relation",indexes = {
        @Index(name = "user_idx", columnList = "userId"),
        @Index(name = "to_user_idx", columnList = "toUserId")
})
public class UserRelationEntity implements UserRelation {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column
    private Long userId;

    @Getter @Setter
    @Column
    private Long toUserId;

    @Getter @Setter
    @Column(length = 20)
    private String remark;

    @Getter @Setter
    @Column
    private Relation relation;

    @Getter @Setter
    @Column
    private LocalDateTime updateTime;
}
