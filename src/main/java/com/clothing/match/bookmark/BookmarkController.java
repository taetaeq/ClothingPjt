package com.clothing.match.bookmark;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/bookmark")
public class BookmarkController {
	
	@Autowired
    private BookmarkService bookmarkService;

    // 북마크 저장
    @PostMapping("/save")
    @ResponseBody
    public String saveBookmark(@RequestParam("userId") int userId,
                               @RequestParam("closetIds") String closetIds) {
        bookmarkService.saveBookmark(userId, closetIds);
        return "북마크가 저장되었습니다.";
    }

    // 북마크 조회
    @GetMapping("/list")
    public String getBookmarks(@RequestParam("userId") int userId, Model model) {
        List<BookmarkVo> bookmarks = bookmarkService.getBookmarks(userId);
        model.addAttribute("bookmarks", bookmarks);
        return "bookmark/list";
    }
    
}
