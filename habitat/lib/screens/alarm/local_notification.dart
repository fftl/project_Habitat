import 'package:flutter_local_notifications/flutter_local_notifications.dart';

class LocalNotification {
  LocalNotification._();

  static final notifications = FlutterLocalNotificationsPlugin();

  static initNotification() async {
    var androidSetting =
        const AndroidInitializationSettings('@mipmap/ic_launcher');

    var iosSetting = const IOSInitializationSettings(
      requestAlertPermission: true,
      requestBadgePermission: true,
      requestSoundPermission: true,
    );

    var initializationSetting =
        InitializationSettings(android: androidSetting, iOS: iosSetting);

    await notifications.initialize(
      initializationSetting,
    );
  }

  static Future<void> showNotification() async {
    var androidDetails = const AndroidNotificationDetails('pook', '찌르기',
        priority: Priority.high, importance: Importance.max);

    var iosDetails = const IOSNotificationDetails(
      presentAlert: true,
      presentBadge: true,
      presentSound: true,
    );

    await notifications.show(1, '테스트1', '내용테스트',
        NotificationDetails(android: androidDetails, iOS: iosDetails));
  }
}
