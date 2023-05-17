import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/api/drinklog/api_drinklogs.dart';
import 'package:habitat/api/user/api_users.dart';

class ReportController extends GetxController {
  RxInt water = 0.obs;
  RxInt goal = 1.obs;
  RxList weekly = [].obs;
  RxList monthly = [].obs;

  @override
  void onInit() {
    super.onInit();
    goalUpdate();
    weeklyIntakeUpdate();
    monthlyIntakeUpdate();
  }

  goalUpdate() {
    getUserInfoLogs(success: (response) {
      goal.value = response['goal'];
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }

  weeklyIntakeUpdate() {
    getWeeklyIntake(success: (response) {
      weekly.value = response;
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }

  monthlyIntakeUpdate() {
    getMonthlyIntake(success: (response) {
      monthly.value = response;
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }
}
