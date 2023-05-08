import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:habitat/api/base_url.dart';
import 'package:habitat/models/users_model.dart';
import 'package:http/http.dart' as http;

class ApiUsers {
  final String baseurl = BaseUrl().rooturl;

  // 유저의 오늘 목표 음수량 재설정
  void patchUserModifyGoal(String userKey) {
    Uri url = Uri.http(
      baseurl,
      'users/modify/goal',
      {
        'userKey': userKey,
      },
    );
    http.patch(url);
  }

  Future<List<Usersmodel>> getUserInfo(String userKey) async {
    List<Usersmodel> getuserinfodata = [];

    Uri url = Uri.http(
      baseurl,
      'users',
      {
        'userKey': userKey,
      },
    );
    final response = await http.get(url);
    if (response.statusCode == 200) {
      final temp = jsonDecode(utf8.decode(response.bodyBytes));
      getuserinfodata.add(Usersmodel.fromJson(temp));
    }

    return getuserinfodata;
  }

  void changeUserNickname(String nickname, String userKey) async {
    Uri url = Uri.http(baseurl, 'users/modify', {'userKey': userKey});

    final response = await http.patch(
      url,
      headers: {"Content-Type": "application/json"},
      body: jsonEncode({
        'friendCode': nickname,
      }),
    );

    if (response.statusCode == 200) {
      debugPrint('닉네임 변경 성공');
    }
  }
}
