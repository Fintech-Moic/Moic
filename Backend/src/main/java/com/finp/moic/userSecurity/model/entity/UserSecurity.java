package com.finp.moic.userSecurity.model.entity;

import com.finp.moic.util.entity.Base;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="userSecurity")
@Table(indexes = {
        @Index(name = "userSecurity_userId",columnList = "userId"),
})
@Getter
@Builder
@ToString
public class UserSecurity extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userSecuritySeq;

    @Column(length = 20, nullable = false, unique = true)
    private String userId;

    @Column(length = 500, nullable = false)
    private String salt;

    public UserSecurity() {
    }

    @Builder
    public UserSecurity(long userSecuritySeq, String userId, String salt) {
        this.userSecuritySeq = userSecuritySeq;
        this.userId = userId;
        this.salt = salt;
    }

    @Builder
    public UserSecurity(BaseBuilder<?, ?> b, long userSecuritySeq, String userId,
                        String salt) {
        super(b);
        this.userSecuritySeq = userSecuritySeq;
        this.userId = userId;
        this.salt = salt;
    }

}
