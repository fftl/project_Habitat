import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get/get.dart';
import 'package:habitat/api/friends/api_friendcode.dart';
import 'package:habitat/api/friends/api_friendrequest.dart';
import 'package:habitat/api/friends/api_friendslist.dart';
import 'package:habitat/screens/alarm/notification.dart';
import 'package:web_socket_channel/io.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

const storage = FlutterSecureStorage();

class SocialController extends GetxController {
  RxString userCode = ''.obs;
  RxList<Map<String, dynamic>> requestfriendslist =
      <Map<String, dynamic>>[].obs;
  RxList<Map<String, dynamic>> friendslist = <Map<String, dynamic>>[].obs;
  RxString msg = ''.obs;
  @override
  void onInit() {
    super.onInit();
    getSocialCode();
    getSocialRequest();
    getSocialList();
    getSocketMessage();
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

  getSocialList() {
    getFriendsList(
      success: (res) async {
        List temp = <Map<String, dynamic>>[];
        temp = await res.map((dynamic item) => item).toList() ?? [];
        for (var value in temp) {
          friendslist.add(value);
        }
        debugPrint('친구 : $friendslist');
      },
      fail: (error) {
        debugPrint('에러 : $error');
      },
    );
  }

  getSocketMessage() async {
    Future<String?> getuserKey = storage.read(key: 'userKey');
    String? userKey = await getuserKey;
    Future<String?> accessToken = storage.read(key: "accessToken");
    String? authorization = await accessToken;

    final WebSocketChannel channel = IOWebSocketChannel.connect(
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer $authorization",
      },
      'ws://k8a704.p.ssafy.io:8081/api/websocket/$userKey',
    );
    StreamBuilder(
      stream: channel.stream,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          // msg = snapshot.data as String;
          NotificationService.onNotificationCreatedMethod(snapshot.data);
          msg = snapshot.data;
          debugPrint(msg.toString());
          return snapshot.data;
        } else {
          debugPrint('안보내짐');
          return const Text('데이터 없음');
        }
      },
    );

    if (msg != '') {
      NotificationService.showNotification(
          title: 'Habit@', body: msg.toString());
    }
  }
}
