import 'package:flutter/material.dart';
import 'package:habitat/screens/settingscreen/coaster_connect.dart';
import 'package:habitat/screens/settingscreen/modify_goal_screen.dart';
import 'package:habitat/screens/settingscreen/setting_cash.dart';
import 'package:habitat/screens/settingscreen/setting_water.dart';

class SettingBox extends StatefulWidget {
  const SettingBox({
    super.key,
  });

  @override
  State<SettingBox> createState() => _SettingBoxState();
}

class _SettingBoxState extends State<SettingBox> {
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
