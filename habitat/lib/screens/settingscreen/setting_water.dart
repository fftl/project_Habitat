import 'package:flutter/material.dart';
import 'package:habitat/api/user/api_users.dart';
import 'package:habitat/screens/settingscreen/setting_screen.dart';
import 'package:habitat/widgets/dock_bar.dart';

class SettingWater extends StatefulWidget {
  const SettingWater({super.key});

  @override
  State<SettingWater> createState() => _SettingWaterState();
}

class _SettingWaterState extends State<SettingWater> {
  final weatherList = ['고온다습', '고온건조', '저온다습', '저온건조'];
  var selectedWeatherValue;
  double selectedWeather = 0;

  final activeList = ['활동량이 많음', '활동량 보통', '활동량 적음'];
  var selectedActiveValue;
  double selectedActive = 0;

  final _weightEditController = TextEditingController();
  var _weight = 0;

  double cal() {
    double waterNeeds = _weight * 30 * selectedWeather * selectedActive;
    return waterNeeds;
  }

  onSubmitButton() {
    cal();
    print(_weightEditController.text);
    ApiUsers().patchUserModifyGoal('asdf', cal());

    Navigator.push(context,
        MaterialPageRoute(builder: (context) => const SettingScreen()));
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
    _weightEditController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        leading: const BackButton(color: Color(0xff002B20)),
        title: const Text(
          '목표 음수량 추천받기',
          style: TextStyle(color: Color(0xff002B20)),
        ),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Column(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  Text('체중'),
                  SizedBox(
                    height: 60,
                  ),
                  Text('기후'),
                  SizedBox(
                    height: 60,
                  ),
                  Text('활동량'),
                ],
              ),
              const SizedBox(
                width: 10,
              ),
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Row(
                    children: [
                      SizedBox(
                        width: 90,
                        child: TextField(
                          textAlign: TextAlign.center,
                          controller: _weightEditController,
                          onChanged: (val) {
                            setState(() {
                              _weight = int.parse(val);
                            });
                          },
                        ),
                      ),
                      const SizedBox(height: 15, child: Text('kg')),
                    ],
                  ),
                  const SizedBox(
                    height: 30,
                  ),
                  SizedBox(
                    child: DropdownButton(
                        value: selectedWeatherValue,
                        items: weatherList.map(
                          (value) {
                            return DropdownMenuItem(
                              value: value,
                              child: Text(value),
                            );
                          },
                        ).toList(),
                        onChanged: (value) {
                          setState(() {
                            debugPrint(value.toString());
                            selectedWeatherValue = value;
                            if (selectedWeatherValue == '고온다습') {
                              selectedWeather = 1.3;
                            } else if (selectedWeatherValue == '고온저습') {
                              selectedWeather = 1.1;
                            } else {
                              selectedWeather = 1;
                            }
                          });
                        }),
                  ),
                  const SizedBox(
                    height: 30,
                  ),
                  SizedBox(
                      child: DropdownButton(
                          value: selectedActiveValue,
                          items: activeList.map(
                            (value) {
                              return DropdownMenuItem(
                                value: value,
                                child: Text(value),
                              );
                            },
                          ).toList(),
                          onChanged: (value) {
                            setState(() {
                              selectedActiveValue = value;

                              if (selectedActiveValue == '활동량이 많음') {
                                selectedActive = 1.3;
                              } else {
                                selectedActive = 1;
                              }
                            });
                          }))
                ],
              ),
            ],
          ),
          const SizedBox(
            height: 50,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ElevatedButton(
                  style: ElevatedButton.styleFrom(
                      backgroundColor: const Color(0xff47799B)),
                  onPressed: onSubmitButton,
                  child: const Text('목표 음수량 추천받기'))
            ],
          ),
        ],
      ),
      bottomNavigationBar: const DockBar(),
    );
  }
}
