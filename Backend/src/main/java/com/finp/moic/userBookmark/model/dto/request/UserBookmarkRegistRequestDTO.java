package com.finp.moic.userBookmark.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserBookmarkRegistRequestDTO {

    @NotBlank
    private String shopName;

    @NotNull
    private String shopLocation;

    private String userId;

    public UserBookmarkRegistRequestDTO() {
    }

    @Builder
    public UserBookmarkRegistRequestDTO(String shopName, String shopLocation, String userId) {
        this.shopName = shopName;
        this.shopLocation = shopLocation;
        this.userId = userId;
    }
}
