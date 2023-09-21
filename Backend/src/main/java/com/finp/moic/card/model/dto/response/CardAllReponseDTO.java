package com.finp.moic.card.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@ToString
public class CardAllReponseDTO implements Serializable {

    private List<String> companyList;
    private List<String> typeList;
    private List<CardResponseDTO> cardList;

    public CardAllReponseDTO() {
    }

    @Builder
    public CardAllReponseDTO(List<String> companyList, List<String> typeList, List<CardResponseDTO> cardList) {
        this.companyList = companyList;
        this.typeList = typeList;
        this.cardList = cardList;
    }
}
