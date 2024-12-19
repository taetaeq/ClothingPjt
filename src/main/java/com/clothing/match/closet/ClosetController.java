package com.clothing.match.closet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.clothing.match.user.member.UserMemberVo;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.ColorInfo;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

@Controller
@RequestMapping("/user/closet")
public class ClosetController {

    @Autowired
    ClosetService closetService;

    // 나의 옷장 페이지
    @GetMapping("/myCloset")
    public String myCloset(HttpSession session) {
        System.out.println("[ClosetController] myCloset()");

        String nextPage = "user/closet/my_closet";  // my_closet.jsp로 이동

        // 로그인 확인
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
        if (loginedUserMemberVo == null) {
            System.out.println("[ClosetController] 로그인 정보 없음, 로그인 페이지로 리다이렉트");
            nextPage = "redirect:/user/member/loginForm";  // 로그인 안 된 경우 로그인 페이지로 리다이렉트
        }

        return nextPage;
    }
    
    @PostMapping("/analyzeImage")
    @ResponseBody // 이 어노테이션을 추가하여 JSON 응답으로 반환
    public Map<String, String> analyzeImage(@RequestParam("imageFile") MultipartFile file) {
    	System.out.println("=== analyzeImage 메소드 호출됨 ===");
    	Map<String, String> result = new HashMap<>();
    	try {
            if (file != null && !file.isEmpty()) {
                System.out.println("Uploaded File Name: " + file.getOriginalFilename());
                System.out.println("File Size: " + file.getSize());

                // 업로드된 파일을 임시 저장 (테스트용)
                File tempFile = new File("C:/clothing/uploads/temp_" + file.getOriginalFilename());
                file.transferTo(tempFile);

                result.put("status", "파일 업로드 성공");
                result.put("fileName", file.getOriginalFilename());
            } else {
                result.put("status", "파일이 비어 있습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "서버에서 파일 처리 중 오류가 발생했습니다.");
        }
        return result; // JSON 형식으로 반환
    }
        /*
        try (InputStream inputStream = file.getInputStream()) {
            ByteString imgBytes = ByteString.readFrom(inputStream);

            try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
                Image img = Image.newBuilder().setContent(imgBytes).build();

                AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .setImage(img)
                    .addFeatures(Feature.newBuilder().setType(Feature.Type.IMAGE_PROPERTIES).build())
                    .addFeatures(Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build())
                    .build();

                AnnotateImageResponse response = vision.batchAnnotateImages(Arrays.asList(request)).getResponsesList().get(0);

                // 색상 추출
                String dominantColor = "기타";
                if (response.hasImagePropertiesAnnotation()) {
                    List<ColorInfo> colors = response.getImagePropertiesAnnotation().getDominantColors().getColorsList();
                    if (!colors.isEmpty()) {
                        ColorInfo color = colors.get(0);
                        dominantColor = String.format("RGB(%.0f, %.0f, %.0f)",
                            color.getColor().getRed(),
                            color.getColor().getGreen(),
                            color.getColor().getBlue());
                    }
                }
                result.put("color", dominantColor);

                // 패턴 추출
                String detectedPattern = "무지";
                for (EntityAnnotation annotation : response.getLabelAnnotationsList()) {
                    String label = annotation.getDescription().toLowerCase();
                    if (label.contains("stripe")) {
                        detectedPattern = "스트라이프";
                        break;
                    } else if (label.contains("check")) {
                        detectedPattern = "체크";
                        break;
                    }
                }
                result.put("pattern", detectedPattern);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "이미지 분석 중 오류가 발생했습니다.");
        }

        return result; // JSON 형식으로 반환
    }*/


    
    // 옷장에 옷을 등록하는 화면
    @GetMapping("/addClothingForm")
    public String addClothingForm(HttpSession session) {
        System.out.println("[ClosetController] addClothingForm()");

        String nextPage = "user/closet/add_clothing_form"; // add_clothing_form.jsp 뷰로 변경

        // 로그인 확인
        if (session.getAttribute("loginedUserMemberVo") == null) {
            nextPage = "redirect:/user/member/loginForm";
        }

        return nextPage;
    }
    // 옷 등록 확인
    @PostMapping("/addClothingConfirm")
    public String addClothingConfirm(
            @ModelAttribute ClosetVo closetVo,
            HttpSession session,
            Model model) {

        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
        String nextPage = "/user/closet/add_clothing_ok";

        if (loginedUserMemberVo == null) {
            return "redirect:/user/member/loginForm";
            
        }

        try {
            // MultipartFile 처리
            MultipartFile file = closetVo.getImageFile();
            System.out.println("Original file: " + file.getOriginalFilename());
            
            if (!file.isEmpty()) {
                // 저장 경로 처리
                String uploadDirectory = "C:/clothing/uploads/";
                System.out.println("Real path for upload directory: " + uploadDirectory);
                String imageUrlDirectory = "/uploads/";

                // 디렉터리가 없으면 생성
                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                    System.out.println("Directory created: " + uploadDirectory);
                }

                // 고유한 파일 이름 생성
                String originalFilename = file.getOriginalFilename();
                String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
                
                // 서버에 파일 저장
                File dest = new File(uploadDirectory, uniqueFilename);
                file.transferTo(dest);
                System.out.println("File saved: " + dest.getAbsolutePath());

                // URL 경로 설정
                closetVo.setImageUrl(imageUrlDirectory + uniqueFilename);
            } else {
                model.addAttribute("message", "이미지 파일이 업로드되지 않았습니다.");
                return "/user/closet/add_clothing_ng";
            }
            
            // 서비스 호출
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

    // 옷 삭제
    @GetMapping("/delete/{closetId}")
    public String deleteClothing(@PathVariable("closetId") int closetId, HttpSession session, Model model) {
        System.out.println("[ClosetController] deleteClothing()");

        String nextPage = "/user/closet/delete_clothing_form";  // 기본 삭제 폼 페이지 설정
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");

        if (loginedUserMemberVo == null) {
            nextPage = "redirect:/user/member/loginForm";  // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        } else {
            // 옷의 세부 정보 조회
            ClosetVo closetVo = closetService.getClothingItemDetails(closetId);

            System.out.println("ClosetVo 조회 결과: " + closetVo);

            if (closetVo != null) {
                // 이미지 파일 경로
                String imagePath = "C:/clothing/uploads/" + closetVo.getImageUrl().substring(9);  // /uploads/ 이후 경로

                System.out.println("이미지 파일 경로: " + imagePath);

                // 이미지 파일 삭제
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    imageFile.delete();  // 이미지 삭제
                    System.out.println("이미지 삭제 성공: " + imagePath);
                } else {
                    System.out.println("이미지 파일이 존재하지 않습니다: " + imagePath);
                }

                // 아이템 삭제
                int result = closetService.removeClothingItem(closetId);

                if (result > 0) {
                    // 삭제 성공 시
                    model.addAttribute("message", "옷이 성공적으로 삭제되었습니다.");
                    nextPage = "/user/closet/delete_clothing_ok";  // 삭제 성공 페이지로 이동
                } else {
                    // 삭제 실패 시
                    model.addAttribute("message", "옷 삭제에 실패했습니다.");
                    nextPage = "/user/closet/delete_clothing_ng";  // 삭제 실패 페이지로 이동
                }
            } else {
                model.addAttribute("message", "해당 아이템을 찾을 수 없습니다.");
                nextPage = "/user/closet/delete_clothing_ng";  // 아이템이 없을 경우
            }
        }

        return nextPage;  // 결과에 맞는 페이지로 이동
    }



    // 특정 옷 아이템의 세부 사항 조회
    @GetMapping("/details/{closetId}")
    public String detailClothing(@PathVariable("closetId") int closetId, HttpSession session, Model model) {
        System.out.println("[ClosetController] detailClothing()");

        String nextPage = "/user/closet/detail_clothing";
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");

        if (loginedUserMemberVo == null) {
            nextPage = "redirect:/user/member/loginForm";
        } else {
            ClosetVo closetVo = closetService.getClothingItemDetails(closetId);  // closetId로 수정
            model.addAttribute("clothing", closetVo);  // "clothing"으로 변경
        }

        return nextPage;
    }

    // 옷 검색 화면
    @GetMapping("/searchClothingForm")
    public String searchClothingForm(@RequestParam(value = "itemName", required = false) String itemName,
                                      @RequestParam(value = "category", required = false) String category,
                                      HttpSession session, Model model) {
        System.out.println("[ClosetController] searchClothingForm()");

        String nextPage = "/user/closet/search_clothing_result";
        
        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");

        if (loginedUserMemberVo == null) {
            nextPage = "redirect:/user/member/loginForm";
        } else {
            if (itemName == null) {
            	itemName = itemName != null ? itemName : "";  // 검색 조건이 없으면 빈 문자열로 처리
            }
         // userId를 가져와 검색에 사용
            int userId = loginedUserMemberVo.getUserId(); // userId가 loginedUserMemberVo에 있다고 가정
            
            List<ClosetVo> searchResults = closetService.searchClothingItems(userId, itemName, category);
            model.addAttribute("searchResults", searchResults);
        }

        return nextPage;
    }
}
