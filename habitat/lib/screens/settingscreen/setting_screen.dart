import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/controller/user_controller.dart';
import 'package:habitat/screens/settingscreen/setting_box.dart';
import 'package:habitat/screens/settingscreen/setting_profile.dart';
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
      bottomNavigationBar: const DockBar(),
    );
  }
}
