package com.zhlzzz.together.match;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "match")
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
    private LocalDateTime createTime;
}
