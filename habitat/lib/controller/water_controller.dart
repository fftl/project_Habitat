import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/api/drinklog/api_drinklogs.dart';
import 'package:habitat/api/user/api_users.dart';

class WaterController extends GetxController {
  RxInt water = 0.obs;
  RxInt goal = 1.obs;
  RxList waterlog = [].obs;

  @override
  void onInit() {
    super.onInit();
    waterUpdate();
    goalUpdate();
    waterLogUpdate();
  }

  waterUpdate() {
    getTodaytotalDrink(success: (response) {
      water.value = response['totalDrink'];
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }

  goalUpdate() {
    getUserInfoLogs(success: (response) {
      goal.value = response['goal'];
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }

  waterLogUpdate() {
    getTodayDrinkLogs(success: (response) {
      waterlog.value = response;
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }

  drinkWater(Map<String, dynamic> waterData) {
    postAddDrinkLog(
        body: waterData,
        success: (response) {
          debugPrint("전송성공 : $response");
        },
        fail: (e) {
          debugPrint("에러발생 : $e");
        });

    int drinkwater = waterData['drink'];
    water.value += drinkwater;

    debugPrint("오늘 마신 물의 총 양 : $water");
  }

  autoDrinkWater(int drink) {
    water.value += drink;
  }
}
