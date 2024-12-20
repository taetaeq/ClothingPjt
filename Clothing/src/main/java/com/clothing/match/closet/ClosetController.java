package com.clothing.match.closet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.clothing.match.user.member.UserMemberVo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Controller
@RequestMapping("/user/closet")
public class ClosetController {

    @Autowired
    ClosetService closetService;

    private static final String BUCKET_NAME = "my-clothing-bucket";

    // 나의 옷장 페이지
    @GetMapping("/myCloset")
    public String myCloset(HttpSession session) {
        String nextPage = "user/closet/my_closet";

        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
        if (loginedUserMemberVo == null) {
            nextPage = "redirect:/user/member/loginForm";
        }

        return nextPage;
    }

    // 옷장에 옷을 등록하는 화면
    @GetMapping("/addClothingForm")
    public String addClothingForm(HttpSession session) {
        String nextPage = "user/closet/add_clothing_form";

        if (session.getAttribute("loginedUserMemberVo") == null) {
            nextPage = "redirect:/user/member/loginForm";
        }

        return nextPage;
    }

 // 옷 등록 확인
    @PostMapping("/addClothingConfirm")
    public String addClothingConfirm(@ModelAttribute ClosetVo closetVo, HttpSession session, Model model) {
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
        String nextPage = "/user/closet/add_clothing_ok";

        if (loginedUserMemberVo == null) {
            return "redirect:/user/member/loginForm";
        }

        try {
            MultipartFile file = closetVo.getImageFile();
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

                // Google Cloud Storage에 업로드
                Storage storage = StorageOptions.newBuilder()
                        .setCredentials(GoogleCredentials.fromStream(new FileInputStream("C:/apiKey/clothing-444605-0391bde9040d.json")))
                        .build()
                        .getService();

                BlobId blobId = BlobId.of(BUCKET_NAME, uniqueFilename);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

                storage.create(blobInfo, file.getBytes());

                String imageUrl = "https://storage.googleapis.com/" + BUCKET_NAME + "/" + uniqueFilename;
                System.out.println("File uploaded to bucket: " + BUCKET_NAME + " as " + uniqueFilename);
                System.out.println("Image URL: " + imageUrl);
                closetVo.setImageUrl(imageUrl);

                // Vision API 분석 호출
                String color = analyzeImage(imageUrl, "IMAGE_PROPERTIES");

                closetVo.setColor(color);
            } else {
                model.addAttribute("message", "이미지 파일이 업로드되지 않았습니다.");
                return "/user/closet/add_clothing_ng";
            }

            int result = closetService.addClothingConfirm(closetVo, loginedUserMemberVo.getUserId());
            if (result == ClosetService.CLOSET_ITEM_ADD_SUCCESS) {
                model.addAttribute("message", "옷이 성공적으로 등록되었습니다.");
            } else if (result == ClosetService.CLOSET_ITEM_ALREADY_EXIST) {
                model.addAttribute("message", "이미 존재하는 아이템입니다.");
                return "/user/closet/add_clothing_ng";
            } else {
                model.addAttribute("message", "옷 등록에 실패했습니다.");
                return "/user/closet/add_clothing_ng";
            }
        } catch (IOException e) {
            model.addAttribute("message", "파일 저장 중 오류가 발생했습니다.");
            e.printStackTrace();
            return "/user/closet/add_clothing_ng";
        } catch (Exception e) {
            model.addAttribute("message", "예기치 못한 오류가 발생했습니다.");
            e.printStackTrace();
            return "/user/closet/add_clothing_ng";
        }

        return nextPage;
    }

    // 옷 삭제
    @GetMapping("/delete/{closetId}")
    public String deleteClothing(@PathVariable("closetId") int closetId, HttpSession session, Model model) {
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");

        if (loginedUserMemberVo == null) {
            return "redirect:/user/member/loginForm";
        }

        try {
            ClosetVo closetVo = closetService.getClothingItemDetails(closetId);
            if (closetVo != null) {
                String imageUrl = closetVo.getImageUrl();
                String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

                Storage storage = StorageOptions.newBuilder()
                        .setCredentials(GoogleCredentials.fromStream(new FileInputStream("C:/apiKey/clothing-444605-0391bde9040d.json")))
                        .build()
                        .getService();

                BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
                boolean deleted = storage.delete(blobId);

                if (deleted) {
                    System.out.println("File deleted from bucket: " + BUCKET_NAME + " as " + fileName);
                } else {
                    System.out.println("File not found in bucket: " + BUCKET_NAME + " as " + fileName);
                }

                int result = closetService.removeClothingItem(closetId);
                if (result > 0) {
                    model.addAttribute("message", "옷이 성공적으로 삭제되었습니다.");
                } else {
                    model.addAttribute("message", "옷 삭제에 실패했습니다.");
                }
            } else {
                model.addAttribute("message", "해당 아이템을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            model.addAttribute("message", "삭제 중 오류가 발생했습니다.");
            e.printStackTrace();
        }

        return "/user/closet/delete_clothing_ok";
    }

 // 임시 이미지 업로드
    @PostMapping("/uploadTempImage")
    public ResponseEntity<Map<String, String>> uploadTempImage(@RequestParam("imageFile") MultipartFile file) {
        try {
        	// Google Cloud Storage 연결
        	Storage storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream("C:/apiKey/clothing-444605-0391bde9040d.json")))
                    .build()
                    .getService();

        	
        	// 고유한 파일 이름 생성
            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
            
            // Cloud Storage에 파일 업로드
            BlobId blobId = BlobId.of(BUCKET_NAME, uniqueFilename);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, file.getBytes());

            // 업로드된 이미지의 URL 반환
            String imageUrl = "https://storage.googleapis.com/" + BUCKET_NAME + "/" + uniqueFilename;
            System.out.println("File uploaded to bucket: " + BUCKET_NAME + " as " + uniqueFilename);
            System.out.println("Image URL: " + imageUrl);
            return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "이미지 업로드 실패"));
        }
    }
    
 // Vision API 분석
    @PostMapping("/analyzeImage")
    public ResponseEntity<Map<String, String>> analyzeImage(@RequestBody Map<String, String> request) {
        String imageUrl = request.get("imageUrl");
        if (imageUrl == null || imageUrl.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "이미지 URL이 비어 있습니다."));
        }

        try {
            // Vision API 호출
            String color = analyzeImage(imageUrl, "IMAGE_PROPERTIES");
            return ResponseEntity.ok(Map.of("color", color));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "이미지 분석 중 오류가 발생했습니다."));
        }
    }
    
 // Vision API 호출 (이미지 분석)
    private String analyzeImage(String imageUrl, String featureType) {
        try {
        	 // Vision API 호출 URL
        	String apiKey = System.getenv("GOOGLE_API_KEY");
        	if (apiKey == null || apiKey.isEmpty()) {
        	    throw new IllegalStateException("API 키가 설정되지 않았습니다.");
        	}
        	String visionApiUrl = "https://vision.googleapis.com/v1/images:annotate?key=" + apiKey;
            
            // 요청 JSON 작성	
            String requestJson = "{"
            	    + "\"requests\": [{"
            	    + "\"image\": {\"source\": {\"imageUri\": \"" + imageUrl + "\"}},"
            	    + "\"features\": [{\"type\": \"" + featureType + "\", \"maxResults\": 5}]"
            	    + "}]"
            	    + "}";

            // REST API 요청 설정
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);
            // Vision API 호출
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(visionApiUrl, requestEntity, String.class);
            // 응답 JSON 처리
            String jsonResponse = responseEntity.getBody();
            System.out.println("Vision API Response: " + jsonResponse);

            if ("IMAGE_PROPERTIES".equals(featureType)) {
            	System.out.println(jsonResponse);
                return parseColor(jsonResponse);   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "분석 실패";
    }
    private static final Map<String, int[]> EXTENDED_COLOR_MAP = new HashMap<>();
		 // 확장된 색상 데이터베이스 초기화
		 static {
			 	EXTENDED_COLOR_MAP.put("화이트", new int[]{255, 255, 255});
			    EXTENDED_COLOR_MAP.put("실버", new int[]{192, 192, 192});
			    EXTENDED_COLOR_MAP.put("라이트 그레이", new int[]{211, 211, 211});
			    EXTENDED_COLOR_MAP.put("그레이", new int[]{128, 128, 128});
			    EXTENDED_COLOR_MAP.put("다크 그레이", new int[]{64, 64, 64});
			    EXTENDED_COLOR_MAP.put("블랙", new int[]{0, 0, 0});
			    EXTENDED_COLOR_MAP.put("레드", new int[]{255, 0, 0});
			    EXTENDED_COLOR_MAP.put("딥레드", new int[]{139, 0, 0});
			    EXTENDED_COLOR_MAP.put("버건디", new int[]{128, 0, 32});
			    EXTENDED_COLOR_MAP.put("브릭", new int[]{156, 102, 102});
			    EXTENDED_COLOR_MAP.put("페일 핑크", new int[]{250, 218, 221});
			    EXTENDED_COLOR_MAP.put("핑크", new int[]{255, 192, 203});
			    EXTENDED_COLOR_MAP.put("라이트 핑크", new int[]{255, 182, 193});
			    EXTENDED_COLOR_MAP.put("로즈골드", new int[]{183, 110, 121});
			    EXTENDED_COLOR_MAP.put("오렌지", new int[]{255, 165, 0});
			    EXTENDED_COLOR_MAP.put("아이보리", new int[]{255, 255, 240});
			    EXTENDED_COLOR_MAP.put("오트밀", new int[]{205, 186, 136});
			    EXTENDED_COLOR_MAP.put("라이트 옐로우", new int[]{255, 255, 224});
			    EXTENDED_COLOR_MAP.put("옐로우", new int[]{255, 255, 0});
			    EXTENDED_COLOR_MAP.put("머스타드", new int[]{255, 219, 88});
			    EXTENDED_COLOR_MAP.put("골드", new int[]{255, 215, 0});
			    EXTENDED_COLOR_MAP.put("라이트 그린", new int[]{144, 238, 144});
			    EXTENDED_COLOR_MAP.put("그린", new int[]{0, 128, 0});
			    EXTENDED_COLOR_MAP.put("올리브 그린", new int[]{107, 142, 35});
			    EXTENDED_COLOR_MAP.put("카키", new int[]{195, 176, 145});
			    EXTENDED_COLOR_MAP.put("다크 그린", new int[]{0, 100, 0});
			    EXTENDED_COLOR_MAP.put("민트", new int[]{152, 255, 152});
			    EXTENDED_COLOR_MAP.put("스카이 블루", new int[]{135, 206, 235});
			    EXTENDED_COLOR_MAP.put("블루", new int[]{0, 0, 255});
			    EXTENDED_COLOR_MAP.put("다크 블루", new int[]{0, 0, 139});
			    EXTENDED_COLOR_MAP.put("네이비", new int[]{0, 0, 128});
			    EXTENDED_COLOR_MAP.put("다크 네이비", new int[]{0, 0, 102});
			    EXTENDED_COLOR_MAP.put("라벤더", new int[]{230, 230, 250});
			    EXTENDED_COLOR_MAP.put("퍼플", new int[]{128, 0, 128});
			    EXTENDED_COLOR_MAP.put("브라운", new int[]{165, 42, 42});
			    EXTENDED_COLOR_MAP.put("다크 브라운", new int[]{92, 64, 51});
			    EXTENDED_COLOR_MAP.put("카멜", new int[]{193, 154, 107});
			    EXTENDED_COLOR_MAP.put("베이지", new int[]{245, 245, 220});
			    EXTENDED_COLOR_MAP.put("카키 베이지", new int[]{195, 176, 145});
			    EXTENDED_COLOR_MAP.put("연청", new int[]{173, 216, 230});
			    EXTENDED_COLOR_MAP.put("중청", new int[]{70, 130, 180});
			    EXTENDED_COLOR_MAP.put("진청", new int[]{0, 0, 128});
			    EXTENDED_COLOR_MAP.put("흑청", new int[]{47, 79, 79});
		 }

 
    private String getClosestColorName(int red, int green, int blue) {
        String closestColor = "알 수 없음";
        double minDistance = Double.MAX_VALUE;

        for (Map.Entry<String, int[]> entry : EXTENDED_COLOR_MAP.entrySet()) {
            int[] color = entry.getValue();
            double distance = Math.sqrt(
                Math.pow(red - color[0], 2) +
                Math.pow(green - color[1], 2) +
                Math.pow(blue - color[2], 2)
            );

            if (distance < minDistance) {
                minDistance = distance;
                closestColor = entry.getKey();
            }
        }

        return closestColor;
    }

    
    private String parseColor(String jsonResponse) {
    	try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode colorNode = rootNode.path("responses").get(0)
                    .path("imagePropertiesAnnotation")
                    .path("dominantColors")
                    .path("colors").get(0)
                    .path("color");

            if (!colorNode.isMissingNode()) {
                int red = colorNode.get("red").asInt();
                int green = colorNode.get("green").asInt();
                int blue = colorNode.get("blue").asInt();
                return getClosestColorName(red, green, blue); // 가장 가까운 색상 이름 반환
            }else {
                System.out.println("색상 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "색상 없음";
    }

    // 사용자가 등록한 옷장 아이템 조회
    @GetMapping("/viewClothing")
    public String viewClothing(HttpSession session, Model model) {
        System.out.println("[ClosetController] viewClothing()");

        String nextPage = "/user/closet/view_clothing";
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");

        if (loginedUserMemberVo == null) {
            nextPage = "redirect:/user/member/loginForm";
        } else {
            // 옷장 아이템을 조회하여 모델에 추가
            List<ClosetVo> closetItems = closetService.getUserClosetItems(loginedUserMemberVo.getUserId());
            model.addAttribute("closetItems", closetItems);
        }
        // 카테고리 리스트 추가
        List<String> categories = Arrays.asList("아우터", "상의", "드레스", "하의", "신발");
        model.addAttribute("categories", categories);

        return nextPage;
    }

    // 특정 옷 아이템의 세부 사항 조회
    @GetMapping("/details/{closetId}")
    public String detailClothing(@PathVariable("closetId") int closetId, HttpSession session, Model model) {
        String nextPage = "/user/closet/detail_clothing";
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");

        if (loginedUserMemberVo == null) {
            nextPage = "redirect:/user/member/loginForm";
        } else {
            ClosetVo closetVo = closetService.getClothingItemDetails(closetId);
            model.addAttribute("clothing", closetVo);
        }

        return nextPage;
    }

    // 옷 검색 화면
    @GetMapping("/searchClothingForm")
    public String searchClothingForm(@RequestParam(value = "itemName", required = false) String itemName,
                                      @RequestParam(value = "category", required = false) String category,
                                      HttpSession session, Model model) {
        String nextPage = "/user/closet/search_clothing_result";
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");

        if (loginedUserMemberVo == null) {
            nextPage = "redirect:/user/member/loginForm";
        } else {
            if (itemName == null) {
                itemName = "";
            }
            int userId = loginedUserMemberVo.getUserId();
            List<ClosetVo> searchResults = closetService.searchClothingItems(userId, itemName, category);
            model.addAttribute("searchResults", searchResults);
        }

        return nextPage;
    }

}
