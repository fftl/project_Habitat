import 'package:flutter/material.dart';
import 'package:habitat/screens/alarm/notification.dart';
import 'package:habitat/screens/alarm/notification_button.dart';

class AlarmPage extends StatefulWidget {
  const AlarmPage({super.key});

  @override
  State<AlarmPage> createState() => _AlarmPageState();
}

class _AlarmPageState extends State<AlarmPage> {
  @override
  void initState() {
    // 초기화

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
          child: NotificationButton(
              text: 'notification',
              onPressed: () async {
                await NotificationService.showNotification(
                    title: 'test', body: 'body');
              })),
    );
  }
}

class Alarm extends StatelessWidget {
  const Alarm({super.key});

  static GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      navigatorKey: navigatorKey,
    );
  }
}
