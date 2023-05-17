import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/controller/social_controller.dart';

class friendslistWidget extends StatefulWidget {
  final ScrollController scrollcontroller;

  const friendslistWidget({super.key, required this.scrollcontroller});

  @override
  State<friendslistWidget> createState() => _friendslistWidgetState();
}

class _friendslistWidgetState extends State<friendslistWidget> {
  // final List<FriendRequestModel> _friendList = [];

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    // _loadFriendRequestList();
  }

  // Future<void> _loadFriendRequestList() async {
  // List<FriendRequestModel> friendrequestlist =
  // await ApiFriendRequestList().getRequestFriendList('asdf');

  // setState(() {
  // _friendRequestList = friendrequestlist;
  // });
  // }

  final controller = Get.put(SocialController());
  @override
  Widget build(BuildContext context) {
    return GetX<SocialController>(builder: (controller) {
      if (controller.friendslist.isNotEmpty) {
        return ListView.separated(
            scrollDirection: Axis.vertical,
            shrinkWrap: true,
            controller: widget.scrollcontroller,
            itemBuilder: (context, index) {
              return friendslist(
                userKey: controller.friendslist[index]['userKey'],
                nickname: controller.friendslist[index]['nickname'],
                imgUrl: controller.friendslist[index]['imgUrl'],
                recent: controller.friendslist[index]['recent'],
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
  final String userKey, nickname, imgUrl, recent;
  const friendslist({
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
