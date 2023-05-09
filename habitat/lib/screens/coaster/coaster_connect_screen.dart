import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/controller/coaster_controller.dart';

class CoasterConnectScreen extends StatelessWidget {
  CoasterConnectScreen({super.key});

  final CoasterController coasterController = Get.put(CoasterController());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Center(
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.white,
              ),
              onPressed: () {
                Get.find<CoasterController>().scanDevice();
              },
              child: const Text(
                "coaster connect",
                style: TextStyle(
                  color: Colors.black,
                ),
              ),
            ),
          ),
          GetX<CoasterController>(
            builder: (controller) {
              return Text(controller.coasterStatus.value);
            },
          ),
          GetX<CoasterController>(
            builder: (controller) {
              return Text(controller.coasterData.value);
            },
          )
        ],
      ),
    );
  }
}
