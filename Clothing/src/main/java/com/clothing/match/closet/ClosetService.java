package com.clothing.match.closet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClosetService {

    final static public int CLOSET_ITEM_ADD_SUCCESS = 1;
    final static public int CLOSET_ITEM_ALREADY_EXIST = 0;
    final static public int CLOSET_ITEM_ADD_FAIL = -1;

    @Autowired
    ClosetDao closetDao;
    
 // 옷장에 옷을 추가하는 메소드
    public int addClothingConfirm(ClosetVo closetVo, int userId) {
        System.out.println("[ClosetService] addClothingConfirm()");

        // 옷장에 동일한 아이템이 이미 존재하는지 확인
        boolean isExist = closetDao.isClothingExist(closetVo.getItemName(), userId);

        if (isExist) {
            // 아이템이 이미 존재하면
            return CLOSET_ITEM_ALREADY_EXIST;
        } else {
            // 아이템을 추가
            int result = closetDao.insertClothingItem(closetVo, userId);
            if (result > 0) {
                return CLOSET_ITEM_ADD_SUCCESS;
            } else {
                return CLOSET_ITEM_ADD_FAIL;
            }
        }
    }
    
    // 특정 사용자의 옷장 아이템들을 조회
    public List<ClosetVo> getUserClosetItems(int userId) {
        System.out.println("[ClosetService] getUserClosetItems()");

        // 해당 사용자의 옷장 아이템 목록을 조회
        return closetDao.selectUserClosetItems(userId);
    }

    // 옷장 아이템을 삭제하는 메소드
    public int removeClothingItem(int closetId) {
        System.out.println("[ClosetService] removeClothingItem()");

        // 옷장 아이템 삭제
        return closetDao.deleteClothingItem(closetId);
    }

    // 옷장 아이템의 세부 정보를 조회
    public ClosetVo getClothingItemDetails(int closetId) {
        System.out.println("[ClosetService] getClothingItemDetails()");

        // 아이템의 세부 사항 조회
        return closetDao.selectClothingItemDetails(closetId);
    }

    // 옷장 아이템 검색
    public List<ClosetVo> searchClothingItems(int userId, String itemName, String category) {
        System.out.println("[ClosetService] searchClothingItems()");

        // 검색 조건에 맞는 아이템 목록 조회
        return closetDao.searchClothingItems(userId, itemName, category);
    }
}
