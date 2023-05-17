import 'package:firebase_core/firebase_core.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:habitat/screens/alarm/notification.dart';
import 'package:habitat/screens/login/login_screen.dart';
import 'package:habitat/screens/main/main_screen.dart';
import 'package:habitat/screens/onboarding/onboarding_screen.dart';
import 'package:web_socket_channel/io.dart';
import 'package:web_socket_channel/web_socket_channel.dart';
import 'firebase_options.dart';
import 'package:flutter/material.dart';
import 'package:habitat/screens/coaster/coaster_connect_screen.dart';
import 'package:habitat/screens/loading/loading_screen.dart';
import 'package:habitat/screens/login/login_bridge.dart';
import 'package:habitat/screens/reward/reward_screen.dart';
import 'package:habitat/screens/settingscreen/setting_screen.dart';
import 'package:habitat/screens/social/social_screen.dart';
import 'package:habitat/screens/report/report_screen.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );

  await NotificationService.initializeNotification();

  const storage = FlutterSecureStorage();
  Future<String?> userKey = storage.read(key: 'userKey');

  final WebSocketChannel channel = IOWebSocketChannel.connect(
      'ws://k8a704.p.ssafy.io/api/websocket/$userKey');

  GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();

  runApp(
    MaterialApp(
      navigatorKey: navigatorKey,
      title: "habit@",
      theme: ThemeData(
        fontFamily: "SeoulNamsan",
        primaryColor: const Color(0xFF78C6F7),
        scaffoldBackgroundColor: const Color(0xFFA1EF7A),
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => const LoginBridge(),
        // '/': (context) => MainScreen(),
        '/onboarding': (context) => const OnboardingScreen(),
        '/login': (context) => const LoginScreen(),
        '/main': (context) => MainScreen(),
        '/report': (context) => const ReportScreen(),
        '/reward': (context) => RewardScreen(),
        '/social': (context) => SocialScreen(),
        '/setting': (context) => SettingScreen(),
        '/loading': (context) => const LoadingScreen(),
        '/bluetooth': (context) => CoasterConnectScreen(),
      },
    ),
  );
}
