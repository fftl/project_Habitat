import 'package:flutter/material.dart';
import 'package:habitat/screens/alarm/alarm_screen.dart';
import 'package:habitat/screens/settingscreen/coaster_connect.dart';
import 'package:habitat/screens/settingscreen/modify_goal_screen.dart';
import 'package:habitat/screens/settingscreen/setting_cash.dart';
import 'package:get/get.dart';
import 'package:habitat/controller/user_controller.dart';
import 'package:habitat/screens/settingscreen/setting_box.dart';
import 'package:habitat/screens/settingscreen/setting_profile.dart';
import 'package:habitat/screens/settingscreen/setting_water.dart';
import 'package:habitat/widgets/dock_bar.dart';

class SettingScreen extends StatelessWidget {
  SettingScreen({super.key});

  final userController = Get.put(UserController());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: const Text(
          "Setting 설정",
          style: TextStyle(
            color: Color(0xff002B20),
            fontSize: 28,
            fontWeight: FontWeight.w600,
          ),
        ),
        automaticallyImplyLeading: false,
        backgroundColor: Colors.transparent,
        elevation: 0,
      ),
      body: ListView(
        children: [
          Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              GetX<UserController>(
                builder: (controller) {
                  return Container(
                    width: MediaQuery.of(context).size.width,
                    margin: const EdgeInsets.symmetric(
                        horizontal: 50, vertical: 20),
                    height: 100,
                    // width: 200,
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: const BorderRadius.all(
                        Radius.circular(12),
                      ),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.grey.withOpacity(0.4),
                          blurRadius: 2.0,
                          spreadRadius: 0.0,
                          offset: const Offset(0, 0),
                        )
                      ],
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(right: 20),
                          child: Image.network(
                            controller.profileImg.value,
                            // "https://your-habitat.s3.ap-northeast-2.amazonaws.com/static/default.png",
                            width: 48,
                          ),
                        ),
                        Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              children: [
                                Text(
                                  controller.name.value,
                                  style: const TextStyle(
                                    fontSize: 18,
                                    fontWeight: FontWeight.w600,
                                  ),
                                ),
                                IconButton(
                                  icon: const Icon(Icons.edit),
                                  onPressed: () {
                                    Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) =>
                                                const SettingProfile()));
                                  },
                                )
                              ],
                            ),
                            Text(
                              "목표 음수량 : ${controller.goal}ml",
                              style: const TextStyle(
                                fontSize: 18,
                                fontWeight: FontWeight.w300,
                                color: Colors.grey,
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  );
                },
              ),
              const SettingBox(),
            ],
          ),
        ],
      ),
      bottomNavigationBar: DockBar(),
    );
  }
}

class settingbox extends StatefulWidget {
  const settingbox({
    super.key,
  });

  @override
  State<settingbox> createState() => _settingboxState();
}

class _settingboxState extends State<settingbox> {
  onSettingGoal() {
    Navigator.push(
        context, MaterialPageRoute(builder: (context) => ModifyGoalScreen()));
  }

  onSettingWater() {
    Navigator.push(
        context, MaterialPageRoute(builder: (context) => const SettingWater()));
  }

  onDeleteCoaster() {}

  onSettingCoaster() {}

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(left: 70, bottom: 20),
      alignment: Alignment.bottomLeft,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          TextButton(
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (context) => const AlarmPage()));
              },
              child: const Text('test')),
          const Text(
            "목표설정",
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.w600,
            ),
          ),
          const SizedBox(
            height: 6,
          ),
          TextButton(
            onPressed: onSettingGoal,
            style: TextButton.styleFrom(
              // minimumSize: Size.zero,
              foregroundColor: const Color(0xff47799B),
              padding: const EdgeInsets.only(left: 10),
              tapTargetSize: MaterialTapTargetSize.shrinkWrap,
            ),
            child: const Text(
              "목표 음수량 설정",
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.w400,
              ),
            ),
          ),
          TextButton(
            onPressed: onSettingWater,
            style: TextButton.styleFrom(
              foregroundColor: const Color(0xff47799B),
              padding: const EdgeInsets.only(left: 10),
              tapTargetSize: MaterialTapTargetSize.shrinkWrap,
            ),
            child: const Text(
              "목표 음수량 추천받기",
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.w400,
              ),
            ),
          ),
          const SizedBox(
            height: 15,
          ),
          const Text(
            "코스터 설정",
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.w600,
            ),
          ),
          const SizedBox(
            height: 6,
          ),
          TextButton(
            onPressed: () {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const CoasterConnect(),
                  ));
            },
            style: TextButton.styleFrom(
              // minimumSize: Size.zero,
              foregroundColor: const Color(0xff47799B),
              padding: const EdgeInsets.only(left: 10),
              tapTargetSize: MaterialTapTargetSize.shrinkWrap,
            ),
            child: const Text(
              "코스터 등록",
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.w400,
              ),
            ),
          ),
          TextButton(
            onPressed: onDeleteCoaster,
            style: TextButton.styleFrom(
              foregroundColor: const Color(0xff47799B),
              padding: const EdgeInsets.only(left: 10),
              tapTargetSize: MaterialTapTargetSize.shrinkWrap,
            ),
            child: const Text(
              "코스터 삭제",
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.w400,
              ),
            ),
          ),
          TextButton(
            onPressed: onSettingCoaster,
            style: TextButton.styleFrom(
              foregroundColor: const Color(0xff47799B),
              padding: const EdgeInsets.only(left: 10),
              tapTargetSize: MaterialTapTargetSize.shrinkWrap,
            ),
            child: const Text(
              "코스터 알림설정",
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.w400,
              ),
            ),
          ),
          const SizedBox(
            height: 15,
          ),
          const Text(
            "앱 설정",
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.w600,
            ),
          ),
          const SizedBox(
            height: 6,
          ),
          TextButton(
            onPressed: () {},
            style: TextButton.styleFrom(
              // minimumSize: Size.zero,
              foregroundColor: const Color(0xff47799B),
              padding: const EdgeInsets.only(left: 10),
              tapTargetSize: MaterialTapTargetSize.shrinkWrap,
            ),
            child: const Text(
              "앱 알림설정",
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.w400,
              ),
            ),
          ),
          TextButton(
            onPressed: () {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const SettingCash(),
                  ));
            },
            style: TextButton.styleFrom(
              foregroundColor: const Color(0xff47799B),
              padding: const EdgeInsets.only(left: 10),
              tapTargetSize: MaterialTapTargetSize.shrinkWrap,
            ),
            child: const Text(
              "앱 캐시 삭제",
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.w400,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
