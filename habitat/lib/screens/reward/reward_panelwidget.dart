import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/controller/reward_controller.dart';
import 'package:habitat/widgets/plant_collection_modal.dart';

class RewardPanelWidget extends StatelessWidget {
  final ScrollController scrollController;

  RewardPanelWidget({
    Key? key,
    required this.scrollController,
  }) : super(key: key);

  final rewardController = Get.put(RewardController());

  @override
  Widget build(BuildContext context) {
    return GetX<RewardController>(
      builder: (controller) {
        if (controller.collection.isNotEmpty) {
          return Column(
            children: [
              const SizedBox(
                height: 50,
                child: Align(
                  alignment: Alignment.bottomCenter,
                  child: Text(
                    "collection",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: 25,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                ),
              ),
              SizedBox(
                height: 350,
                child: GridView.count(
                  controller: scrollController,
                  scrollDirection: Axis.vertical,
                  padding: const EdgeInsets.symmetric(horizontal: 40),
                  mainAxisSpacing: 20,
                  crossAxisSpacing: 20,
                  crossAxisCount: 3,
                  children: List.generate(
                      controller.collection.length,
                      (index) => PlantCollectionModal(
                            flowerKey: controller.collection[index].flowerKey,
                            name: controller.collection[index].name,
                            story: controller.collection[index].story,
                            getCondition:
                                controller.collection[index].getCondition,
                            userStatus: controller.collection[index].userStatus,
                          )),
                ),
              ),
            ],
          );
        }
        return const Text("not yet");
      },
    );
  }
}
