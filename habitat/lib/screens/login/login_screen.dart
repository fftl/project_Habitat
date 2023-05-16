import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:habitat/api/user/api_users.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  // final usercontroller = Get.put(UserController());

  static const storage = FlutterSecureStorage();

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  Future<bool> signInWithGoogle() async {
    final GoogleSignInAccount? googleUser = await GoogleSignIn().signIn();
    if (googleUser != null) {
      String socialKey = googleUser.id;
      Future futuerMapData = postUserLogin(socialKey);
      Map<String, String> headerData = await futuerMapData;
      debugPrint("accessToken : ${headerData['accesstoken']}");
      debugPrint("refreshToken : ${headerData['refreshtoken']}");
      await LoginScreen.storage
          .write(key: 'accessToken', value: headerData['accesstoken']);
      await LoginScreen.storage
          .write(key: 'refreshToken', value: headerData['refreshtoken']);
      return true;
    } else {
      return false;
    }
  }

  late Future<bool> loginChecking;

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
                onPressed: () async {
                  loginChecking = signInWithGoogle();
                  bool? authorization = await loginChecking;
                  if (authorization) {
                    if (context.mounted) {
                      Navigator.pushReplacementNamed(context, '/main');
                    }
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
