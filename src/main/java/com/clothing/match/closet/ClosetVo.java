package com.clothing.match.closet;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClosetVo {
		private int closetId;
	 	private String itemName;   // 옷 이름
	    private String category;   // 옷 카테고리
	    private String detail;		// 옷 세부 항목
	    private String color;       // 색상
	    private String fit;        // 핏
	    private String pattern;    // 패턴/무늬
	    
	    private MultipartFile imageFile; // 업로드된 파일
	    private String imageUrl;         // DB에 저장할 이미지 URL
	    
}
