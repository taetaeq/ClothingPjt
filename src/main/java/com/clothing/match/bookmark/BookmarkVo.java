package com.clothing.match.bookmark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkVo {
    private int bookmarkId;
    private int userId;
    private String closetIds; // 추천받은 옷의 ID 리스트 (예: "1,3,5")
    private String createdAt;
}
