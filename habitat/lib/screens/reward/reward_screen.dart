import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/controller/reward_controller.dart';
import 'package:habitat/screens/reward/reward_panelwidget.dart';
import 'package:habitat/widgets/dock_bar.dart';
import 'package:habitat/widgets/mw_line.dart';
import 'package:sliding_up_panel2/sliding_up_panel2.dart';
import 'package:step_progress_indicator/step_progress_indicator.dart';

class RewardScreen extends StatelessWidget {
  RewardScreen({super.key});

  final ScrollController scrollController = ScrollController();

  final PanelController panelController = PanelController();

  final rewardController = Get.put(RewardController());

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
              const MwLine(),
              GetX<RewardController>(
                builder: (controller) {
                  return Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Image.asset(
                        controller.flower['flowerKey'] != null &&
                                controller.exp['lv'] != null
                            ? 'lib/assets/images/flowers/${controller.flower['flowerKey'].toString()}/${controller.exp['lv'].toString()}.png'
                            : "lib/assets/images/sunflower.png",
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
                            totalSteps: controller.exp['maxExp'] ?? 100,
                            currentStep: controller.exp['exp'] ?? 25,
                            size: 26,
                            padding: 0,
                            fallbackLength: 170,
                            selectedColor:
                                const Color.fromARGB(255, 44, 167, 243),
                            unselectedColor: const Color(0xFFBBDEF9),
                            progressDirection: TextDirection.rtl,
                            roundedEdges: const Radius.circular(14),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(top: 8),
                            child: Text(
                              controller.exp['exp'] != null &&
                                      controller.exp['maxExp'] != null
                                  ? "${(controller.exp['exp'] / controller.exp['maxExp'] * 100).toStringAsFixed(1)}%"
                                  : "not yet",
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
                  );
                },
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
                child: GetX<RewardController>(
                  builder: (controller) {
                    return Column(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(
                          controller.flower['name'] ?? "not yet",
                          style: const TextStyle(
                            fontSize: 20,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        Text(
                          controller.flower['story'] ?? "not yet",
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
                          controller.flower['getCondition'] ?? "not yet",
                          style: TextStyle(
                              fontSize: 12,
                              fontWeight: FontWeight.w600,
                              color: Colors.grey[400]),
                        ),
                      ],
                    );
                  },
                ),
              )
            ],
          ),
        ),
        panelBuilder: () => RewardPanelWidget(
          scrollController: scrollController,
        ),
      ),
      bottomNavigationBar: DockBar(),
    );
  }
}
