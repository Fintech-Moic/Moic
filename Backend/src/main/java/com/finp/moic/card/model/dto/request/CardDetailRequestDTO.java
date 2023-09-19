package com.finp.moic.card.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CardDetailRequestDTO {

    @NotNull
    @NotBlank
    private String cardName;

    public CardDetailRequestDTO() {
    }

    @Builder
    public CardDetailRequestDTO(@NotNull String cardName) {
        this.cardName = cardName;
    }

}
