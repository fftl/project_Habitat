import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/api/friends/api_friendrequest.dart';
import 'package:habitat/controller/social_controller.dart';

class friendsRequestListWidget extends StatelessWidget {
  final ScrollController scrollcontroller;

  friendsRequestListWidget({
    super.key,
    required this.scrollcontroller,
  });
  final controller = Get.put(SocialController());

  // void getRequestFriendCode() {
  //   WidgetsBinding.instance.addPostFrameCallback((_) {
  //     int friendRequestKey:
  //     controller.requestfriendslist['friendRequestKey'];
  //   });
  // }
  // final Future<List<FriendsListModel>> friendslistdata =

  void delete(int friendRequestKey) {
    controller.requestfriendslist.removeWhere(
        (request) => request['friendRequestKey'] == friendRequestKey);
  }

  @override
  Widget build(BuildContext context) {
    return GetX<SocialController>(builder: (controller) {
      if (controller.requestfriendslist.isNotEmpty) {
        return ListView.separated(
            scrollDirection: Axis.vertical,
            shrinkWrap: true,
            controller: scrollcontroller,
            itemBuilder: (context, index) {
              return requestfriendlist(
                friendRequestKey: controller.requestfriendslist[index]
                    ['friendRequestKey'],
                delete: delete,
                nickname: controller.requestfriendslist[index]['nickname'],
                imgUrl: controller.requestfriendslist[index]['imgUrl'],
                userKey: controller.requestfriendslist[index]['userKey'],
              );
            },
            separatorBuilder: (context, index) => const SizedBox(
                  height: 10,
                ),
            itemCount: controller.requestfriendslist.length);
      } else {
        return const SizedBox(
          height: 10,
        );
      }
    });
  }
}

class requestfriendlist extends StatelessWidget {
  final int friendRequestKey;
  final String userKey, nickname, imgUrl;
  final Function(int) delete;

  const requestfriendlist({
    super.key,
    required this.friendRequestKey,
    required this.userKey,
    required this.nickname,
    required this.imgUrl,
    required this.delete,
  });

  requestOk(Map<String, dynamic> body) {
    int requestCode = body['friendRequestCode'];
    postFriendRequest(
        body: body,
        success: (response) {
          debugPrint("전송성공 : $response");
        },
        fail: (e) {
          debugPrint("에러발생 : $e");
        });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          Padding(
            padding: const EdgeInsets.only(right: 20),
            child: imgUrl != ''
                ? Image.network(
                    imgUrl,
                    scale: 5,
                  )
                : const Icon(
                    Icons.local_florist_rounded,
                    size: 50,
                  ),
          ),
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                nickname,
                style: const TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.w600,
                ),
              ),
            ],
          ),
          Padding(
            padding: const EdgeInsets.all(3),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.grey,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(30.0),
                        ),
                      ),
                      onPressed: () {
                        debugPrint('수락보냄');
                        delete(friendRequestKey);
                      },
                      child: const Text('수락')),
                ),
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.red,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(30.0),
                        ),
                      ),
                      onPressed: () {
                        putFriendRequest(
                            body: friendRequestKey,
                            success: (res) {
                              debugPrint('거절됨');
                            },
                            fail: (e) {
                              debugPrint('에러 $e');
                            });
                        delete(friendRequestKey);
                      },
                      child: const Text('거절')),
                ),
              ],
            ),
          )
        ],
      ),
    );
  }
}
