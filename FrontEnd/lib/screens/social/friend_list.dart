import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/controller/social_controller.dart';

class FriendslistWidget extends StatelessWidget {
  final ScrollController scrollcontroller;

  FriendslistWidget({super.key, required this.scrollcontroller});

  // Future<void> _loadFriendRequestList() async {
  final controller = Get.put(SocialController());

  @override
  Widget build(BuildContext context) {
    return GetX<SocialController>(builder: (controller) {
      if (controller.friendslist.isNotEmpty) {
        return ListView.separated(
          scrollDirection: Axis.vertical,
          shrinkWrap: true,
          controller: scrollcontroller,
          itemBuilder: (context, index) {
            return Friendslist(
              userKey: controller.friendslist[index]['userKey'],
              nickname: controller.friendslist[index]['nickname'],
              imgUrl: controller.friendslist[index]['imgUrl'],
              recent: controller.friendslist[index]['recent'] ?? '',
            );
          },
          separatorBuilder: (context, index) => const SizedBox(
            height: 10,
          ),
          itemCount: controller.friendslist.length,
        );
      } else {
        return const SizedBox(
          height: 10,
        );
      }
    });
  }
}

class Friendslist extends StatelessWidget {
  final String userKey, nickname, imgUrl, recent;
  const Friendslist({
    super.key,
    required this.userKey,
    required this.nickname,
    required this.imgUrl,
    required this.recent,
  });

  @override
  Widget build(BuildContext context) {
    late var recentTime = DateTime.parse(recent);
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
          width: 260,
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
              Text(
                recent != ''
                    ? "${recentTime.year}년 ${recentTime.month}월 ${recentTime.day}일 ${recentTime.hour}시${recentTime.minute}분에 마셨어요"
                    : '물을 마시지 않았어요',
                style: const TextStyle(
                  fontSize: 12,
                  fontWeight: FontWeight.w600,
                ),
              ),
            ],
          ),
        ),
        IconButton(
          onPressed: () {},
          icon: const Icon(Icons.add_alert_outlined),
          iconSize: 30,
        )
      ],
    );
  }
}
