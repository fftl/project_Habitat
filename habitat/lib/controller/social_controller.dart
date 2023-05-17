import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/api/friends/api_friendcode.dart';
import 'package:habitat/api/friends/api_friendrequest.dart';

class SocialController extends GetxController {
  RxString userCode = ''.obs;
  RxList<Map<String, dynamic>> requestfriendslist =
      <Map<String, dynamic>>[].obs;

  @override
  void onInit() {
    super.onInit();
    getSocialCode();
    getSocialRequest();
  }

  getSocialCode() {
    getCode(success: (response) {
      userCode.value = response['friendCode'];
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }

  getSocialRequest() {
    getRequestFriends(
      success: (res) async {
        List temp = <Map<String, dynamic>>[];
        temp = await res.map((dynamic item) => item).toList() ?? [];
        for (var value in temp) {
          requestfriendslist.add(value);
        }
        debugPrint('친구신청 : $requestfriendslist');
      },
      fail: (error) {
        debugPrint('에러 : $error');
      },
    );
  }
}
