package com.ssafy.habitat.controller;

import com.ssafy.habitat.service.FriendRequestService;
import com.ssafy.habitat.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/friends")
public class FriendController {
    private FriendService friendService;
    private FriendRequestService friendRequestService;

    @Autowired
    public FriendController(FriendService friendService, FriendRequestService friendRequestService) {
        this.friendService = friendService;
        this.friendRequestService = friendRequestService;
    }
}
