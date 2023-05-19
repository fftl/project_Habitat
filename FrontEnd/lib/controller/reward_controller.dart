import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/api/flower/api_flowers.dart';

class RewardController extends GetxController {
  RxMap<String, dynamic> exp = <String, dynamic>{}.obs;
  RxMap<String, dynamic> flower = <String, dynamic>{}.obs;
  RxList<Map<String, dynamic>> collection = <Map<String, dynamic>>[].obs;

  @override
  void onInit() {
    super.onInit();
    flowerInfoUpdate();
    flowerConllectionUpdate();
  }

  flowerInfoUpdate() {
    getGrowingFlower(success: (response) async {
      debugPrint("response : $response");
      Map flowerStatus = {};
      flowerStatus = await response;

      exp.value = await flowerStatus['exp'];
      flower.value = await flowerStatus['flower'];

      debugPrint("꽃 정보 : ${flower['story']}");
      debugPrint("꽃 정보 type : ${flower['story'].runtimeType}");
      debugPrint("경험치 정보 : ${exp['exp'].toString()}");
      debugPrint("경험치 정보 type : ${exp['exp'].runtimeType}");
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }

  flowerConllectionUpdate() {
    getFlowerCollection(success: (response) async {
      List flowerCollection = <Map<String, dynamic>>[];
      flowerCollection =
          await response.map((dynamic item) => item).toList() ?? [];
      collection.clear();
      for (var value in flowerCollection) {
        collection.add(value);
      }

      debugPrint("플라워 스테이터스 : $flowerCollection");
      debugPrint("플라워 스테이터스 타입 : ${flowerCollection.runtimeType}");
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }
}
