import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:habitat/api/base_url.dart';
import 'package:http/http.dart' as http;

// 친구 신청 목록 보기
void getRequestFriends({
  required dynamic Function(dynamic) success,
  required Function(String error) fail,
  Map<String, String>? body,
}) {
  baseApi(
    path: 'friends/request/all',
    requestType: RequestType.get,
    success: success,
    fail: fail,
  );
}

class ApiFriendRequestList {
  // final String baseurl = BaseUrl().rooturl;

  // Future<List<FriendRequestModel>> getRequestFriendList(String id) async {
  //   List<FriendRequestModel> friendlist = [];

  //   Uri url = Uri.http(baseurl, 'friends/request/all', {'userKey': id});
  //   final response = await http.get(url);
  //   if (response.statusCode == 200) {
  //     final List<dynamic> temp = jsonDecode(utf8.decode(response.bodyBytes));
  //     for (var e in temp) {
  //       friendlist.add(FriendRequestModel.fromJson(e));
  //     }
  //   }

  //   return friendlist;
  // }
}

class ApiFriendRequest {
  final String baseurl = BaseUrl().rooturl;
  final int friendRequestKey = 0;
  bool result = false;

  void postFriendRequest(int requestCode, String id) async {
    Uri url = Uri.http(baseurl, 'friends/request/ok', {'userKey': id});
    http.Response response = await http.post(url,
        body: jsonEncode({'friendRequestKey': requestCode}));

    if (response.statusCode == 200) {
      debugPrint('수락');
    }
  }

  void putFriendRequest(int requestCode, String id) async {
    Uri url = Uri.http(baseurl, 'friends/request/cancel', {'userKey': id});
    http.Response response = await http.post(url,
        body: jsonEncode({'friendRequestKey': requestCode}));

    if (response.statusCode == 200) {
      debugPrint('거절');
    }
  }
}
