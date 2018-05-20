package com.zhlzzz.together.match;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


public class MatchEntity implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private Long userId;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private Integer gameTypeId;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private boolean finished = false;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column(nullable = false)
    private boolean deleted = false;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private LocalDateTime createTime;
}
