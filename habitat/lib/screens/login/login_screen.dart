import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:habitat/controller/user_controller.dart';

Future<UserCredential> signInWithGoogle() async {
  final GoogleSignInAccount? googleUser = await GoogleSignIn().signIn();

  final GoogleSignInAuthentication? googleAuth =
      await googleUser?.authentication;

  final credential = GoogleAuthProvider.credential(
    accessToken: googleAuth?.accessToken,
    idToken: googleAuth?.idToken,
  );

  // Once signed in, return the UserCredential
  return await FirebaseAuth.instance.signInWithCredential(credential);
}

class LoginScreen extends StatelessWidget {
  LoginScreen({super.key});

  final usercontroller = Get.put(UserController());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text("Google"),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              TextButton(
                onPressed: () async {
                  await Get.find<UserController>().signWithGoogle();
                  if (Get.find<UserController>().loginSuccess.value) {
                    Navigator.pushReplacementNamed(context, '/main');
                  }
                },
                child: const Text("Sign in with Google"),
              ),
              TextButton(
                onPressed: () {
                  Navigator.pushReplacementNamed(context, '/main');
                },
                child: const Text(
                  "비밀의 문",
                  style: TextStyle(fontSize: 32),
                ),
              ),
              GetX<UserController>(builder: (controller) {
                return Text(
                  controller.loginSuccess.value ? '' : '실패',
                  style: const TextStyle(fontSize: 32),
                );
              })
            ],
          ),
        ));
  }
}
