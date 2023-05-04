package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Collection;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {

    private CollectionRepository collectionRepository;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public List<Collection> getCollectionList(User user) {
        // 유저의 획득한 꽃 목록 조회
        return user.getCollectionList();
    }

    public void addCollection(Collection newCollection) {
        // 획득한 꽃 collection 등록
        collectionRepository.save(newCollection);
    }
}
