import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/api/flower/api_flowers.dart';

class RewardController extends GetxController {
  RxMap<String, dynamic> exp = <String, dynamic>{}.obs;
  RxMap<String, dynamic> flower = <String, dynamic>{}.obs;
  List collection = [].obs;

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
      debugPrint("유저 스탯 : ${flowerStatus['exp']}");
      exp.value = await flowerStatus['exp'];
      flower.value = await flowerStatus['flower'];
      debugPrint("유저 스탯 : ${exp.toString()}");
      debugPrint("꽃 정보 : ${flower.toString()}");
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
      collection = await response;
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }
}
