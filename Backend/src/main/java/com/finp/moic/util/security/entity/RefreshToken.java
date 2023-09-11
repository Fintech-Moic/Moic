package com.finp.moic.util.security.entity;

import com.finp.moic.util.entity.Base;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="refreshToken")
@Table(indexes = {
        @Index(name = "refreshToken_userId",columnList = "userId"),
})
@Getter
@Builder
@ToString
public class RefreshToken extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userSecuritySeq;

    @Column(length = 20, nullable = false, unique = true)
    private String userId;

    @Column(length = 500, nullable = false)
    private String token;

    public RefreshToken() {
    }

    @Builder
    public RefreshToken(long userSecuritySeq, String userId, String token) {
        this.userSecuritySeq = userSecuritySeq;
        this.userId = userId;
        this.token = token;
    }


}
