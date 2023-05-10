import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:habitat/screens/login/login_screen.dart';
import 'package:habitat/screens/report_screen.dart';

class LoginBridge extends StatelessWidget {
  const LoginBridge({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return StreamBuilder(
      stream: FirebaseAuth.instance.authStateChanges(),
      builder: (BuildContext context, AsyncSnapshot<User?> snapshot) {
        if (!snapshot.hasData) {
          return const LoginScreen();
        } else {
          return const ReportScreen();
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
