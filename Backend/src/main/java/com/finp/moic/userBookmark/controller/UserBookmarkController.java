package com.finp.moic.userBookmark.controller;

import com.finp.moic.userBookmark.model.dto.request.UserBookmarkDeleteRequestDTO;
import com.finp.moic.userBookmark.model.dto.request.UserBookmarkRegistRequestDTO;
import com.finp.moic.userBookmark.model.dto.response.UserBookmarkLookupResponseDTO;
import com.finp.moic.userBookmark.model.service.UserBookmarkService;
import com.finp.moic.util.dto.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/bkm")
public class UserBookmarkController {

    private final UserBookmarkService userBookmarkService;

    @Autowired
    public UserBookmarkController(UserBookmarkService userBookmarkService) {
        this.userBookmarkService = userBookmarkService;
    }

    /**
     * TO DO :: userId 삭제 및 주석 해제
     * **/
    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO> registBookmark(@RequestBody @Valid UserBookmarkRegistRequestDTO userBookmarkRegistRequestDTO/*,
                                                  @AuthenticationPrincipal UserAuthentication userAuthentication*/){

        userBookmarkService.registBookmark(userBookmarkRegistRequestDTO, /*userAuthentication.getId()*/userBookmarkRegistRequestDTO.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("북마크 등록이 완료되었습니다.")
                .build());
    }

    /**
     * TO DO :: userId 삭제 및 주석 해제
     * **/
    @PostMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteBookmarkList(@RequestBody @Valid UserBookmarkDeleteRequestDTO userBookmarkDeleteRequestDTO/*,
                                                  @AuthenticationPrincipal UserAuthentication userAuthentication*/){

        userBookmarkService.deleteBookmarkList(userBookmarkDeleteRequestDTO, /*userAuthentication.getId()*/userBookmarkDeleteRequestDTO.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("북마크 삭제가 완료되었습니다.")
                .build());
    }

    /**
     * TO DO :: userId 삭제 및 주석 해제
     * **/
    @GetMapping
    public ResponseEntity<ResponseDTO> getBookmarkList(@RequestParam("userId") String userId){

        List<UserBookmarkLookupResponseDTO> dto=userBookmarkService.getBookmarkList(/*userAuthentication.getId()*/userId);
        HashMap<String,Object> response=new HashMap<>();
        response.put("shopList",dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("내 북마크 조회")
                .data(response)
                .build());
    }

}
