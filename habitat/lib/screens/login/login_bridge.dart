import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:habitat/screens/login/login_screen.dart';
import 'package:habitat/screens/main/main_screen.dart';

class LoginBridge extends StatefulWidget {
  const LoginBridge({Key? key}) : super(key: key);

  @override
  State<LoginBridge> createState() => _LoginBridgeState();
}

class _LoginBridgeState extends State<LoginBridge> {
  static const storage = FlutterSecureStorage();

  String readOnboarding = '';
  String userInfo = '';

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      asyncMethod();
    });
  }

  asyncMethod() async {
    //read 함수를 통하여 key값에 맞는 정보를 불러온다. 불러오는 결과의 타입은 String
    readOnboarding = await storage.read(key: "onboarding") ?? 'none';
    userInfo = await storage.read(key: "userInfo") ?? 'none';
    debugPrint(readOnboarding);
  }

  @override
  Widget build(BuildContext context) {
    return Builder(
      builder: (BuildContext context) {
        if (readOnboarding == 'none') {
          return const MainScreen();
        } else if (userInfo == 'none') {
          return const LoginScreen();
        } else {
          return const MainScreen();
          // return Center(
          //   child: Column(children: [
          //     Text("${snapshot.data?.displayName ?? 'anon'}님 환영합니다."),
          //   ]),
          // );
        }
      },
    );
  }
}
