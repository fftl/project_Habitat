import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/api/flower/api_flowers.dart';

class RewardController extends GetxController {
  RxMap exp = {}.obs;
  RxMap flower = {}.obs;
  List collection = [].obs;

  @override
  void onInit() {
    super.onInit();
    updateFlowerInfo();
    updateUserFlowerConllection();
  }

  updateFlowerInfo() {
    getGrowingFlower(success: (response) async {
      debugPrint("response : $response");
      exp.value = await response['exp'];
      flower.value = await response['flower'];
      debugPrint("유저 스탯 : ${exp.toString()}");
      debugPrint("꽃 정보 : ${flower.toString()}");
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }

  updateUserFlowerConllection() {
    getFlowerCollection(success: (response) async {
      collection = await response;
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }
}
