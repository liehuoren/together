package com.zhlzzz.together.match;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "matche")
public class MatchEntity implements Match, Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column(nullable = false)
    private Long userId;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column(nullable = false)
    private Integer gameTypeId;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private String formId;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private String name;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private Integer memberNum;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private Integer minute;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private Range matchRange;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private String otherItem;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private Long roomId;

    @Setter(AccessLevel.PACKAGE)
    @Column
    private boolean finished = false;

    @Setter(AccessLevel.PACKAGE)
    @Column
    private boolean deleted = false;

    @Setter(AccessLevel.PACKAGE)
    @Column
    private boolean closeDown = false;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column(nullable = false)
    private LocalDateTime createTime;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column(nullable = false)
    private LocalDateTime expiration;

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public boolean isDeleted() { return deleted; }

    @Override
    public boolean isCloseDown() { return closeDown; }

    @Override
    public boolean isEffective() {
        return !finished && !deleted && expiration.isAfter(LocalDateTime.now()) && !closeDown;
    }

    public MatchDto toDto() {
        return MatchDto.builder()
                .id(id)
                .userId(userId)
                .formId(formId)
                .name(name)
                .gameTypeId(gameTypeId)
                .matchRange(matchRange)
                .memberNum(memberNum)
                .minute(minute)
                .createTime(createTime)
                .expiration(expiration)
                .finished(finished)
                .deleted(deleted)
                .closeDown(closeDown)
                .effective(isEffective())
                .build();
    }
}
