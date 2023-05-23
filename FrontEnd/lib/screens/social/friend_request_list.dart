import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/api/friends/api_friendrequest.dart';
import 'package:habitat/controller/social_controller.dart';

class FriendsRequestListWidget extends StatelessWidget {
  final ScrollController scrollcontroller;

  FriendsRequestListWidget({
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
              return Requestfriendlist(
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

class Requestfriendlist extends StatelessWidget {
  final int friendRequestKey;
  final String userKey, nickname, imgUrl;
  final Function(int) delete;

  const Requestfriendlist({
    super.key,
    required this.friendRequestKey,
    required this.userKey,
    required this.nickname,
    required this.imgUrl,
    required this.delete,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.all(8),
          child: imgUrl != ''
              ? Container(
                  decoration:
                      BoxDecoration(borderRadius: BorderRadius.circular(16)),
                  width: 60,
                  height: 60,
                  clipBehavior: Clip.hardEdge,
                  child: Image.network(
                    imgUrl,
                    fit: BoxFit.fill,
                  ),
                )
              : const Icon(
                  Icons.local_florist_rounded,
                  size: 50,
                ),
        ),
        Container(
          padding: const EdgeInsets.symmetric(horizontal: 8),
          width: 170,
          child: Column(
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
        ),
        Row(
          crossAxisAlignment: CrossAxisAlignment.end,
          children: [
            Padding(
              padding: const EdgeInsets.only(right: 8),
              child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    padding: const EdgeInsets.all(0),
                    backgroundColor: Colors.grey,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30.0),
                    ),
                  ),
                  onPressed: () async {
                    debugPrint(friendRequestKey.toString());

                    okFriendRequest(
                        body: {'friendRequestKey': friendRequestKey},
                        success: (response) {
                          debugPrint("전송성공 : $response");
                        },
                        fail: (e) {
                          debugPrint("에러발생 : $e");
                        });
                    debugPrint('수락보냄');
                    await Get.find<SocialController>().getSocialList();
                    delete(friendRequestKey);
                  },
                  child: const Text('수락')),
            ),
            ElevatedButton(
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(30.0),
                  ),
                ),
                onPressed: () {
                  cancelFriendRequest(
                      body: {'friendRequestKey': friendRequestKey},
                      success: (res) {
                        debugPrint('거절됨');
                      },
                      fail: (e) {
                        debugPrint('에러 $e');
                      });
                  delete(friendRequestKey);
                },
                child: const Text('거절')),
          ],
        )
      ],
    );
  }
}
