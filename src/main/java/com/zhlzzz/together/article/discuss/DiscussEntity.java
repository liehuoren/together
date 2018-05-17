package com.zhlzzz.together.article.discuss;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="discuss")
public class DiscussEntity {

    @Getter
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;//评论内容

    @Getter @Setter
    @Column
    private LocalDateTime createTime;//评论时间

    @Getter @Setter
    @Column
    private LocalDateTime modifyTime;//评论时间

    @Getter @Setter
    @Column(nullable = false)
    private int audit;//是否审核

    @Getter @Setter
    @Column(nullable = false)
    private int toTop;//是否置顶

    @Getter @Setter
    @Column
    private  Long articleId;//文章ID

    @Getter @Setter
    @Column
    private  Long userId;//评论用户ID
}
