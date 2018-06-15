package com.zhlzzz.together.rank;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rank",indexes = {
        @Index(name = "user_idx", columnList = "userId")
})
public class Rank {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column
    private Long userId;

    @Getter @Setter
    @Column(nullable = false)
    private String nickname;

    @Getter @Setter
    @Column
    private String area;

    @Getter @Setter
    @Column
    private Double rating;

    @Getter @Setter
    @Column
    private Double kd;

    @Getter @Setter
    @Column
    private LocalDateTime updateTime;

}
