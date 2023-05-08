import 'package:flutter/material.dart';
import 'package:habitat/api/flower/api_flowers.dart';
import 'package:habitat/models/flower_model.dart';
import 'package:habitat/widgets/plant_collection_modal.dart';

class RewardPanelWidget extends StatelessWidget {
  final ScrollController controller;

  RewardPanelWidget({
    Key? key,
    required this.controller,
  }) : super(key: key);

  final Future<List<FlowerCollectionModel>> flowerCollectionData =
      ApiFlowers().getFlowerCollection('asdf');

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: flowerCollectionData,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
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
                  controller: controller,
                  scrollDirection: Axis.vertical,
                  padding: const EdgeInsets.symmetric(horizontal: 40),
                  mainAxisSpacing: 20,
                  crossAxisSpacing: 20,
                  crossAxisCount: 3,
                  children: List.generate(
                      snapshot.data!.length,
                      (index) => PlantCollectionModal(
                            flowerKey: snapshot.data![index].flowerKey,
                            name: snapshot.data![index].name,
                            story: snapshot.data![index].story,
                            getCondition: snapshot.data![index].getCondition,
                            userStatus: snapshot.data![index].userStatus,
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
