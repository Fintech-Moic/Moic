package com.finp.moic.card.model.dto.response;

import lombok.*;

@Getter
@ToString
public class CardRegistResponseDTO {

    private Long cardSeq;

    private String company;

    private String type;

    private String name;

    private String cardImage;

    @Builder
    public CardRegistResponseDTO(Long cardSeq, String company, String type,
                                 String name, String cardImage) {
        this.cardSeq = cardSeq;
        this.company = company;
        this.type = type;
        this.name = name;
        this.cardImage = cardImage;
    }

}
