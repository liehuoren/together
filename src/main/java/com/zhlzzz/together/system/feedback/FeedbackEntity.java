package com.zhlzzz.together.system.feedback;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="sys_feedback")
public class FeedbackEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(columnDefinition = "TEXT")
    private String content;

    @Getter @Setter
    @Column
    private LocalDateTime createTime;

    @Getter @Setter
    @Column
    private Long userId;

    @Getter @Setter
    @Column(length = 200)
    private String email;
}
