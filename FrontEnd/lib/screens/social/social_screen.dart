import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:habitat/api/friends/api_sendrequestcode.dart';
import 'package:habitat/controller/social_controller.dart';
import 'package:habitat/screens/social/friend_list.dart';
import 'package:habitat/screens/social/friend_request_list.dart';
import 'package:habitat/widgets/dock_bar.dart';

class SocialScreen extends StatelessWidget {
  SocialScreen({super.key});

  final ScrollController scrollController = ScrollController();

  final socialController = Get.put(SocialController());

  final TextEditingController tec = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.white,
        title: const Text(
          "Friends 친구",
          style: TextStyle(
              color: Color(0xff002B20),
              fontSize: 28,
              fontWeight: FontWeight.w600),
        ),
        automaticallyImplyLeading: false,
        elevation: 0,
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 10),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Container(
                    padding: const EdgeInsets.fromLTRB(20, 10, 20, 10),
                    decoration: BoxDecoration(
                        border: Border.all(
                          color: Colors.black,
                          width: 1,
                        ),
                        borderRadius: BorderRadius.circular(10)),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      children: [
                        GetX<SocialController>(
                          builder: (controller) {
                            if (controller.userCode.value != '') {
                              return Text(
                                controller.userCode.value,
                                style: const TextStyle(
                                    fontSize: 20, fontWeight: FontWeight.w600),
                              );
                            } else {
                              return const Text('없음');
                            }
                          },
                        ),
                        GetX<SocialController>(
                          builder: (controller) {
                            if (controller.userCode.value != '') {
                              return IconButton(
                                onPressed: () {
                                  Clipboard.setData(ClipboardData(
                                      text: controller.userCode.value));
                                  // if (!mounted) return;
                                  ScaffoldMessenger.of(context)
                                      .showSnackBar(const SnackBar(
                                    content: Text('Copied to clipboard'),
                                  ));
                                },
                                icon: const Icon(Icons.copy_rounded),
                                iconSize: 20,
                                alignment: AlignmentDirectional.centerEnd,
                              );
                            } else {
                              return const Text('X');
                            }
                          },
                        ),
                      ],
                    ),
                  ),
                ],
              ),
              Padding(
                padding: const EdgeInsets.all(10),
                child: Row(
                  children: [
                    Expanded(
                      child: TextField(
                        textAlign: TextAlign.center,
                        controller: tec,
                        decoration: const InputDecoration(
                            hintText: '친구 코드를 입력하세요.', hintStyle: TextStyle()),
                      ),
                    ),
                    ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          backgroundColor: const Color(0xff47799B),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(30.0),
                          ),
                        ),
                        onPressed: () {
                          postRequestCode(
                              body: {'friendCode': tec.text},
                              success: (response) {
                                debugPrint("전송성공 : $response");
                              },
                              fail: (e) {
                                debugPrint("에러발생 : $e");
                              });
                        },
                        child: const Text('확인')),
                    // IconButton(
                    //   onPressed: () {
                    //     // ApiSendRequestCode().postRequestCode(tec.text, 'asdf');
                    //   },
                    //   icon: Image.asset(
                    //     'lib/assets/images/kakao.png',
                    //     fit: BoxFit.cover,
                    //   ),
                    //   iconSize: 30,
                    // )
                  ],
                ),
              ),
              const SizedBox(
                height: 10,
              ),
              const Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  Text(
                    '친구신청 목록',
                    style: TextStyle(fontSize: 20, fontWeight: FontWeight.w600),
                  ),
                ],
              ),
              FriendsRequestListWidget(
                scrollcontroller: scrollController,
              ),
              const SizedBox(
                height: 10,
              ),
              const Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  Text(
                    '내 친구 목록',
                    style: TextStyle(fontSize: 20, fontWeight: FontWeight.w600),
                  ),
                ],
              ),
              FriendslistWidget(
                scrollcontroller: scrollController,
              )
            ],
          ),
        ),
      ),
      bottomNavigationBar: DockBar(),
    );
  }
}
