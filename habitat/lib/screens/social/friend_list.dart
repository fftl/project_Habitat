import 'package:flutter/material.dart';
import 'package:habitat/models/friends_list_model.dart';

class friendslistWidget extends StatefulWidget {
  final ScrollController controller;
  Future<List<FriendsListModel>> friendslistdata;
  friendslistWidget({
    super.key,
    required this.controller,
    required this.friendslistdata,
  });

  @override
  State<friendslistWidget> createState() => _friendslistWidgetState();
}

class _friendslistWidgetState extends State<friendslistWidget> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: widget.friendslistdata,
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

class friendslist extends StatefulWidget {
  final String userKey, nickname, imgUrl, recent;

  const friendslist({
    super.key,
    required this.userKey,
    required this.nickname,
    required this.imgUrl,
    required this.recent,
  });

  @override
  State<friendslist> createState() => _friendslistState();
}

class _friendslistState extends State<friendslist> {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          Padding(
            padding: const EdgeInsets.all(10),
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
              Text(
                widget.recent,
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
