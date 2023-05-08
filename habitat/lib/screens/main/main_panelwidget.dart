import 'package:flutter/material.dart';
import 'package:habitat/api/drinklog/api_drinklogs.dart';
import 'package:habitat/models/drink_log_model.dart';

class MainPanelWidget extends StatelessWidget {
  final ScrollController controller;

  MainPanelWidget({
    Key? key,
    required this.controller,
  }) : super(key: key);

  final Future<List<Drinklogmodel>> drinklogdatas =
      ApiDrinkLogs().getTodayDrinkLogs('asdf');

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: drinklogdatas,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            return Column(
              children: [
                const SizedBox(
                  height: 50,
                  child: Align(
                    alignment: Alignment.bottomCenter,
                    child: Text(
                      "water log",
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
                  child: ListView.separated(
                    padding: const EdgeInsets.symmetric(horizontal: 60),
                    controller: controller,
                    itemCount: snapshot.data!.length,
                    itemBuilder: (context, index) {
                      return WaterLog(
                          drink: snapshot.data![index].drink,
                          drinkType: snapshot.data![index].drinkType,
                          createdAt: snapshot.data![index].createdAt);
                    },
                    separatorBuilder: (context, index) => const SizedBox(
                      height: 10,
                    ),
                  ),
                ),
              ],
            );
          }
          return const Text(
            "non data",
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 25,
              fontWeight: FontWeight.w600,
            ),
          );
        });
  }
}

class WaterLog extends StatelessWidget {
  final int drink;
  final String drinkType, createdAt;

  WaterLog({
    super.key,
    required this.drink,
    required this.drinkType,
    required this.createdAt,
  });

  late final drinktime = DateTime.parse(createdAt);

  IconData iconData(String drinkType) {
    if (drinkType == "c") {
      return Icons.coffee_rounded;
    } else if (drinkType == "d") {
      return Icons.sports_bar_rounded;
    } else {
      return Icons.water_drop_rounded;
    }
  }

  Color iconColor(String drinkType) {
    if (drinkType == "c") {
      return Colors.brown.shade900;
    } else if (drinkType == "d") {
      return Colors.amber.shade800;
    } else {
      return Colors.lightBlue;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 40,
      margin: const EdgeInsets.only(bottom: 10),
      decoration: BoxDecoration(
        border: Border(
          bottom: BorderSide(color: Colors.grey.shade300),
        ),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Icon(
            iconData(drinkType),
            size: 36,
            color: iconColor(drinkType),
          ),
          Text(
            "${drink}ml 섭취!",
            style: const TextStyle(
              fontSize: 14,
              fontWeight: FontWeight.w600,
            ),
          ),
          Text(
            "${drinktime.hour}:${drinktime.minute}",
            style: const TextStyle(
              fontSize: 12,
              fontWeight: FontWeight.w600,
            ),
          ),
        ],
      ),
    );
  }
}
