import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:habitat/screens/main/main_screen.dart';

class OnboardingScreen extends StatefulWidget {
  const OnboardingScreen({super.key});

  @override
  State<OnboardingScreen> createState() => _OnboardingScreenState();
}

class _OnboardingScreenState extends State<OnboardingScreen> {
  static const storage = FlutterSecureStorage();
  dynamic check = "asd";

  checkOnboarding() async {
    var check = await storage.read(key: 'onboarding');
  }

  readOnboarding() async {
    await storage.write(key: 'onboarding', value: 'onboarding');
  }

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      checkOnboarding();
      debugPrint("온보딩 체크 $check");
      if (check != null) {
        Navigator.pushNamed(context, '/main');
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Center(
            child: TextButton(
              style: TextButton.styleFrom(
                backgroundColor: Colors.white,
              ),
              onPressed: () {
                readOnboarding();
                debugPrint("넘어가기 전 체크 $check");
                Navigator.pushAndRemoveUntil(
                    context,
                    MaterialPageRoute(
                        builder: (BuildContext context) => const MainScreen()),
                    (route) => false);
              },
              child: const Text("시작"),
            ),
          ),
        ],
      ),
    );
  }
}
