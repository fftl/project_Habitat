import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get/get.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:habitat/api/user/api_users.dart';

class UserController extends GetxController {
  var userKey = {};
  RxBool loginSuccess = false.obs;
  RxString name = "".obs;
  RxString profileImg = "".obs;
  RxInt goal = 1.obs;

  static const storage = FlutterSecureStorage();

  @override
  void onInit() {
    super.onInit();
    userInfoUpdate();
  }

  signWithGoogle() async {
    GoogleSignInAccount? googleUser = await GoogleSignIn().signIn();
    GoogleSignInAuthentication? googleAuth = await googleUser?.authentication;

    if (googleAuth != null) {
      userKey = {
        'acessToken': googleAuth.accessToken,
        'idToken': googleAuth.idToken
      };
      debugPrint("액세스토큰 : ${userKey['acessToken']}");
      debugPrint("아이디토큰 : ${userKey['idToken']}");
      debugPrint("아이디 : ${googleUser?.id}");
      debugPrint("이메일 : ${googleUser?.email}");
      debugPrint("디스플레이 네임 : ${googleUser?.displayName}");

      storage.write(key: 'userKey', value: userKey.toString());
      loginSuccess.value = true;
    }
  }

  userInfoUpdate() async {
    getUserInfoLogs(
      success: (response) async {
        Map userinfo = await response;
        name.value = userinfo['nickname'];
        profileImg.value = userinfo['imgUrl'];
        goal.value = userinfo['goal'];
        debugPrint("user의 닉네임 : $name / 목표음수량 : $goal");
        debugPrint("user의 프로필 사진 : $profileImg");
      },
      fail: (e) {
        debugPrint("에러발생 : $e");
      },
    );
  }
}
