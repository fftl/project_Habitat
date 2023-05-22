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
      backgroundColor: Colors.transparent,
      body: Container(
        decoration: const BoxDecoration(
          gradient: LinearGradient(
            colors: [Color(0xFF78C6F7), Colors.white],
            stops: [0, 1],
            begin: Alignment.topCenter,
            end: Alignment.bottomCenter,
          ),
        ),
        child: Stack(
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
                      Image.asset(
                        'lib/assets/images/cup.png',
                        scale: 2,
                      ),
                      const SizedBox(
                        height: 20,
                      ),
                      const Text(
                        '하루 필수 수분 섭취량,',
                        style: TextStyle(fontSize: 20),
                      ),
                      const Text(
                        '어떻게 챙기고 계신가요?',
                        style: TextStyle(fontSize: 20),
                      )
                    ],
                  ),
                ),
                // 두 번째 페이지
                SizedBox.expand(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Image.asset(
                        'lib/assets/images/umbrella.png',
                        scale: 2,
                      ),
                      const SizedBox(
                        height: 20,
                      ),
                      const Text(
                        '기후와 활동량에 따라서도',
                        style: TextStyle(fontSize: 20),
                      ),
                      const Text(
                        '달라지는 수분섭취량!',
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
                            width: 300,
                            height: 300,
                            decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(1000),
                                color: const Color(0xffE8FCDB)),
                          ),
                          Image.asset(
                            'lib/assets/images/seed.png',
                            scale: 5,
                          )
                        ],
                      ),
                      const SizedBox(
                        height: 20,
                      ),
                      const Text(
                        '내가 마신 물로 식물을 키워보세요!',
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
                          // backgroundColor: Colors.transparent,
                          // textStyle: const TextStyle(
                          //   color: Color(0x0ff002b2),
                          ),
                      onPressed: () {
                        readOnboarding();
                        debugPrint("넘어가기 전 체크 $check");
                        Navigator.pushAndRemoveUntil(
                            context,
                            MaterialPageRoute(
                                builder: (BuildContext context) =>
                                    const LoginScreen()),
                            (route) => false);
                      },
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Image.asset(
                            'lib/assets/images/characters/bigMangWull.png',
                            scale: 2,
                          ),
                          const SizedBox(
                            height: 20,
                          ),
                          const Text(
                            "시작하기",
                            style: TextStyle(
                              color: Colors.grey,
                              fontSize: 20,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                )
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
      ),
    );
  }
}
