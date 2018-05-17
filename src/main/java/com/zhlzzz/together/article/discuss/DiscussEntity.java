package com.zhlzzz.together.article.discuss;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="discuss")
@ToString
public class DiscussEntity implements Discuss,Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Getter @Setter
    @Column
    private LocalDateTime createTime;

    @Setter
    @Column(nullable = false)
    private Boolean audit = false;

    @Override
    public Boolean isAudit() {
        return audit;
    }

    @Setter
    @Column(nullable = false)
    private Boolean toTop = false;

    @Override
    public Boolean isToTop() {
        return toTop;
    }

    @Getter @Setter
    @Column
    private  Long articleId;

    @Getter @Setter
    @Column
    private  Long userId;
}
