package com.zhlzzz.together.article.reply;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="reply")
public class ReplyEntity {

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

    @Getter @Setter
    @Column
    private LocalDateTime modifyTime;

    @Getter @Setter
    @Column
    private  Long articleId;

    @Getter @Setter
    @Column
    private  Long discussId;
}
