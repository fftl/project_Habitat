import 'dart:io';

import 'package:flutter/material.dart';
import 'package:habitat/api/user/api_users.dart';
import 'package:habitat/controller/user_controller.dart';
import 'package:image_picker/image_picker.dart';

class SettingProfile extends StatefulWidget {
  const SettingProfile({super.key});

  @override
  State<SettingProfile> createState() => _SettingProfileState();
}

class _SettingProfileState extends State<SettingProfile> {
  final controller = Get.put(UserController());
  bool isEdited = false;
  TextEditingController tec = TextEditingController();
  late File profileImg;
  final ImagePicker _picker = ImagePicker();

  @override
  void initState() {
    super.initState();
  }

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
      body: SingleChildScrollView(
        child: Column(
          children: [
            Stack(children: [
              const CircleAvatar(
                radius: 80,
                // backgroundImage: _profileImg ==
                //         const AssetImage(
                //             'lib/assets/images/default_profile.png')
                //     ? _profileImg as ImageProvider
                //     : FileImage(File(_profileImg.path)),
              ),
              Positioned(
                  bottom: 30,
                  right: 20,
                  child: InkWell(
                    onTap: () {
                      showModalBottomSheet(
                          context: context,
                          builder: (builder) => bottomSheet());
                    },
                    child: Icon(
                      Icons.camera_alt,
                      color: Colors.grey.shade400,
                      size: 30,
                    ),
                  ))
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
                    ? Padding(
                        padding: const EdgeInsets.only(left: 5),
                        child: ElevatedButton(
                            style: ElevatedButton.styleFrom(
                              backgroundColor: Colors.grey,
                            ),
                            onPressed: () {
                              changeUserNickname(
                                  body: {'nickname': nick},
                                  success: (response) {},
                                  fail: (e) {});
                              // ApiUsers().changeUserNickname(nick, 'asdf');
                              isEdited = false;
                            },
                            child: const Text('수정')),
                      )
                    : Padding(
                        padding: const EdgeInsets.only(left: 5),
                        child: IconButton(
                          icon: const Icon(Icons.edit),
                          onPressed: () {
                            setState(() {
                              isEdited = true;
                            });
                          },
                        ),
                      )
              ],
            ),
          ],
        ),
      ),
    );
  }

  takePhoto(ImageSource source) async {
    final pickedFile = await _picker.pickImage(source: source);

    if (pickedFile != null) {
      await Future.delayed(const Duration(seconds: 1));
      profileImg = File(pickedFile.path);
    }
    setState(() {
      // _profileImg = pickedFile;
      // ApiUsers().changeUserProfile(profileImg, 'asdf');
    });
  }

  Widget bottomSheet() {
    return Container(
      height: 120,
      width: MediaQuery.of(context).size.width,
      margin: const EdgeInsets.all(10),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          SizedBox(
            child: Column(
              children: [
                IconButton(
                  onPressed: () {
                    takePhoto(ImageSource.camera);
                  },
                  icon: const Icon(Icons.camera_alt),
                  iconSize: 50,
                ),
                const Text('카메라')
              ],
            ),
          ),
          SizedBox(
            child: Column(
              children: [
                IconButton(
                  onPressed: () {
                    takePhoto(ImageSource.gallery);
                  },
                  icon: const Icon(Icons.photo_library),
                  iconSize: 50,
                ),
                const Text('사진첩')
              ],
            ),
          )
        ],
      ),
    );
  }
}
