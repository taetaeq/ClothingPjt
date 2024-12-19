package com.clothing.match.bookmark;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service	
public class BookmarkService {
	@Autowired
    private BookmarkDao bookmarkDao;

    public int saveBookmark(int userId, String closetIds) {
    	int result = bookmarkDao.saveBookmark(userId, closetIds);
        if (result == 0) {
            System.err.println("북마크 저장 실패");
        }
        return result;
    }

    public List<BookmarkVo> getBookmarks(int userId) {
    	 List<BookmarkVo> bookmarks = bookmarkDao.getBookmarksByUserId(userId);
         if (bookmarks.isEmpty()) {
             System.err.println("북마크가 없습니다.");
         }
         return bookmarks;
     }
   

}
