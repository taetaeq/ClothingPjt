package com.clothing.match.closet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClosetDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // 옷장에 아이템이 존재하는지 확인
    public boolean isClothingExist(String itemName, int userId) {
        String sql = "SELECT COUNT(*) FROM closet WHERE user_id = ? AND item_name = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, userId, itemName);
        return count > 0;
    }

 // 옷 등록
    public int insertClothingItem(ClosetVo closetVo, int userId) {
        System.out.println("[ClosetDao] insertClothingItem()");
        System.out.println("[insertClothingItem] imageUrl: " + closetVo.getImageUrl());
        // SQL 쿼리문
        String sql = "INSERT INTO closet(item_name, category, detail, color, image_url, fit, pattern, user_id) "
                   + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        int result = -1;  // 결과값을 -1로 초기화

        try {
        	System.out.println("[ClosetDao] Executing SQL query...");
            System.out.println("SQL Query: " + sql);
            
            // 옷장에 옷 추가하는 쿼리 실행
            result = jdbcTemplate.update(sql,
                    closetVo.getItemName(),  // 옷 이름
                    closetVo.getCategory(),   // 카테고리
                    closetVo.getDetail()!= null ? closetVo.getDetail() : "",
                    closetVo.getColor(),      // 색상
                    closetVo.getImageUrl(),   // 이미지 URL
                    closetVo.getFit(),        // 핏
                    closetVo.getPattern(),    // 패턴/무늬
                    userId);                  // 사용자의 user_id
            
            System.out.println("Result: " + result);
            
        } catch (Exception e) {
            e.printStackTrace();  // 예외 발생 시 콘솔에 출력
        }

        return result;  // 성공적으로 실행되었으면 1, 아니면 -1을 반환
    }

    // 옷장에 등록된 옷 조회
    public List<ClosetVo> selectUserClosetItems(int userId) {
        System.out.println("[ClosetDao] selectUserClosetItems()");

        String sql = "SELECT * FROM closet WHERE user_id = ?";

        List<ClosetVo> closetItems = new ArrayList<>();

        try {
            // ClosetVo 객체를 바탕으로 결과 매핑
            RowMapper<ClosetVo> rowMapper = BeanPropertyRowMapper.newInstance(ClosetVo.class);
            closetItems = jdbcTemplate.query(sql, rowMapper, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return closetItems;  // 결과 리턴
    }

 // 옷장에 등록된 옷 삭제
    public int deleteClothingItem(int closetId) {
        System.out.println("[ClosetDao] deleteClothingItem()");

        String sql = "DELETE FROM closet WHERE closet_id = ?";
        int result = -1;

        try {
            // 옷을 삭제하는 쿼리 실행
            result = jdbcTemplate.update(sql, closetId);

            // 이미지 파일 삭제 추가
            ClosetVo closetVo = selectClothingItemDetails(closetId); // 기존 코드를 통해 상세 조회
            if (closetVo != null && closetVo.getImageUrl() != null) {
                File file = new File("C:\\clothing\\uploads\\" + closetVo.getImageUrl());
                if (file.exists() && file.delete()) {
                    System.out.println("이미지 삭제 완료: " + closetVo.getImageUrl());
                } else {
                    System.out.println("이미지 삭제 실패: " + closetVo.getImageUrl());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;  // 성공적으로 실행되었으면 1, 아니면 -1을 반환
    }


    // 특정 아이템의 세부 사항을 조회 (아이템 이름, 카테고리 등)
    public ClosetVo selectClothingItemDetails(int closetId) {
        System.out.println("[ClosetDao] selectClothingItemDetails()");

        String sql = "SELECT * FROM closet WHERE closet_id = ?";
        ClosetVo closetVo = null;

        try {
            // 결과 매핑 후 단일 결과 리턴
            RowMapper<ClosetVo> rowMapper = BeanPropertyRowMapper.newInstance(ClosetVo.class);
            closetVo = jdbcTemplate.queryForObject(sql, rowMapper, closetId);
        } catch (EmptyResultDataAccessException e) {
            // 아이템이 존재하지 않는 경우 예외 처리
            System.out.println("No clothing item found with closet_id: " + closetId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return closetVo;  // 해당 아이템의 세부 사항을 반환, 없으면 null
    }


 // 옷장 아이템 검색
    public List<ClosetVo> searchClothingItems(int userId, String itemName, String category) {
        // StringBuilder를 사용하여 쿼리 생성
        StringBuilder sql = new StringBuilder("SELECT * FROM closet WHERE user_id = ?");

        // 파라미터를 저장할 리스트
        List<Object> params = new ArrayList<>();
        params.add(userId); // userId 조건 추가
        
        // 조건에 맞는 SQL 쿼리 추가
        if (itemName != null && !itemName.isEmpty()) {
            sql.append(" AND item_name LIKE ?");
            params.add("%" + itemName + "%");
        }
        if (category != null && !category.isEmpty()) {
            sql.append(" AND category = ?");
            params.add(category);
        }

        // 파라미터들을 쿼리에 적용하여 결과 반환
        return jdbcTemplate.query(sql.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(ClosetVo.class));
    }
}
