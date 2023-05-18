import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/controller/coaster_controller.dart';
import 'package:habitat/controller/reward_controller.dart';
import 'package:habitat/controller/water_controller.dart';
import 'package:habitat/screens/main/main_panelwidget.dart';
import 'package:habitat/widgets/dock_bar.dart';
import 'package:habitat/widgets/mw_line.dart';
import 'package:habitat/widgets/waterlog_input_modal.dart';
import 'package:sliding_up_panel2/sliding_up_panel2.dart';
import 'package:step_progress_indicator/step_progress_indicator.dart';

class MainScreen extends StatelessWidget {
  MainScreen({super.key});

  final ScrollController scrollController = ScrollController();

  final PanelController panelController = PanelController();

  final waterController = Get.put(WaterController());

  final coasterController = Get.put(CoasterController());

  final rewardcontroller = Get.put(RewardController());

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
              const SizedBox(
                height: 72,
              ),
              const MwLine(),
              Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  GetX<CoasterController>(
                    builder: (controller) {
                      WaterController waterController =
                          Get.find<WaterController>();
                      RewardController rewardController =
                          Get.find<RewardController>();
                      waterController.drinkWater({
                        "drink": controller.water.value,
                        "drinkType": controller.type,
                      });
                      return Container(
                        decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(180),
                            color: controller.connectDeviceState.value
                                ? Colors.blue
                                : Colors.blueGrey.shade200),
                        margin: const EdgeInsets.only(right: 42, top: 12),
                        child: IconButton(
                          onPressed: () {
                            if (controller.connectDeviceState.value) {
                              controller.disconnectDevice();
                            } else {
                              controller.scanDevice();
                            }
                          },
                          icon: Icon(Icons.bluetooth_outlined,
                              size: 28,
                              color: controller.connectDeviceState.value
                                  ? Colors.black
                                  : Colors.grey.shade800),
                        ),
                      );
                    },
                  ),
                ],
              ),
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
                      Positioned(
                        bottom: 60,
                        child: Column(
                          children: [
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
                            ),
                            const WaterLogInputModal(),
                          ],
                        ),
                      )
                    ],
                  );
                },
              ),
              const SizedBox(
                height: 50,
              ),
              GetX<WaterController>(
                builder: (controller) {
                  return Text(
                    "오늘 목표치의 ${(controller.water.value / controller.goal.value * 100).toStringAsFixed(2)}%을 달성했어요! ",
                  );
                },
              ),

              // ElevatedButton(
              //   onPressed: () {
              //     Navigator.pushNamed(context, '/bluetooth');
              //   },
              //   child: const Text("코스터연결"),
              // ),
            ],
          ),
        ),
        panelBuilder: () => MainPanelWidget(scrollController: scrollController),
      ),
      bottomNavigationBar: DockBar(),
    );
  }
}
