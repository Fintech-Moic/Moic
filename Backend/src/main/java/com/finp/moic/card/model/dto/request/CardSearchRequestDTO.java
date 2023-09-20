package com.finp.moic.card.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CardSearchRequestDTO {

    @NotNull
    private String company;

    @NotNull
    private String type;

    @NotNull
    private String cardName;

    private String userId;

    public CardSearchRequestDTO() {
    }

    @Builder
    public CardSearchRequestDTO(@NotNull String company, @NotNull String type, @NotNull String cardName,
                                String userId) {
        this.company = company;
        this.type = type;
        this.cardName = cardName;
        this.userId = userId;
    }
}
