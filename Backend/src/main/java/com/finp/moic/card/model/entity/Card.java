package com.finp.moic.card.model.entity;

import com.finp.moic.cardBenefit.model.entity.CardBenefit;
import com.finp.moic.util.entity.Base;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;

import java.util.List;

@Entity(name="card")
@Table(indexes = {
    @Index(name = "card_name", columnList = "name"),
    @Index(name = "card_delete", columnList = "deleted_at,is_delete"),
})
@Getter
@Builder
@SQLDelete(sql = "UPDATE card SET is_delete = true, deleted_at = CURRENT_TIMESTAMP WHERE card_seq = ?")
public class Card extends Base {

    @Id
    @Column(name="card_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cardSeq;

    @Column(name="company", length = 20, nullable = false)
    private String company;

    @Column(name="type", length = 10, nullable = false)
    private String type;

    @Column(name="name", length = 20, nullable = false)
    private String name;

    @Column(name="card_image", columnDefinition = "TEXT", nullable = false)
    private String cardImage;

    @OneToMany(mappedBy = "card")
    private List<CardBenefit> cardBenefits;

    public Card() {
    }

    public Card(long cardSeq, String company, String type, String name, String cardImage, List<CardBenefit> cardBenefits) {
        this.cardSeq = cardSeq;
        this.company = company;
        this.type = type;
        this.name = name;
        this.cardImage = cardImage;
        this.cardBenefits = cardBenefits;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardSeq=" + cardSeq +
                ", company='" + company + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", cardImage='" + cardImage + '\'' +
                '}';
    }
}
