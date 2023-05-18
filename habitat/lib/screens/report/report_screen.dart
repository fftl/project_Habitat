import 'package:flutter/material.dart';
import 'package:syncfusion_flutter_charts/charts.dart';
import 'package:habitat/widgets/dock_bar.dart';
import 'package:habitat/screens/settingscreen/modify_goal_screen.dart';
import 'package:habitat/controller/report_controller.dart';
import 'package:get/get.dart';
import 'package:intl/intl.dart';

class ReportScreen extends StatefulWidget {
  const ReportScreen({super.key});

  @override
  State<ReportScreen> createState() => _ReportScreenState();
}

class _ReportScreenState extends State<ReportScreen> {
  final reportController = Get.put(ReportController());

  @override
  void initState() {
    super.initState();
    reportController.onInit();
    reportController.refresh();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        title: const Text(
          "Report 리포트",
          style: TextStyle(
              color: Color(0xff002B20),
              fontSize: 28,
              fontWeight: FontWeight.w600),
        ),
        automaticallyImplyLeading: false,
        elevation: 0,
      ),
      body: const SafeArea(
        child: MyReport(),
      ),
      bottomNavigationBar: const DockBar(),
    );
  }
}

class IntakeData {
  IntakeData(
      this.date, this.waterIntake, this.caffeineIntake, this.noncaffeineIntake);
  String date;
  num waterIntake;
  num caffeineIntake;
  num noncaffeineIntake;
}

class MyReport extends StatefulWidget {
  const MyReport({super.key});

  @override
  State<MyReport> createState() => _MyReportState();
}

class _MyReportState extends State<MyReport> {
  late TooltipBehavior _tooltipBehavior;
  late TrackballBehavior _trackballBehavior;

  @override
  void initState() {
    _tooltipBehavior = TooltipBehavior(enable: true);
    _trackballBehavior = TrackballBehavior(
      enable: true,
      builder: (BuildContext context, TrackballDetails trackballDetails) {
        return Container(
          height: 50,
          width: 150,
          decoration: const BoxDecoration(
            color: Color.fromRGBO(0, 8, 22, 0.75),
            borderRadius: BorderRadius.all(Radius.circular(6.0)),
          ),
        );
      },
    );
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    num screenHeight = MediaQuery.of(context).size.height;
    num screenWidth = MediaQuery.of(context).size.width;

    return Container(
      decoration: const BoxDecoration(
        gradient: LinearGradient(
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
          colors: [
            Colors.lightBlue,
            Colors.white,
          ],
        ),
      ),
      child: ListView(
        children: [
          Align(
            alignment: Alignment.center,
            child: Container(
              margin: const EdgeInsets.symmetric(vertical: 20.0),
              height: screenHeight * 0.55,
              width: screenWidth * 0.90,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(20),
                color: Colors.white,
                boxShadow: [
                  BoxShadow(
                    color: Colors.grey.withOpacity(0.5),
                    spreadRadius: 5,
                    blurRadius: 7,
                    offset: const Offset(0, 3),
                  ),
                ],
              ),
              child: SafeArea(
                //차트 시작

                //으악

                child: GetX<ReportController>(
                  builder: (controller) {
                    List<IntakeData> chartData = [];

                    for (int i = 0; i < controller.weekly.length; i++) {
                      String date = controller.weekly[i]['date'];
                      num waterIntake = controller.weekly[i]['waterDrink'];
                      num caffeineIntake = controller.weekly[i]['cafeDrink'];
                      num noncaffeineIntake =
                          controller.weekly[i]['nonCafeDrink'];
                      chartData.add(IntakeData(date, waterIntake,
                          caffeineIntake, noncaffeineIntake));
                    }

                    return SfCartesianChart(
                      trackballBehavior: _trackballBehavior,
                      title: ChartTitle(
                        text: "나의 물마시기 기록",
                        textStyle: const TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      legend: Legend(
                        isVisible: true,
                        position: LegendPosition.bottom,
                      ),
                      tooltipBehavior: _tooltipBehavior,
                      series: <ChartSeries>[
                        LineSeries<IntakeData, dynamic>(
                          name: '물',
                          legendIconType: LegendIconType.circle,
                          dataSource: chartData,

                          //요기

                          xValueMapper: (IntakeData data, _) =>
                              data.date.substring(data.date.length - 5),
                          yValueMapper: (IntakeData data, _) =>
                              data.waterIntake,
                          dataLabelSettings: const DataLabelSettings(
                            isVisible: false,
                          ),
                          enableTooltip: true,
                          color: Colors.indigo,
                          width: 4,
                          opacity: 0.9,
                        ),
                        LineSeries<IntakeData, dynamic>(
                          name: '카페인음료',
                          legendIconType: LegendIconType.circle,
                          dataSource: chartData,

                          //요기

                          xValueMapper: (IntakeData data, _) =>
                              data.date.substring(data.date.length - 5),
                          yValueMapper: (IntakeData data, _) =>
                              data.caffeineIntake,
                          dataLabelSettings: const DataLabelSettings(
                            isVisible: false,
                          ),
                          enableTooltip: true,
                          color: Colors.deepOrange,
                          width: 4,
                          opacity: 0.9,
                        ),
                        LineSeries<IntakeData, dynamic>(
                          name: '비카페인음료',
                          legendIconType: LegendIconType.circle,
                          dataSource: chartData,

                          //요기

                          xValueMapper: (IntakeData data, _) =>
                              data.date.substring(data.date.length - 5),
                          yValueMapper: (IntakeData data, _) =>
                              data.noncaffeineIntake,
                          dataLabelSettings: const DataLabelSettings(
                            isVisible: false,
                          ),
                          enableTooltip: true,
                          color: Colors.teal,
                          width: 4,
                          opacity: 0.9,
                        ),
                      ],
                      plotAreaBorderWidth: 0,
                      primaryXAxis: CategoryAxis(
                        edgeLabelPlacement: EdgeLabelPlacement.shift,
                        borderColor: Colors.transparent,
                        borderWidth: 0,
                        axisLine: const AxisLine(width: 0),
                        majorGridLines: const MajorGridLines(width: 0),
                        majorTickLines:
                            const MajorTickLines(color: Colors.transparent),
                        labelRotation: 305,
                        labelStyle: const TextStyle(
                          fontSize: 10,
                        ),
                        rangePadding: ChartRangePadding.none,
                      ),
                      primaryYAxis: NumericAxis(
                        minimum: 0,
                        maximum: 2000,
                        interval: 200,
                        labelFormat: '{value}ml',
                        axisLine: const AxisLine(width: 0),
                        majorGridLines: const MajorGridLines(width: 0),
                        majorTickLines:
                            const MajorTickLines(color: Colors.transparent),
                      ),
                    );
                  },
                ),
              ),
            ),
          ),
          // 새 칠드런
          Align(
            alignment: Alignment.center,
            child: Container(
              margin: const EdgeInsets.symmetric(vertical: 20.0),
              width: screenWidth * 0.90,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(20),
                color: Colors.white,
                boxShadow: [
                  BoxShadow(
                    color: Colors.grey.withOpacity(0.5),
                    spreadRadius: 5,
                    blurRadius: 7,
                    offset: const Offset(0, 3),
                  ),
                ],
              ),
              child: SafeArea(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const SizedBox(height: 20),
                    const Center(
                      child: Text(
                        '오늘의 목표',
                        style: TextStyle(
                          fontSize: 23,
                          fontWeight: FontWeight.bold,
                        ),
                        textAlign: TextAlign.center,
                      ),
                    ),
                    const SizedBox(height: 20),
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 20.0),
                      child: GetX<ReportController>(
                        builder: (controller) {
                          return Text(
                            '물 마시기 ${controller.daily.value} / ${controller.goal.value}ml',
                            style: const TextStyle(
                              fontSize: 17.5,
                              fontWeight: FontWeight.bold,
                              color: Colors.grey,
                            ),
                          );
                        },
                      ),
                    ),
                    const SizedBox(height: 20),
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 20.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          GetX<ReportController>(
                            builder: (controller) {
                              return Text(
                                '달성률 ${(controller.daily.value / controller.goal.value * 100).toStringAsFixed(2)}%',
                                style: const TextStyle(
                                  fontSize: 17.5,
                                  fontWeight: FontWeight.bold,
                                  color: Colors.grey,
                                ),
                              );
                            },
                          ),
                          ElevatedButton(
                            onPressed: () {
                              Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) => ModifyGoalScreen(),
                                  ));
                            },
                            style: ElevatedButton.styleFrom(
                                backgroundColor:
                                    const Color.fromRGBO(220, 233, 252, 1)),
                            child: const Text(
                              '목표 재설정',
                              style: TextStyle(
                                color: Colors.grey,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 20),
                  ],
                ),
              ),
            ),
          ),
          // 새 칠드런
          Align(
            alignment: Alignment.center,
            child: Container(
              margin: const EdgeInsets.symmetric(vertical: 20.0),
              width: screenWidth * 0.90,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(20),
                color: Colors.white,
                boxShadow: [
                  BoxShadow(
                    color: Colors.grey.withOpacity(0.5),
                    spreadRadius: 5,
                    blurRadius: 7,
                    offset: const Offset(0, 3),
                  ),
                ],
              ),
              child: SafeArea(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const SizedBox(height: 20),
                    const Center(
                      child: Text(
                        '주간 리포트',
                        style: TextStyle(
                          fontSize: 23,
                          fontWeight: FontWeight.bold,
                        ),
                        textAlign: TextAlign.center,
                      ),
                    ),
                    const SizedBox(height: 20),
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 20.0),
                      child: GetX<ReportController>(
                        builder: (controller) {
                          num target = 0;

                          for (int i = 0; i < controller.weekly.length; i++) {
                            target =
                                target + controller.weekly[i]['waterDrink'];
                          }

                          String formatTarget(num target) {
                            num dividedTarget = target / 1000;

                            if (target < 10) {
                              return NumberFormat("0.000", "en_US")
                                  .format(dividedTarget);
                            } else if (target < 100) {
                              return NumberFormat("0.00", "en_US")
                                  .format(dividedTarget);
                            } else if (target < 1000) {
                              return NumberFormat("0.0", "en_US")
                                  .format(dividedTarget);
                            } else {
                              return NumberFormat("0", "en_US")
                                  .format(dividedTarget);
                            }
                          }

                          return Text(
                            '이번 주 물 섭취량 ${formatTarget(target)}L',
                            style: const TextStyle(
                              fontSize: 17.5,
                              fontWeight: FontWeight.bold,
                              color: Colors.grey,
                            ),
                          );
                        },
                      ),
                    ),
                    const SizedBox(height: 20),
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 20.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          GetX<ReportController>(
                            builder: (controller) {
                              num target = 0;

                              for (int i = 0;
                                  i < controller.weekly.length;
                                  i++) {
                                target = target +
                                    controller.weekly[i]['nonCafeDrink'];
                              }

                              String formatTarget(num target) {
                                num dividedTarget = target / 1000;

                                if (target < 10) {
                                  return NumberFormat("0.000", "en_US")
                                      .format(dividedTarget);
                                } else if (target < 100) {
                                  return NumberFormat("0.00", "en_US")
                                      .format(dividedTarget);
                                } else if (target < 1000) {
                                  return NumberFormat("0.0", "en_US")
                                      .format(dividedTarget);
                                } else {
                                  return NumberFormat("0", "en_US")
                                      .format(dividedTarget);
                                }
                              }

                              return Text(
                                '이번 주 비카페인 음료 섭취량 ${formatTarget(target)}L',
                                style: const TextStyle(
                                  fontSize: 17.5,
                                  fontWeight: FontWeight.bold,
                                  color: Colors.grey,
                                ),
                              );
                            },
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 20),
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 20.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          GetX<ReportController>(
                            builder: (controller) {
                              num target = 0;

                              for (int i = 0;
                                  i < controller.weekly.length;
                                  i++) {
                                target =
                                    target + controller.weekly[i]['cafeDrink'];
                              }

                              String formatTarget(num target) {
                                num dividedTarget = target / 1000;

                                if (target < 10) {
                                  return NumberFormat("0.000", "en_US")
                                      .format(dividedTarget);
                                } else if (target < 100) {
                                  return NumberFormat("0.00", "en_US")
                                      .format(dividedTarget);
                                } else if (target < 1000) {
                                  return NumberFormat("0.0", "en_US")
                                      .format(dividedTarget);
                                } else {
                                  return NumberFormat("0", "en_US")
                                      .format(dividedTarget);
                                }
                              }

                              return Text(
                                '이번 주 카페인 음료 섭취량 ${formatTarget(target)}L',
                                style: const TextStyle(
                                  fontSize: 17.5,
                                  fontWeight: FontWeight.bold,
                                  color: Colors.grey,
                                ),
                              );
                            },
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 20),
                  ],
                ),
              ),
            ),
          ),
          // 새 칠드런
          Align(
            alignment: Alignment.center,
            child: Container(
              margin: const EdgeInsets.symmetric(vertical: 20.0),
              width: screenWidth * 0.90,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(20),
                color: Colors.white,
                boxShadow: [
                  BoxShadow(
                    color: Colors.grey.withOpacity(0.5),
                    spreadRadius: 5,
                    blurRadius: 7,
                    offset: const Offset(0, 3),
                  ),
                ],
              ),
              child: SafeArea(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const SizedBox(height: 20),
                    const Center(
                      child: Text(
                        '월간 리포트',
                        style: TextStyle(
                          fontSize: 23,
                          fontWeight: FontWeight.bold,
                        ),
                        textAlign: TextAlign.center,
                      ),
                    ),
                    const SizedBox(height: 20),
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 20.0),
                      child: GetX<ReportController>(
                        builder: (controller) {
                          num target = 0;

                          for (int i = 0; i < controller.monthly.length; i++) {
                            target =
                                target + controller.monthly[i]['waterDrink'];
                          }

                          String formatTarget(num target) {
                            num dividedTarget = target / 1000;

                            if (target < 10) {
                              return NumberFormat("0.000", "en_US")
                                  .format(dividedTarget);
                            } else if (target < 100) {
                              return NumberFormat("0.00", "en_US")
                                  .format(dividedTarget);
                            } else if (target < 1000) {
                              return NumberFormat("0.0", "en_US")
                                  .format(dividedTarget);
                            } else {
                              return NumberFormat("0", "en_US")
                                  .format(dividedTarget);
                            }
                          }

                          return Text(
                            '이번 달 물 섭취량 ${formatTarget(target)}L',
                            style: const TextStyle(
                              fontSize: 17.5,
                              fontWeight: FontWeight.bold,
                              color: Colors.grey,
                            ),
                          );
                        },
                      ),
                    ),
                    const SizedBox(height: 20),
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 20.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          GetX<ReportController>(
                            builder: (controller) {
                              num target = 0;

                              for (int i = 0;
                                  i < controller.monthly.length;
                                  i++) {
                                target = target +
                                    controller.monthly[i]['nonCafeDrink'];
                              }

                              String formatTarget(num target) {
                                num dividedTarget = target / 1000;

                                if (target < 10) {
                                  return NumberFormat("0.000", "en_US")
                                      .format(dividedTarget);
                                } else if (target < 100) {
                                  return NumberFormat("0.00", "en_US")
                                      .format(dividedTarget);
                                } else if (target < 1000) {
                                  return NumberFormat("0.0", "en_US")
                                      .format(dividedTarget);
                                } else {
                                  return NumberFormat("0", "en_US")
                                      .format(dividedTarget);
                                }
                              }

                              return Text(
                                '이번 달 비카페인 음료 섭취량 ${formatTarget(target)}L',
                                style: const TextStyle(
                                  fontSize: 17.5,
                                  fontWeight: FontWeight.bold,
                                  color: Colors.grey,
                                ),
                              );
                            },
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 20),
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 20.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          GetX<ReportController>(
                            builder: (controller) {
                              num target = 0;

                              for (int i = 0;
                                  i < controller.monthly.length;
                                  i++) {
                                target =
                                    target + controller.monthly[i]['cafeDrink'];
                              }

                              String formatTarget(num target) {
                                num dividedTarget = target / 1000;

                                if (target < 10) {
                                  return NumberFormat("0.000", "en_US")
                                      .format(dividedTarget);
                                } else if (target < 100) {
                                  return NumberFormat("0.00", "en_US")
                                      .format(dividedTarget);
                                } else if (target < 1000) {
                                  return NumberFormat("0.0", "en_US")
                                      .format(dividedTarget);
                                } else {
                                  return NumberFormat("0", "en_US")
                                      .format(dividedTarget);
                                }
                              }

                              return Text(
                                '이번 달 카페인 음료 섭취량 ${formatTarget(target)}L',
                                style: const TextStyle(
                                  fontSize: 17.5,
                                  fontWeight: FontWeight.bold,
                                  color: Colors.grey,
                                ),
                              );
                            },
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 20),
                  ],
                ),
              ),
            ),
          ),
        ], // 차트 박스
      ),
    ); //바깥 박스
  }
}
