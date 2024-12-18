package com.clothing.match.bookmark;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.clothing.match.closet.ClosetVo;

@Repository
public class BookmarkDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;

    // 북마크 저장
    public int saveBookmark(int userId, String closetIds) {
        String sql = "INSERT INTO bookmark (user_id, closet_ids) VALUES (?, ?)";
        return jdbcTemplate.update(sql, userId, closetIds);
    }

    // 북마크 조회
    public List<BookmarkVo> getBookmarksByUserId(int userId) {
        String sql = "SELECT * FROM bookmark WHERE user_id = ?";
        try {
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            BookmarkVo vo = new BookmarkVo();
            vo.setBookmarkId(rs.getInt("bookmark_id"));
            vo.setUserId(rs.getInt("user_id"));
            vo.setClosetIds(rs.getString("closet_ids"));
            vo.setCreatedAt(rs.getString("created_at"));
            return vo;
        });
    } catch (DataAccessException e) {
        System.err.println("북마크 조회 실패: " + e.getMessage());
        return List.of();
    }
    }
    

}
