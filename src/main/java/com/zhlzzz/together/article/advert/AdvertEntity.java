package com.zhlzzz.together.article.advert;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="advert")
public class AdvertEntity implements Advert,Serializable{

    @Getter
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(length = 200)
    private String advertUrl;

    @Setter
    @Column
    private boolean available = false;
    public Boolean isAvailable(){return available;}

    @Getter @Setter
    @Column
    private LocalDateTime createTime;

    @Getter @Setter
    @Column
    private  Long articleId;

}
