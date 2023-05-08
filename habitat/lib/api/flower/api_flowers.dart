import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:habitat/api/base_url.dart';
import 'package:habitat/models/flower_model.dart';
import 'package:http/http.dart' as http;

class ApiFlowers {
  final String baseurl = BaseUrl().rooturl;

  // 현재 육성중인 꽃의 스테이터스
  Future<List<FlowerStatusModel>> getGrowingFlower(String userKey) async {
    List<FlowerStatusModel> growingFlowerData = [];

    Uri url = Uri.http(
      baseurl,
      'flowers/exp',
      {
        'userKey': userKey,
      },
    );
    final response = await http.get(url);
    if (response.statusCode == 200) {
      final temp = jsonDecode(utf8.decode(response.bodyBytes));
      // for (var e in temp) {
      growingFlowerData.add(FlowerStatusModel.fromJson(temp));
      debugPrint(growingFlowerData.toString());
      // }
    }

    return growingFlowerData;
  }

  // 유저가 가진 꽃 콜렉션 조회
  Future<List<FlowerCollectionModel>> getFlowerCollection(
      String userKey) async {
    List<FlowerCollectionModel> flowerCollectionData = [];

    Uri url = Uri.http(
      baseurl,
      'flowers/collection',
      {
        'userKey': userKey,
      },
    );
    final response = await http.get(url);
    if (response.statusCode == 200) {
      final temp = jsonDecode(utf8.decode(response.bodyBytes));
      for (var e in temp) {
        flowerCollectionData.add(FlowerCollectionModel.fromJson(e));
        debugPrint(e.toString());
      }
    }

    return flowerCollectionData;
  }
}
