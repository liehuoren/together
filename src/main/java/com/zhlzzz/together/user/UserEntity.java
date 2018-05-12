package com.zhlzzz.together.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "openId_udx", columnNames = {"openId"})
})
public class UserEntity implements User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column(length = 20,nullable = false)
    private String openId;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column(length = 20)
    private String unionId;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column(length = 50)
    private String nickName;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column(length = 200)
    private String avatarUrl;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private Integer gender;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private LocalDateTime createTime;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private LocalDateTime lastLoginTime;
}
