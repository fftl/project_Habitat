import 'package:flutter/material.dart';
import 'package:habitat/api/flower/api_flowers.dart';
import 'package:habitat/models/flower_model.dart';
import 'package:habitat/screens/reward/reward_panelwidget.dart';
import 'package:habitat/widgets/dock_bar.dart';
import 'package:sliding_up_panel2/sliding_up_panel2.dart';
import 'package:step_progress_indicator/step_progress_indicator.dart';

class RewardScreen extends StatefulWidget {
  const RewardScreen({
    super.key,
  });

  @override
  State<RewardScreen> createState() => _RewardScreenState();
}

class _RewardScreenState extends State<RewardScreen> {
  final ScrollController scrollController = ScrollController();
  final PanelController panelController = PanelController();

  // 데이터 불러오기 상태
  bool isdata = false;
  // 꽃 경험치
  List<ExpModel> flowerExpStatus = [];
  // 꽃 정보
  List<FlowerModel> flowerStatus = [];
  // 경험치 퍼센트
  double expPercent = 0;

  void importdata() async {
    List<FlowerStatusModel> temp = [];
    temp = await ApiFlowers().getGrowingFlower('asdf');
    flowerExpStatus.add(temp[0].exp);
    flowerStatus.add(temp[0].flower);
    expPercent = flowerExpStatus[0].exp / flowerExpStatus[0].maxExp * 100;
    isdata = true;
    setState(() {});
  }

  @override
  void initState() {
    super.initState();
    importdata();
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
              colors: [Color(0xFFA1EF7A), Colors.white],
              stops: [0, 1],
              begin: Alignment.topCenter,
              end: Alignment.bottomCenter,
            ),
          ),
          child: Column(
            // mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const SizedBox(
                height: 72,
              ),
              Padding(
                padding:
                    const EdgeInsets.symmetric(horizontal: 16.0, vertical: 28),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    Container(
                      height: 60,
                      padding: const EdgeInsets.symmetric(
                          vertical: 8, horizontal: 16),
                      transformAlignment: Alignment.center,
                      decoration: const BoxDecoration(
                        color: Color(0xFFD0F4BA),
                        borderRadius: BorderRadius.all(
                          Radius.circular(12),
                        ),
                      ),
                      child: const Text(
                        "목표를 달성해서 꽃을 키워보자!!",
                        style: TextStyle(
                          fontSize: 18,
                        ),
                      ),
                    ),
                    const Icon(
                      Icons.water_drop_rounded,
                      size: 60,
                      color: Colors.lightBlue,
                    )
                  ],
                ),
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Image.asset(
                    isdata
                        ? 'lib/assets/images/flowers/${flowerExpStatus[0].flowerKey}/${flowerExpStatus[0].lv}.png'
                        // ? 'lib/assets/images/flowers/7/2.png'
                        : 'lib/assets/images/sunflower.png',
                    scale: 4.8,
                  ),
                  const SizedBox(
                    width: 10,
                  ),
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      StepProgressIndicator(
                        direction: Axis.vertical,
                        totalSteps: isdata ? flowerExpStatus[0].maxExp : 100,
                        currentStep: isdata ? flowerExpStatus[0].exp : 0,
                        size: 26,
                        padding: 0,
                        fallbackLength: 170,
                        selectedColor: const Color.fromARGB(255, 44, 167, 243),
                        unselectedColor: const Color(0xFFBBDEF9),
                        progressDirection: TextDirection.rtl,
                        roundedEdges: const Radius.circular(14),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(top: 8),
                        child: Text(
                          "${expPercent.toStringAsFixed(1)}%",
                          style: const TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.w600,
                            color: Colors.black87,
                          ),
                        ),
                      )
                    ],
                  ),
                ],
              ),
              // Stack(
              //   alignment: Alignment.center,
              //   children: [
              //     Container(
              //       height: 160,
              //       width: 160,
              //       decoration: BoxDecoration(
              //         shape: BoxShape.circle,
              //         color: Colors.green[600],
              //       ),
              //     ),
              //     const CircularStepProgressIndicator(
              //       totalSteps: 100,
              //       currentStep: 75,
              //       stepSize: 20,
              //       selectedColor: Color(0xFFBAF19C),
              //       unselectedColor: Color(0xFFEAF8DA),
              //       padding: 0,
              //       width: 200,
              //       height: 200,
              //       selectedStepSize: 20,
              //       // roundedCap: (_, __) => false,
              //     ),
              //     Image.asset(
              //       'lib/assets/images/sunflower.png',
              //       scale: 1.5,
              //     ),
              //   ],
              // ),
              Container(
                width: 260,
                height: 120,
                alignment: Alignment.topCenter,
                margin: const EdgeInsets.symmetric(vertical: 30),
                padding: const EdgeInsets.all(14),
                decoration: BoxDecoration(
                    color: Colors.white,
                    boxShadow: [
                      BoxShadow(
                        color: Colors.grey.withOpacity(0.7),
                        blurRadius: 5.0,
                        spreadRadius: 0.0,
                        offset: const Offset(0, 0),
                      )
                    ],
                    borderRadius: BorderRadius.circular(24)),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(
                      isdata ? flowerStatus[0].name : "not yet",
                      style: const TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.w600,
                      ),
                    ),
                    Text(
                      isdata ? flowerStatus[0].story : "not yet",
                      style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.w600,
                          color: Colors.grey[400]),
                    ),
                    // Text(
                    //   "획득레벨 : 어려움",
                    //   style: TextStyle(
                    //       fontSize: 12,
                    //       fontWeight: FontWeight.w600,
                    //       height: 2.5,
                    //       color: Colors.grey[400]),
                    // ),
                    Text(
                      isdata
                          ? "획득조건 : ${flowerStatus[0].getCondition}"
                          : "not yet",
                      style: TextStyle(
                          fontSize: 12,
                          fontWeight: FontWeight.w600,
                          color: Colors.grey[400]),
                    ),
                  ],
                ),
              )
            ],
          ),
        ),
        panelBuilder: () => RewardPanelWidget(
          controller: scrollController,
        ),
      ),
      bottomNavigationBar: const DockBar(),
    );
  }
}
