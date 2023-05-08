import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:habitat/api/base_url.dart';
import 'package:http/http.dart' as http;

class ApiSendRequestCode {
  final String baseurl = BaseUrl().rooturl;

  void postRequestCode(String friendCode, String userKey) async {
    Uri url = Uri.http(baseurl, 'friends/request/code', {
      'userKey': userKey,
    });

    final response = await http.post(
      url,
      headers: {"Content-Type": "application/json"},
      body: jsonEncode({
        'friendCode': friendCode,
      }),
    );
    if (response.statusCode == 200) {
      debugPrint('성공');
    } else {
      debugPrint('에러남');
    }
  }
}
