package com.finp.moic.giftCard.model.entity;

import com.finp.moic.user.model.entity.User;
import com.finp.moic.util.entity.Base;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name="giftCard")
@Table(indexes = {
        @Index(name="giftCard_shop",columnList = "category, shopName"),
})
@Getter
@Builder
@ToString
public class GiftCard extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long giftCardSeq;

    /* 혜지 : FK 확인 필요 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId", referencedColumnName = "id")
    private User user;

    @Column(length = 20, nullable = false)
    private String category;

    @Column(length = 20, nullable = false)
    private String shopName;

    @Column(length = 20, nullable = false)
    private String productName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String barcodeImage;

    @Column(nullable = false, unique = true)
    private long barcodeNumber;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    public GiftCard() {
    }

    @Builder
    public GiftCard(long giftCardSeq, User user, String category,
                    String shopName, String productName, String barcodeImage,
                    long barcodeNumber, LocalDateTime dueDate) {
        this.giftCardSeq = giftCardSeq;
        this.user = user;
        this.category = category;
        this.shopName = shopName;
        this.productName = productName;
        this.barcodeImage = barcodeImage;
        this.barcodeNumber = barcodeNumber;
        this.dueDate = dueDate;
    }

    @Builder
    public GiftCard(BaseBuilder<?, ?> b, long giftCardSeq, User user,
                    String category, String shopName, String productName,
                    String barcodeImage, long barcodeNumber, LocalDateTime dueDate) {
        super(b);
        this.giftCardSeq = giftCardSeq;
        this.user = user;
        this.category = category;
        this.shopName = shopName;
        this.productName = productName;
        this.barcodeImage = barcodeImage;
        this.barcodeNumber = barcodeNumber;
        this.dueDate = dueDate;
    }

}
