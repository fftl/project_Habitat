import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:habitat/screens/alarm/notification.dart';
import 'package:web_socket_channel/io.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

const storage = FlutterSecureStorage();

Future<String?> userKey = storage.read(key: 'userKey');

final WebSocketChannel channel =
    IOWebSocketChannel.connect('ws://k8a704.p.ssafy.io/api/websocket/$userKey');

class AlarmPage extends StatefulWidget {
  const AlarmPage({super.key});

  @override
  State<AlarmPage> createState() => _AlarmPageState();
}

class _AlarmPageState extends State<AlarmPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
          child:
              // NotificationButton(
              //     text: 'notification',
              //     onPressed: () async {
              //       await NotificationService.showNotification(
              //           title: 'test', body: 'body');
              //     },
              StreamBuilder(
        stream: channel.stream,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            final data = snapshot.data as String;
            NotificationService.showNotification(title: 'Habit@', body: data);
            NotificationService.onNotificationCreatedMethod(snapshot.data);
            debugPrint(data);
            return Text(data);
          } else {
            return const Text('데이터 없음');
          }
        },
      )),
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
