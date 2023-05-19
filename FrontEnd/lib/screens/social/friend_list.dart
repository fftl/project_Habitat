import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/controller/social_controller.dart';

class friendslistWidget extends StatelessWidget {
  final ScrollController scrollcontroller;

  friendslistWidget({super.key, required this.scrollcontroller});

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
              return friendslist(
                userKey: controller.friendslist[index]['userKey'],
                nickname: controller.friendslist[index]['nickname'],
                imgUrl: controller.friendslist[index]['imgUrl'],
                recent: controller.friendslist[index]['recent'] ?? '',
              );
            },
            separatorBuilder: (context, index) => const SizedBox(
                  height: 10,
                ),
            itemCount: controller.friendslist.length);
      } else {
        return const SizedBox(
          height: 10,
        );
      }
    });
  }
}

class friendslist extends StatelessWidget {
  String userKey, nickname, imgUrl, recent;
  friendslist({
    super.key,
    required this.userKey,
    required this.nickname,
    required this.imgUrl,
    required this.recent,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          Padding(
            padding: const EdgeInsets.all(10),
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
              Text(
                recent,
                style: const TextStyle(
                  fontSize: 12,
                  fontWeight: FontWeight.w600,
                ),
              ),
            ],
          ),
          IconButton(
            onPressed: () {},
            icon: const Icon(Icons.add_alert_outlined),
            iconSize: 30,
          )
        ],
      ),
    );
  }
}
