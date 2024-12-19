package com.clothing.match.recommend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendVo {
	private String itemName;  // 옷 이름
    private String category;  // 카테고리 (상의, 하의, 신발)
    private String color;     // 색상
    private String fit;       // 핏 (슬림, 레귤러, 오버 등)
    private String imageUrl;  // 이미지 URL
}
