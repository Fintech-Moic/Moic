package com.finp.moic.card.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TempDTO {
    private String userId;

    public TempDTO() {
    }

    @Builder
    public TempDTO(String userId) {
        this.userId = userId;
    }
}
