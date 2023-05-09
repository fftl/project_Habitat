import 'package:flutter/material.dart';
import 'package:habitat/api/user/api_users.dart';

class SettingProfile extends StatefulWidget {
  const SettingProfile({super.key});

  @override
  State<SettingProfile> createState() => _SettingProfileState();
}

class _SettingProfileState extends State<SettingProfile> {
  bool isEdited = false;
  late String nick;
  TextEditingController tec = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        leading: const BackButton(color: Color(0xff002B20)),
        title: const Text(
          '내 정보 수정',
          style: TextStyle(color: Color(0xff002B20)),
        ),
      ),
      body: Column(
        children: [
          const Row(mainAxisAlignment: MainAxisAlignment.center, children: [
            Icon(
              Icons.local_florist_rounded,
              size: 150,
            ),
          ]),
          const SizedBox(
            height: 20,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              isEdited
                  ? SizedBox(
                      width: 100,
                      height: 10,
                      child: TextField(
                        controller: tec,
                        onChanged: (val) {
                          setState(() {
                            nick = val;
                          });
                        },
                      ))
                  : const Text(
                      "쏘영쏘",
                      style: TextStyle(
                        fontSize: 30,
                        fontWeight: FontWeight.w600,
                      ),
                    ),
              isEdited
                  ? TextButton(
                      onPressed: () {
                        ApiUsers().changeUserNickname(nick, 'asdf');
                      },
                      child: const Text('수정'))
                  : IconButton(
                      icon: const Icon(Icons.edit),
                      onPressed: () {
                        setState(() {
                          isEdited = true;
                        });
                      },
                    )
            ],
          ),
        ],
      ),
    );
  }
}
