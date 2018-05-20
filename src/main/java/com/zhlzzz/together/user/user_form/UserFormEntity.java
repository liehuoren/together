package com.zhlzzz.together.user.user_form;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_form")
public class UserFormEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column
    private Long userId;

    @Getter @Setter
    @Column
    private String formId;

    @Getter @Setter
    @Column
    private LocalDateTime createTime;
}
