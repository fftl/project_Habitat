import 'package:flutter/material.dart';
import 'package:habitat/api/friends/api_friendrequest.dart';
import 'package:habitat/api/friends/api_friendsList.dart';
import 'package:habitat/models/friend_request_model.dart';
import 'package:habitat/models/friends_list_model.dart';

class friendslistWidget extends StatefulWidget {
  final ScrollController controller;
  const friendslistWidget({
    super.key,
    required this.controller,
  });

  @override
  State<friendslistWidget> createState() => _friendslistWidgetState();
}

class _friendslistWidgetState extends State<friendslistWidget> {
  final Future<List<FriendsListModel>> friendslistdata =
      ApiFriendsList().getFriendsList('asdf');

  void test() {
    debugPrint(friendslistdata.toString());
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: friendslistdata,
        builder: (context, snapshot) {
          if (snapshot.hasData && snapshot.data != null) {
            return ListView.separated(
                scrollDirection: Axis.vertical,
                shrinkWrap: true,
                controller: widget.controller,
                itemBuilder: (context, index) {
                  return friendslist(
                      userKey: snapshot.data![index].userKey,
                      nickname: snapshot.data![index].nickname,
                      imgUrl: snapshot.data![index].imgUrl,
                      recent: snapshot.data![index].recent);
                },
                separatorBuilder: (context, index) => const SizedBox(
                      height: 10,
                    ),
                itemCount: snapshot.data!.length);
          } else {
            return const SizedBox(
              height: 10,
            );
          }
        });
  }
}

class friendsRequestlistWidget extends StatefulWidget {
  final ScrollController controller;

  const friendsRequestlistWidget({super.key, required this.controller});

  @override
  State<friendsRequestlistWidget> createState() =>
      _friendsRequestlistWidgetState();
}

class _friendsRequestlistWidgetState extends State<friendsRequestlistWidget> {
  List<FriendRequestModel> _friendRequestList = [];

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _loadFriendRequestList();
  }

  Future<void> _loadFriendRequestList() async {
    List<FriendRequestModel> friendrequestlist =
        await ApiFriendRequestList().getRequestFriendList('asdf');

    setState(() {
      _friendRequestList = friendrequestlist;
    });
  }

  delete(int friendRequestKey) {
    setState(() {
      _friendRequestList.removeWhere(
          (request) => request.friendRequestKey == friendRequestKey);
    });
  }

  @override
  Widget build(BuildContext context) {
    if (_friendRequestList == null) {
      return const CircularProgressIndicator();
    } else {
      return ListView.separated(
          scrollDirection: Axis.vertical,
          shrinkWrap: true,
          controller: widget.controller,
          itemBuilder: (context, index) {
            return requestfriendlist(
              userKey: _friendRequestList[index].userKey,
              nickname: _friendRequestList[index].nickname,
              imgUrl: _friendRequestList[index].imgUrl,
              friendRequestKey: _friendRequestList[index].friendRequestKey,
              delete: delete,
            );
          },
          separatorBuilder: (context, index) => const SizedBox(
                height: 10,
              ),
          itemCount: _friendRequestList.length);
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
                        ApiFriendRequest()
                            .postFriendRequest(friendRequestKey, userKey);
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
                        ApiFriendRequest()
                            .putFriendRequest(friendRequestKey, userKey);
                        debugPrint('거절보냄');
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
