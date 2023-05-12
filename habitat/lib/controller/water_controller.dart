import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get/get.dart';
import 'package:habitat/api/drinklog/api_drinklogs.dart';
import 'package:intl/intl.dart';

class WaterController extends GetxController {
  var time = DateFormat('YY/MM/dd').format(DateTime.now()).toString();
  RxInt water = 0.obs;
  RxInt goal = 1400.obs;
  RxList waterlog = [].obs;

  // 내부 저장소 접근
  static const storage = FlutterSecureStorage();

  @override
  void onInit() {
    super.onInit();
    getStorageData();
    getApiData();
  }

  getApiData() {
    getTodayDrinkLogs(success: (response) {
      waterlog.value = response;
    }, fail: (e) {
      debugPrint("에러발생 : $e");
    });
  }

  drinkwater(int drink) {
    debugPrint("마신 물의 양 : ${drink.toString()}");
    water.value += drink;
    storage.write(key: 'water', value: water.string);
    debugPrint("마신 물의 양 : $water");
  }

  getStorageData() async {
    var storageTime = await storage.read(key: 'time');
    var storageWater = await storage.read(key: 'water');

    debugPrint(water.value.toString());

    if (time == storageTime) {
      if (storageWater != null) {
        water.value = int.parse(storageWater);
      }
    } else {
      storage.write(key: 'time', value: time);
    }
  }
}
