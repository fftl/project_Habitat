package com.ssafy.habitat.service;

import com.ssafy.habitat.entity.Collection;
import com.ssafy.habitat.entity.User;
import com.ssafy.habitat.repository.CollectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {
    private final Logger LOGGER = LoggerFactory.getLogger(CollectionService.class);


    private CollectionRepository collectionRepository;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    //@Cacheable(value="CollectionList", key="#user.userKey")
    public List<Collection> getCollectionList(User user) {
        // 유저의 획득한 꽃 목록 조회
        LOGGER.info("getCollectionList() : 유저의 획득한 꽃 목록 조회");

        List<Collection> collectionList = collectionRepository.findByUser(user);
        return collectionList;
    }

    @Caching( evict = {
            //@CacheEvict(value="CollectionCnt", key="#newCollection.user.userKey"),
            //@CacheEvict(value="CollectionList", key="#newCollection.user.userKey"),
            //@CacheEvict(value="UserFlowerStateList", key="#newCollection.user.userKey")
    })
    public void addCollection(Collection newCollection) {
        // 획득한 꽃 collection 등록
        LOGGER.info("addCollection() : 수한 꽃 collection 등록");
        collectionRepository.save(newCollection);
    }

    //@Cacheable(value="CollectionCnt", key="#user.userKey")
    public int getCollectionCnt(User user) {
        LOGGER.info("getCollectionCnt() : 유저의 수확한 꽃 개수 조회");

        Long count = collectionRepository.countByUser(user);
        return count.intValue();
    }
}
