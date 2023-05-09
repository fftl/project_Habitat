import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:habitat/api/drinklog/api_drinklogs.dart';
import 'package:habitat/controller/water_controller.dart';

class WaterLogInputModal extends StatefulWidget {
  const WaterLogInputModal({super.key});

  @override
  State<WaterLogInputModal> createState() => _WaterLogInputModalState();
}

class _WaterLogInputModalState extends State<WaterLogInputModal> {
  // 음료 타입
  final drinkTypeList = ['water', 'coffee', 'non-coffee'];
  // 선택한 음료 타입
  var selectedDrinkTypeValue = 'water';
  // 선택한 음료 타입 api 전송코드
  var drinkType = 'w';
  // 마신 음수량
  var drink = 0;

  @override
  Widget build(BuildContext context) {
    return TextButton(
      style: const ButtonStyle(
        backgroundColor: MaterialStatePropertyAll(Colors.blue),
      ),
      onPressed: () {
        _showdialog(context);
      },
      child: const Text(
        "물 마시기",
        style: TextStyle(color: Colors.white),
      ),
    );
  }

  Future<dynamic> _showdialog(BuildContext context) {
    return showDialog<String>(
      context: context,
      builder: (BuildContext context) => StatefulBuilder(
        builder: (context, setter) {
          return Dialog(
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Row(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      DropdownButton(
                          value: selectedDrinkTypeValue,
                          items: drinkTypeList.map(
                            (value) {
                              return DropdownMenuItem(
                                value: value,
                                child: Text(value),
                              );
                            },
                          ).toList(),
                          onChanged: (value) {
                            setter(() {
                              selectedDrinkTypeValue = value!;
                              if (selectedDrinkTypeValue == 'coffee') {
                                drinkType = 'c';
                              } else if (selectedDrinkTypeValue ==
                                  'non-coffee') {
                                drinkType = 'd';
                              } else {
                                drinkType = 'w';
                              }
                            });
                          }),
                      const Text("을(를) "),
                      SizedBox(
                          width: 40,
                          child: TextField(
                            onChanged: (value) {
                              setState(() {
                                drink = int.parse(value);
                              });
                            },
                          )),
                      const Text('만큼 마셨습니다!'),
                    ],
                  ),
                  const SizedBox(height: 5),
                  TextButton(
                    onPressed: () {
                      ApiDrinkLogs().postAddDrinkLog(drink, drinkType, 'asdf');
                      Get.find<WaterController>().drinkwater(drink);
                      Navigator.pop(context);
                    },
                    child: const Text('전송'),
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }
}
