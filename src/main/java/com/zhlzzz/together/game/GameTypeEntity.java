package com.zhlzzz.together.game;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_type", uniqueConstraints = {
        @UniqueConstraint(name = "name_udx", columnNames = {"name"})
})
@ToString
public class GameTypeEntity implements GameType, Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter
    @Column(length = 20)
    private String name;

    @Getter @Setter
    @Column(length = 200)
    private String imgUrl;

    @Setter
    @Column
    private Boolean hot = false;

    @Getter @Setter
    @Column
    private LocalDateTime createTime;

    @Override
    public Boolean isHot() {
        return hot;
    }

}
