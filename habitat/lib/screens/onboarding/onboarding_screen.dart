import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:habitat/screens/login/login_screen.dart';
import 'package:smooth_page_indicator/smooth_page_indicator.dart';

class OnboardingScreen extends StatefulWidget {
  const OnboardingScreen({super.key});

  @override
  State<OnboardingScreen> createState() => _OnboardingScreenState();
}

class _OnboardingScreenState extends State<OnboardingScreen> {
  static const storage = FlutterSecureStorage();
  dynamic check = "";

  readOnboarding() async {
    await storage.write(key: 'onboarding', value: 'onboarding');
  }

  final PageController pageController = PageController(
    initialPage: 0,
  );

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        alignment: Alignment.center,
        children: [
          PageView(
            controller: pageController,
            children: [
              // 첫 번째 페이지
              SizedBox.expand(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Stack(
                      alignment: Alignment.center,
                      children: [
                        Container(
                          color: Colors.red,
                          height: 400,
                        ),
                        Image.asset('lib/assets/images/sunflower.png')
                      ],
                    ),
                    const Text(
                      'Page index : 0',
                      style: TextStyle(fontSize: 20),
                    ),
                  ],
                ),
              ),
              // 두 번째 페이지
              SizedBox.expand(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Stack(
                      alignment: Alignment.center,
                      children: [
                        Container(
                          color: Colors.yellow,
                          height: 400,
                        ),
                        Image.asset('lib/assets/images/sunflower.png')
                      ],
                    ),
                    const Text(
                      'Page index : 1',
                      style: TextStyle(fontSize: 20),
                    ),
                  ],
                ),
              ),
              // 세 번째 페이지
              SizedBox.expand(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Stack(
                      alignment: Alignment.center,
                      children: [
                        Container(
                          color: Colors.green,
                          height: 400,
                        ),
                        Image.asset('lib/assets/images/sunflower.png')
                      ],
                    ),
                    const Text(
                      'Page index : 2',
                      style: TextStyle(fontSize: 20),
                    ),
                  ],
                ),
              ),
              // 네 번째 페이지
              SizedBox.expand(
                child: Center(
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
                              builder: (BuildContext context) => const LoginScreen()),
                          (route) => false);
                    },
                    child: const Text("시작하기"),
                  ),
                ),
              ),
            ],
          ),
          Positioned(
            bottom: 30,
            child: SmoothPageIndicator(
              controller: pageController, // PageController
              count: 4,
              effect: const ScaleEffect(
                activeDotColor: Colors.lightBlue,
              ), // your preferred effect
              onDotClicked: (index) {
                pageController.animateToPage(
                  index,
                  duration: const Duration(milliseconds: 200),
                  curve: Curves.ease,
                );
              },
            ),
          )
        ],
      ),
    );
  }
}
