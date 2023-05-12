import 'dart:convert';
import 'dart:io';
import 'package:flutter/material.dart';
import 'package:habitat/api/base_url.dart';
import 'package:habitat/models/users_model.dart';
import 'package:http/http.dart' as http;

Future<Map<String, String>> postAddDrinkLog(String socialKey) async {
  Uri url = Uri.parse('http://k8a704.p.ssafy.io:8081/api/users/login');
  final response = await http.post(
    url,
    headers: {"Content-Type": "application/json"},
    body: jsonEncode({
      'socialKey': socialKey,
      'socialType': 1,
    }),
  );
  if (response.statusCode == 200) {
    late dynamic jsonResponse;
    jsonResponse = response.headers;
    return jsonResponse;
  }
  return {};
}

class ApiUsers {
  final String baseurl = BaseUrl().rooturl;

  // 유저의 오늘 목표 음수량 재설정
  void patchUserModifyGoal(String userKey, double goal) async {
    Uri url = Uri.http(
      baseurl,
      'users/modify/goal',
      {
        'userKey': userKey,
      },
    );
    final response = await http.patch(
      url,
      headers: {"Content-Type": "application/json"},
      body: jsonEncode({'goal': goal}),
    );
    if (response.statusCode == 200) {
      debugPrint('성공');
    } else {
      debugPrint('에러남');
    }
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
        'nickname': nickname,
      }),
    );

    if (response.statusCode == 200) {
      debugPrint('닉네임 변경 성공');
    }
  }

  Future<void> changeUserProfile(File file, String userKey) async {
    Uri url = Uri.http(baseurl, 'users/modify/img', {'userKey': userKey});
    var request = http.MultipartRequest('PATCH', url);

    String fileName = file.path.split('/').last;

    request.files.add(http.MultipartFile.fromBytes(
        'file', File(file.path).readAsBytesSync(),
        filename: fileName));

    var res = await request.send();

    if (res.statusCode == 200) {
      debugPrint('이미지 업로드 성공');
    } else {
      debugPrint('에러${res.statusCode}');
    }
  }
}
