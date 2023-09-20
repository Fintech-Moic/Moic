package com.finp.moic.card.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class CardInitFilterResponseDTO {

    private List<String> companyList;
    private List<String> typeList;

    public CardInitFilterResponseDTO() {
    }

    @Builder
    public CardInitFilterResponseDTO(List<String> companyList, List<String> typeList) {
        this.companyList = companyList;
        this.typeList = typeList;
    }
}
