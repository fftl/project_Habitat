import 'package:flutter/material.dart';
// import 'package:habitat/api/user/api_users.dart';
import 'package:get/get.dart';
import 'package:habitat/api/user/api_users.dart';
import 'package:habitat/controller/user_controller.dart';

class ModifyGoalScreen extends StatelessWidget {
  ModifyGoalScreen({super.key});

  TextEditingController tec = TextEditingController();

  final controller = Get.put(UserController());
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        leading: const BackButton(color: Color(0xff002B20)),
        title: const Text(
          '목표 음수량 설정',
          style: TextStyle(color: Color(0xff002B20)),
        ),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              SizedBox(
                width: 300,
                child: TextField(
                    textAlign: TextAlign.center,
                    controller: tec,
                    decoration: const InputDecoration(
                        hintText: '목표 음수량을 입력하세요.', hintStyle: TextStyle())),
              ),
              const Text('ml'),
            ],
          ),
          const SizedBox(
            height: 20,
          ),
          ElevatedButton(
              style: ElevatedButton.styleFrom(
                  backgroundColor: const Color(0xff47799B)),
              onPressed: () {
                patchUserModifyGoal(
                    body: {'goal': double.parse(tec.text)},
                    success: (res) {
                      debugPrint('성공');
                    },
                    fail: (e) {
                      debugPrint('에러');
                    });
                // ApiUsers().patchUserModifyGoal('asdf', double.parse(tec.text));
              },
              child: const Text('목표 음수량 설정하기'))
        ],
      ),
    );
  }
}
