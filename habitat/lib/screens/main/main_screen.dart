import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get/get.dart';
import 'package:habitat/controller/coaster_controller.dart';
import 'package:habitat/controller/social_controller.dart';
import 'package:habitat/controller/water_controller.dart';
import 'package:habitat/screens/main/main_panelwidget.dart';
import 'package:habitat/widgets/dock_bar.dart';
import 'package:habitat/widgets/waterlog_input_modal.dart';
import 'package:sliding_up_panel2/sliding_up_panel2.dart';
import 'package:step_progress_indicator/step_progress_indicator.dart';
import 'package:web_socket_channel/io.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

const storage = FlutterSecureStorage();

class MainScreen extends StatefulWidget {
  const MainScreen({super.key});

  @override
  State<MainScreen> createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  final ScrollController scrollController = ScrollController();

  final PanelController panelController = PanelController();

  final waterController = Get.put(WaterController());

  final coasterController = Get.put(CoasterController());

  final socialController = Get.put(SocialController());

  WebSocketChannel? channel;

  @override
  void initState() {
    super.initState();
    getUserKey();
    socialController.getSocketMessage();
  }

  getUserKey() async {
    Future<String?> getuserKey = storage.read(key: 'userKey');
    String? userKey = await getuserKey;
    Future<String?> accessToken = storage.read(key: "accessToken");
    String? authorization = await accessToken;

    channel = IOWebSocketChannel.connect(
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer $authorization",
      },
      'ws://k8a704.p.ssafy.io:8081/api/websocket/$userKey',
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SlidingUpPanel(
        margin: const EdgeInsets.symmetric(horizontal: 10),
        minHeight: 50,
        maxHeight: 400,
        borderRadius: const BorderRadius.vertical(top: Radius.circular(36)),
        controller: panelController,
        scrollController: scrollController,
        onPanelOpened: () {
          waterController.waterLogUpdate();
        },
        collapsed: const Align(
          alignment: Alignment.topCenter,
          child: Icon(
            Icons.keyboard_double_arrow_up_rounded,
            size: 25,
          ),
        ),
        body: Container(
          decoration: const BoxDecoration(
            gradient: LinearGradient(
              colors: [Color(0xFF78C6F7), Colors.white],
              stops: [0, 1],
              begin: Alignment.topCenter,
              end: Alignment.bottomCenter,
            ),
          ),
          child: Column(
            // mainAxisAlignment: MainAxisAlignment.center,
            children: [
              StreamBuilder(
                stream: channel?.stream,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    final data = snapshot.data as String;
                    debugPrint(data);
                    return Text(data);
                  } else {
                    return const Text('데이터 없음');
                  }
                },
              ),
              const SizedBox(
                height: 72,
              ),
              const mwLine(),
              GetX<WaterController>(
                builder: (controller) {
                  return Stack(
                    alignment: Alignment.center,
                    children: [
                      Container(
                        height: 240,
                        width: 240,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          color: Colors.blue[600],
                        ),
                      ),
                      CircularStepProgressIndicator(
                        totalSteps: controller.goal.value,
                        currentStep: controller.water.value,
                        stepSize: 30,
                        selectedColor: const Color(0xFF9CD2F7),
                        unselectedColor: const Color(0xFFDCE9FC),
                        padding: 0,
                        width: 280,
                        height: 280,
                        selectedStepSize: 30,
                        // roundedCap: (_, __) => false,
                      ),
                      Text(
                        "${controller.water}ml",
                        style: const TextStyle(
                          fontSize: 52,
                          fontWeight: FontWeight.w600,
                          color: Colors.white,
                        ),
                      ),
                      Positioned(
                        bottom: 80,
                        child: Text(
                          "/ ${controller.goal}ml",
                          style: const TextStyle(
                            fontSize: 22,
                            fontWeight: FontWeight.w600,
                            color: Colors.white70,
                          ),
                        ),
                      )
                    ],
                  );
                },
              ),
              const SizedBox(
                height: 10,
              ),
              GetX<WaterController>(
                builder: (controller) {
                  return Text(
                    "오늘 목표치의 ${(controller.water.value / controller.goal.value * 100).toStringAsFixed(2)}%을 달성했어요! ",
                  );
                },
              ),
              const WaterLogInputModal(),
              ElevatedButton(
                  onPressed: () {
                    Navigator.pushNamed(context, '/bluetooth');
                  },
                  child: const Text("코스터연결")),
            ],
          ),
        ),
        panelBuilder: () => MainPanelWidget(scrollController: scrollController),
      ),
      bottomNavigationBar: const DockBar(),
    );
  }
}

class mwLine extends StatefulWidget {
  const mwLine({
    super.key,
  });

  @override
  State<mwLine> createState() => _mwLineState();
}

class _mwLineState extends State<mwLine> {
  final linelist = [
    "간지러워요.",
    "제로 콜라엔 설탕은 없지만, 카페인이 들어있어요.",
    "탄산수는 물 대신 마셔도 되지만, 사람에 따라서는 위에 안 좋을 수 있어요.",
    "녹차, 홍차, 우롱차에는 카페인이 들어있어요.",
    "물을 너무 한꺼번에 마시는 것은 좋지 않아요.",
    "카페인이 들어간 음료를 마시면, 이뇨작용으로 인해 오히려 갈증이 날 수 있어요.",
    "디카페인 커피에도 카페인이 없는 것은 아니에요.",
    "곡물차를 물 대신 마시는 것도 권장되지는 않아요.",
    "이온음료는 물 대신 마셔도 되지만, 설탕 과잉섭취에 주의하세요!",
    "설탕이 든 음료를 마시는 것은 권장되지 않지만, 물을 아예 마시지 않는 것보다는 나아요.",
    "땀을 많이 흘린 날엔 평소보다 자주, 조금씩 물을 마셔주세요.",
    "저 오늘 예쁘죠?"
  ];

  late String line = '';

  onChangeLine() {
    line = linelist[Random().nextInt(12)];
    setState(() {});
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    onChangeLine();
  }

  @override
  Widget build(BuildContext context) {
    debugPrint(linelist[Random().nextInt(12)]);
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 28),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Container(
            width: 250,
            height: 80,
            padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
            transformAlignment: Alignment.center,
            alignment: Alignment.center,
            decoration: const BoxDecoration(
              color: Color.fromRGBO(250, 250, 250, 0.3),
              borderRadius: BorderRadius.all(
                Radius.circular(50),
              ),
            ),
            child: Text(
              line,
              style: const TextStyle(
                fontSize: 17,
              ),
            ),
          ),
          IconButton(
              onPressed: onChangeLine,
              icon: const Icon(
                Icons.water_drop_rounded,
                size: 60,
                color: Colors.lightBlue,
              ))
        ],
      ),
    );
  }
}
