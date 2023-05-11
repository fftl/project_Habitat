import 'package:flutter/material.dart';
import 'package:habitat/screens/alarm/local_notification.dart';

class AlarmPage extends StatefulWidget {
  const AlarmPage({super.key});

  @override
  State<AlarmPage> createState() => _AlarmPageState();
}

class _AlarmPageState extends State<AlarmPage> {
  @override
  void initState() {
    // 초기화
    LocalNotification.initialize();

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    LocalNotification.requestPermission();
    return Scaffold(
      body: Center(
        child: TextButton(
          onPressed: () {
            LocalNotification.sampleNotification();
            debugPrint('알람실행됨?');
          },
          child: const Text("알림 보내기"),
        ),
      ),
    );
  }
}
