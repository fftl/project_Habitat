import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:habitat/api/friends/api_friendcode.dart';
import 'package:habitat/api/friends/api_sendrequestcode.dart';
import 'package:habitat/screens/social/friend_list.dart';
import 'package:habitat/widgets/dock_bar.dart';

class SocialScreen extends StatefulWidget {
  const SocialScreen({super.key});

  @override
  State<SocialScreen> createState() => _SocialScreenState();
}

class _SocialScreenState extends State<SocialScreen> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  final ScrollController scrollController = ScrollController();
  late var userCode = ApiFriendcode().getCode('asdf');

  TextEditingController tec = TextEditingController();

  onSubmitButton() {}

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
                        FutureBuilder(
                            future: userCode,
                            builder: (context, snapshot) {
                              if (snapshot.hasData) {
                                return Text(
                                  snapshot.data.toString(),
                                  style: const TextStyle(
                                      fontSize: 20,
                                      fontWeight: FontWeight.w600),
                                );
                              } else {
                                return const Text('없음');
                              }
                            }),
                        FutureBuilder(
                            future: userCode,
                            builder: (context, snapshot) {
                              if (snapshot.hasData) {
                                return IconButton(
                                  onPressed: () {
                                    Clipboard.setData(ClipboardData(
                                        text: snapshot.data.toString()));
                                    if (!mounted) return;
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
                            }),
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
                        controller: tec,
                        decoration: const InputDecoration(
                            hintText: '친구 코드를 입력하세요.', hintStyle: TextStyle()),
                      ),
                    ),
                    ElevatedButton(
                        onPressed: onSubmitButton, child: const Text('확인')),
                    IconButton(
                      onPressed: () {
                        ApiSendRequestCode().postRequestCode(tec.text, 'asdf');
                      },
                      icon: Image.asset(
                        'lib/assets/images/kakao.png',
                        fit: BoxFit.cover,
                      ),
                      iconSize: 30,
                    )
                  ],
                ),
              ),
              const Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  Text(
                    '친구신청 목록',
                    style: TextStyle(fontSize: 20),
                  ),
                ],
              ),
              friendsRequestlistWidget(
                controller: scrollController,
              ),
              const Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  Text(
                    '내 친구 목록',
                    style: TextStyle(fontSize: 20),
                  ),
                ],
              ),
              friendslistWidget(
                controller: scrollController,
              )
            ],
          ),
        ),
      ),
      bottomNavigationBar: const DockBar(),
    );
  }
}
