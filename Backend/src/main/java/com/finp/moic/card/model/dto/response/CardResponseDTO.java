package com.finp.moic.card.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class CardResponseDTO implements Serializable {

    private String id;
    private String company;
    private String type;
    private String name;
    private String cardImage;
    private boolean mine;

    public CardResponseDTO() {
    }

    @QueryProjection
    @Builder
    public CardResponseDTO(String id, String company, String type,
                           String name, String cardImage, boolean mine) {
        this.id = id;
        this.company = company;
        this.type = type;
        this.name = name;
        this.cardImage = cardImage;
        this.mine = mine;
    }
}
