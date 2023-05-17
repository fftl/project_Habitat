import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/api/friends/api_friendrequest.dart';
import 'package:habitat/controller/social_controller.dart';
import 'package:habitat/models/friend_request_model.dart';

class friendslistWidget extends StatefulWidget {
  final ScrollController controller;

  const friendslistWidget({super.key, required this.controller});

  @override
  State<friendslistWidget> createState() => _friendslistWidgetState();
}

class _friendslistWidgetState extends State<friendslistWidget> {
  final List<FriendRequestModel> _friendList = [];

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _loadFriendRequestList();
  }

  Future<void> _loadFriendRequestList() async {
    // List<FriendRequestModel> friendrequestlist =
    // await ApiFriendRequestList().getRequestFriendList('asdf');

    setState(() {
      // _friendRequestList = friendrequestlist;
    });
  }

  @override
  Widget build(BuildContext context) {
    if (_friendList == null) {
      return const CircularProgressIndicator();
    } else {
      return const Scaffold();
      // return ListView.separated(
      //     scrollDirection: Axis.vertical,
      //     shrinkWrap: true,
      //     controller: widget.controller,
      //     itemBuilder: (context, index) {
      //       return friendslist(
      //         userKey: _friendRequestList[index].userKey,
      //         nickname: _friendRequestList[index].nickname,
      //         imgUrl: _friendRequestList[index].imgUrl,
      //         friendRequestKey: _friendRequestList[index].friendRequestKey,
      //         d
      //       );
      //     },
      //     separatorBuilder: (context, index) => const SizedBox(
      //           height: 10,
      //         ),
      //     itemCount: _friendRequestList.length);
    }
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
