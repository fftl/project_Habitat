import 'package:flutter/material.dart';
import 'package:habitat/api/friends/api_friendrequest.dart';
import 'package:habitat/models/friend_request_model.dart';

class friendsRequestlistWidget extends StatefulWidget {
  final ScrollController controller;
  final Function update;

  const friendsRequestlistWidget(
      {super.key, required this.controller, required this.update});

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
              update: widget.update,
            );
          },
          separatorBuilder: (context, index) => const SizedBox(
                height: 10,
              ),
          itemCount: _friendRequestList.length);
    }
  }
}

class requestfriendlist extends StatefulWidget {
  final int friendRequestKey;
  final String userKey, nickname, imgUrl;
  final Function(int) delete;
  final Function update;

  const requestfriendlist({
    super.key,
    required this.friendRequestKey,
    required this.userKey,
    required this.nickname,
    required this.imgUrl,
    required this.delete,
    required this.update,
    // required this.loginUserKey,
  });

  @override
  State<requestfriendlist> createState() => _requestfriendlistState();
}

class _requestfriendlistState extends State<requestfriendlist> {
  final String loginUserKey = 'asdf';

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          Padding(
            padding: const EdgeInsets.only(right: 20),
            child: widget.imgUrl != ''
                ? Image.network(
                    widget.imgUrl,
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
                widget.nickname,
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
                        ApiFriendRequest().postFriendRequest(
                            widget.friendRequestKey, loginUserKey);
                        debugPrint('수락보냄');
                        widget.delete(widget.friendRequestKey);
                        widget.update();
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
                        ApiFriendRequest().deleteFriendRequest(
                            widget.friendRequestKey, loginUserKey);
                        debugPrint('거절보냄');
                        widget.delete(widget.friendRequestKey);
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
