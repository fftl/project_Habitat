import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:habitat/api/base_url.dart';
import 'package:habitat/models/friend_request_model.dart';
import 'package:http/http.dart' as http;

class ApiFriendRequestList {
  final String baseurl = BaseUrl().rooturl;

  Future<List<FriendRequestModel>> getRequestFriendList(String id) async {
    List<FriendRequestModel> friendlist = [];

    Uri url = Uri.http(baseurl, 'friends/request/all', {'userKey': id});
    final response = await http.get(url);
    if (response.statusCode == 200) {
      final List<dynamic> temp = jsonDecode(utf8.decode(response.bodyBytes));
      for (var e in temp) {
        friendlist.add(FriendRequestModel.fromJson(e));
      }
    }

    return friendlist;
  }
}

class ApiFriendRequest {
  final String baseurl = BaseUrl().rooturl;
  final int friendRequestKey = 0;
  bool result = false;

  void postFriendRequest(int requestCode, String userKey) async {
    Uri url = Uri.http(baseurl, 'friends/request/ok', {'userKey': userKey});

    http.Response response = await http.put(url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({'friendRequestKey': requestCode}));

    if (response.statusCode == 200) {
      debugPrint('수락');
    } else {
      debugPrint('안됨 ${response.statusCode}');
    }
  }

  void deleteFriendRequest(int requestCode, String id) async {
    Uri url = Uri.http(baseurl, 'friends/request/cancel', {'userKey': id});
    http.Response response = await http.put(url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({'friendRequestKey': requestCode}));

    if (response.statusCode == 200) {
      debugPrint('거절');
    }
  }
}
