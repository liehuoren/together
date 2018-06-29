package com.lawoba.together.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "openId_udx", columnNames = {"openId"})
})
public class UserEntity implements User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column(length = 20)
    private String phone;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private String openId;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private String unionId;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private Role role;

    @Column
    private String nickName;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private String avatarUrl;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private String qRCode;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private Integer gender;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private Integer creditScore;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private LocalDateTime createTime;

    @Getter @Setter(AccessLevel.PACKAGE)
    @Column
    private LocalDateTime lastLoginTime;

    @Override
    public boolean isAdmin() { return this.role == Role.admin; }

    public void setNickName(String nickName) {
        Base64.Encoder encoder = Base64.getEncoder();
        try {
            byte[] textByte = nickName.getBytes("UTF-8");
            this.nickName = encoder.encodeToString(textByte);
        } catch (IOException e) {

        }
    }

    @Override
    public String getNickName() {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            return new String(decoder.decode(nickName), "UTF-8");
        } catch (IOException e) {

        }
        return nickName;
    }

    public String getRealNickName() {
        return nickName;
    }

    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .phone(phone)
                .nickName(getNickName())
                .openId(openId)
                .unionId(unionId)
                .gender(gender)
                .creditScore(creditScore)
                .avatarUrl(avatarUrl)
                .qRCode(qRCode)
                .avatarUrl(avatarUrl)
                .lastLoginTime(lastLoginTime)
                .createTime(createTime)
                .admin(isAdmin())
                .build();
    }
}
