package com.finp.moic.giftCard.model.entity;

import com.finp.moic.user.model.entity.User;
import com.finp.moic.util.entity.Base;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name="giftcard")
@Table(indexes = {
        @Index(name="giftcard_shop",columnList = "category, shop_name"),
        @Index(name = "giftcard_delete", columnList = "deleted_at,is_delete"),
})
@Getter
@Builder
@SQLDelete(sql = "UPDATE giftcard SET is_delete = true, deleted_at = CURRENT_TIMESTAMP WHERE giftcard_seq = ?")
@Where(clause = "is_delete = false")
public class Giftcard extends Base {

    @Id
    @Column(name="giftcard_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long giftcardSeq;

    /* 혜지 : FK 확인 필요 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @Column(name="category", length = 20, nullable = false)
    private String category;

    @Column(name="shop_name", length = 20, nullable = false)
    private String shopName;

    @Column(name="image_url", columnDefinition = "TEXT", nullable = false)
    private String imageUrl;

    @Column(name="due_date", nullable = false)
    private LocalDate dueDate;


    public Giftcard() {
    }

    public Giftcard(long giftcardSeq, User user, String category, String shopName, String imageUrl, LocalDate dueDate) {
        this.giftcardSeq = giftcardSeq;
        this.user = user;
        this.category = category;
        this.shopName = shopName;
        this.imageUrl = imageUrl;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Giftcard{" +
                "giftcardSeq=" + giftcardSeq +
                ", user=" + user.getId() +
                ", category='" + category + '\'' +
                ", shopName='" + shopName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
