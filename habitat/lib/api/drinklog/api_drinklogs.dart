import 'package:habitat/api/base_url.dart';

// 유저의 현재까지 모든 물, 음료 섭취 기록
void getAllDrinkLogs({
  required dynamic Function(dynamic) success,
  required Function(String error) fail,
  Map<String, String>? body,
}) {
  baseApi(
    path: 'drinkLogs/all',
    requestType: RequestType.get,
    success: success,
    fail: fail,
  );
}

// 유저의 오늘 하루 물, 음료 섭취 기록
void getTodayDrinkLogs({
  required dynamic Function(dynamic) success,
  required Function(String error) fail,
  Map<String, String>? body,
}) {
  baseApi(
    path: 'drinkLogs/day',
    requestType: RequestType.get,
    success: success,
    fail: fail,
  );
}

// 유저의 오늘의 누적 음수량을 조회
void getTodaytotalDrink({
  required dynamic Function(dynamic) success,
  required Function(String error) fail,
  Map<String, String>? body,
}) {
  baseApi(
    path: 'drinkLogs/day/total',
    requestType: RequestType.get,
    success: success,
    fail: fail,
  );
}

// 유저가 마신 물, 음료 수동 기록
void postAddDrinkLog({
  required dynamic Function(dynamic) success,
  required Function(String error) fail,
  Map<String, dynamic>? body,
}) {
  baseApi(
    path: 'drinkLogs/add',
    requestType: RequestType.post,
    body: body,
    success: success,
    fail: fail,
  );
}

// 유저가 마신 물, 음료를 코스터가 자동 기록
void postAddAutoDrinkLog({
  required dynamic Function(dynamic) success,
  required Function(String error) fail,
  Map<String, dynamic>? body,
}) {
  baseApi(
    path: 'drinkLogs/auto',
    requestType: RequestType.post,
    body: body,
    success: success,
    fail: fail,
  );
}

void getWeeklyIntake({
  required dynamic Function(dynamic) success,
  required Function(String error) fail,
  Map<String, dynamic>? body,
}) {
  baseApi(
    path: 'drinkLogs/week/total',
    requestType: RequestType.get,
    success: success,
    fail: fail,
  );
}

void getMonthlyIntake({
  required dynamic Function(dynamic) success,
  required Function(String error) fail,
  Map<String, dynamic>? body,
}) {
  baseApi(
    path: 'drinkLogs/month/total',
    requestType: RequestType.get,
    success: success,
    fail: fail,
  );
}
