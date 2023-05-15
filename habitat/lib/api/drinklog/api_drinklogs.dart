import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:habitat/api/base_url.dart';
import 'package:http/http.dart' as http;

// 유저의 현재까지 모든 물, 음료 섭취 기록
void getAllDrinkLogs({
  required dynamic Function(dynamic) success,
  required Function(String error) fail,
  Map<String, String>? body,
}) {
  baseApi(
    path: 'drinkLogs/all',
    requestType: RequestType.get,
    success: success,
    fail: fail,
  );
}

// 유저의 오늘 하루 물, 음료 섭취 기록
void getTodayDrinkLogs({
  required dynamic Function(dynamic) success,
  required Function(String error) fail,
  Map<String, String>? body,
}) {
  baseApi(
    path: 'drinkLogs/day',
    requestType: RequestType.get,
    success: success,
    fail: fail,
  );
}

class ApiDrinkLogs {
  final String baseurl = BaseUrl().rooturl;

  // 유저의 오늘의 누적 음수량을 조회
  Future<int> getTodaytotalDrink(String userKey) async {
    int todaytotaldrinkdata = 0;

    Uri url = Uri.http(
      baseurl,
      'drinkLogs/day/total',
      {
        'userKey': userKey,
      },
    );
    final response = await http.get(url);
    if (response.statusCode == 200) {
      // final temp = response.body;
      todaytotaldrinkdata = int.parse(response.body);
    }

    return todaytotaldrinkdata;
  }

  // 유저가 마신 물, 음료 수동 기록
  void postAddDrinkLog(int drink, String drinkType, String userKey) async {
    Uri url = Uri.http(
      baseurl,
      'drinkLogs/add',
      {
        'userKey': userKey,
      },
    );
    final response = await http.post(
      url,
      headers: {"Content-Type": "application/json"},
      body: jsonEncode({
        'drink': drink,
        'drinkType': drinkType,
      }),
    );
    if (response.statusCode == 200) {
      debugPrint("전송 성공");
    }
  }

  // 유저가 마신 물, 음료를 코스터가 자동 기록
  void postAddAutoDrinkLog(int drink, String drinkType, String userKey) async {
    Uri url = Uri.http(
      baseurl,
      'drinkLogs/auto',
      {
        'userKey': userKey,
      },
    );
    final response = await http.post(
      url,
      headers: {"Content-Type": "application/json"},
      body: jsonEncode({
        'drink': drink,
        'drinkType': drinkType,
      }),
    );
    if (response.statusCode == 200) {
      debugPrint("전송 성공");
    }
  }
}
