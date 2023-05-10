import 'package:flutter/material.dart';
import 'package:habitat/screens/alarm/local_notification.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

class AlarmScreen extends StatefulWidget {
  String userKey = 'asdf';
  WebSocketChannel channel;

  AlarmScreen({super.key, required this.channel});

  @override
  State<AlarmScreen> createState() => _AlarmScreenState();
}

class _AlarmScreenState extends State<AlarmScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(8),
        child: Column(
          children: [
            StreamBuilder(
                stream: widget.channel.stream,
                builder: (context, snapshot) {
                  return Padding(
                    padding: const EdgeInsets.all(10),
                    child: snapshot.hasData
                        ? LocalNotification.showNotification() as Widget
                        : const Text('데이터없음'),
                  );
                }),
          ],
        ),
      ),
    );
  }
}
