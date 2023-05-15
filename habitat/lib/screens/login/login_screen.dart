import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:habitat/api/user/api_users.dart';

class LoginScreen extends StatelessWidget {
  const LoginScreen({super.key});

  // final usercontroller = Get.put(UserController());

  static const storage = FlutterSecureStorage();

  void signInWithGoogle() async {
    final GoogleSignInAccount? googleUser = await GoogleSignIn().signIn();
    if (googleUser != null) {
      String socialKey = googleUser.id;
      Future futuerMapData = postUserLogin(socialKey);
      Map<String, String> headerData = await futuerMapData;
      debugPrint("accessToken : ${headerData['accesstoken']}");
      debugPrint("refreshToken : ${headerData['refreshtoken']}");
      await storage.write(key: 'accessToken', value: headerData['accesstoken']);
      await storage.write(
          key: 'refreshToken', value: headerData['refreshtoken']);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.white,
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Image.asset('lib/assets/images/characters/MangWull.png'),
              const SizedBox(
                height: 20,
              ),
              TextButton(
                onPressed: () {
                  signInWithGoogle();
                  if (storage.read(key: 'accessToken') != '') {
                    Navigator.pushReplacementNamed(context, '/main');
                  }
                },
                child: const Text(
                  "Sign in with Google",
                  style: TextStyle(
                    fontSize: 24,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ],
          ),
        ));
  }
}
