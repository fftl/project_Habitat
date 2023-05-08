import 'package:flutter/material.dart';
import 'package:habitat/api/drinklog/api_drinklogs.dart';
import 'package:habitat/api/user/api_users.dart';
import 'package:habitat/models/users_model.dart';
import 'package:habitat/screens/main/main_panelwidget.dart';
import 'package:habitat/widgets/dock_bar.dart';
import 'package:habitat/widgets/waterlog_input_modal.dart';
import 'package:sliding_up_panel2/sliding_up_panel2.dart';
import 'package:step_progress_indicator/step_progress_indicator.dart';

class MainScreen extends StatefulWidget {
  const MainScreen({super.key});

  @override
  State<MainScreen> createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  final ScrollController scrollController = ScrollController();
  final PanelController panelController = PanelController();

  // 일일 누적 음수량
  var amountwater = 100;
  //목표 음수량
  var usergoaldata = 100;

  void importdata() async {
    List<Usersmodel> userinfodata = [];
    userinfodata = await ApiUsers().getUserInfo('asdf');
    amountwater = await ApiDrinkLogs().getTodaytotalDrink('asdf');
    usergoaldata = userinfodata[0].goal;
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
                        color: Colors.lightBlue,
                        borderRadius: BorderRadius.all(
                          Radius.circular(12),
                        ),
                      ),
                      child: const Text(
                        "하루에 한잔씩 물을 마십시다!",
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
              Stack(
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
                    totalSteps: usergoaldata,
                    currentStep: amountwater,
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
                    "${amountwater}ml",
                    style: const TextStyle(
                      fontSize: 52,
                      fontWeight: FontWeight.w600,
                      color: Colors.white,
                    ),
                  ),
                  Positioned(
                    bottom: 80,
                    child: Text(
                      "/ ${usergoaldata}ml",
                      style: const TextStyle(
                        fontSize: 22,
                        fontWeight: FontWeight.w600,
                        color: Colors.white70,
                      ),
                    ),
                  )
                ],
              ),
              const SizedBox(
                height: 10,
              ),
              Text(
                "오늘 목표치의 ${(amountwater / usergoaldata * 100).toStringAsFixed(2)}%을 달성했어요! ",
              ),
              WaterLogInputModal(updatedata: importdata),
            ],
          ),
        ),
        panelBuilder: () => MainPanelWidget(controller: scrollController),
      ),
      bottomNavigationBar: const DockBar(),
    );
  }
}
