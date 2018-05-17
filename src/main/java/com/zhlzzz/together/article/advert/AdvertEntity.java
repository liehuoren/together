package com.zhlzzz.together.article.advert;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="advert")
public class AdvertEntity {

    @Getter
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(length = 200)
    private String advertUrl;

    @Getter @Setter
    @Column
    private int isAvailable;

    @Getter @Setter
    @Column
    private LocalDateTime createTime;

    @Getter @Setter
    @Column
    private LocalDateTime modifyTime;

    @Getter @Setter
    @Column
    private  Long articleId;

}
