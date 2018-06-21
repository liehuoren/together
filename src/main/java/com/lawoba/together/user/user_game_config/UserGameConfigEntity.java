package com.zhlzzz.together.user.user_game_config;

import com.zhlzzz.together.match.Match;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_game_config",indexes = {
        @Index(name = "user_idx", columnList = "userId"),
        @Index(name = "game_type_idx", columnList = "gameTypeId")
})
public class UserGameConfigEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(nullable = false)
    private Long userId;

    @Getter @Setter
    @Column(nullable = false)
    private Integer gameTypeId;

    @Getter @Setter
    @Column(length = 100, nullable = false)
    private String nickname;

    @Getter @Setter
    @Column
    private String contact;

    @Getter @Setter
    @Column
    private String area;

    @Getter @Setter
    @Column
    private Integer memberNum;

    @Getter @Setter
    @Column
    private Integer minute;

    @Getter @Setter
    @Column
    private Match.Range matchRange;

    @Getter @Setter
    @Column
    private String otherItem;
}
