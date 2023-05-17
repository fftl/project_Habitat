import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/api/friends/api_friendcode.dart';

class SocialController extends GetxController {
  RxString userCode = ''.obs;

  @override
  void onInit() {
    super.onInit();
    getSocialCode();
  }

  getSocialCode() {
    getCode(success: (response) {
      userCode.value = response['friendCode'];
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }
}
