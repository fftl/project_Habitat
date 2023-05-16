import 'package:flutter/material.dart';
import 'package:syncfusion_flutter_charts/charts.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const Scaffold(
        body: SafeArea(
          child: MyHomePage(title: 'Flutter Demo Home Page'),
        ),
      ),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  late List<IntakeData> _chartData;
  late TooltipBehavior _tooltipBehavior;
  late TrackballBehavior _trackballBehavior;

  @override
  void initState() {
    _chartData = getChartData();
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
    double screenHeight = MediaQuery.of(context).size.height;
    double screenWidth = MediaQuery.of(context).size.width;

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
                child: SfCartesianChart(
                  trackballBehavior: _trackballBehavior,
                  title: ChartTitle(
                    text: "소영쏘's drink",
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
                      dataSource: _chartData,
                      xValueMapper: (IntakeData data, _) => data.date,
                      yValueMapper: (IntakeData data, _) => data.waterIntake,
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
                      dataSource: _chartData,
                      xValueMapper: (IntakeData data, _) => data.date,
                      yValueMapper: (IntakeData data, _) => data.caffeineIntake,
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
                      dataSource: _chartData,
                      xValueMapper: (IntakeData data, _) => data.date,
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
                  // primaryXAxis: DateTimeCategoryAxis(
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
                      fontSize: 8,
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
                      child: Text(
                        '물 마시기 $waterDrank / $waterGoal ml',
                        style: const TextStyle(
                          fontSize: 17.5,
                          fontWeight: FontWeight.bold,
                          color: Colors.grey,
                        ),
                      ),
                    ),
                    const SizedBox(height: 20),
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 20.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            '달성률 ${achievementRate.toStringAsFixed(2)}%',
                            style: const TextStyle(
                              fontSize: 17.5,
                              fontWeight: FontWeight.bold,
                              color: Colors.grey,
                            ),
                          ),
                          ElevatedButton(
                            onPressed: () {
                              // 이 곳에 페이지 이동 코드를 작성하세요.
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
              child: const SafeArea(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    SizedBox(height: 20),
                    Center(
                      child: Text(
                        '주간 리포트',
                        style: TextStyle(
                          fontSize: 23,
                          fontWeight: FontWeight.bold,
                        ),
                        textAlign: TextAlign.center,
                      ),
                    ),
                    SizedBox(height: 20),
                    Padding(
                      padding: EdgeInsets.symmetric(horizontal: 20.0),
                      child: Text(
                        '이번 주 물 섭취량 7.6L',
                        style: TextStyle(
                          fontSize: 17.5,
                          fontWeight: FontWeight.bold,
                          color: Colors.grey,
                        ),
                      ),
                    ),
                    SizedBox(height: 20),
                    Padding(
                      padding: EdgeInsets.symmetric(horizontal: 20.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            '이번 주 비카페인 음료 섭취량 0.3L',
                            style: TextStyle(
                              fontSize: 17.5,
                              fontWeight: FontWeight.bold,
                              color: Colors.grey,
                            ),
                          ),
                        ],
                      ),
                    ),
                    SizedBox(height: 20),
                    Padding(
                      padding: EdgeInsets.symmetric(horizontal: 20.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            '이번 주 카페인 음료 섭취량 22L',
                            style: TextStyle(
                              fontSize: 17.5,
                              fontWeight: FontWeight.bold,
                              color: Colors.grey,
                            ),
                          ),
                        ],
                      ),
                    ),
                    SizedBox(height: 20),
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
              child: const SafeArea(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    SizedBox(height: 20),
                    Center(
                      child: Text(
                        '월간 리포트',
                        style: TextStyle(
                          fontSize: 23,
                          fontWeight: FontWeight.bold,
                        ),
                        textAlign: TextAlign.center,
                      ),
                    ),
                    SizedBox(height: 20),
                    Padding(
                      padding: EdgeInsets.symmetric(horizontal: 20.0),
                      child: Text(
                        '이번 달 물 섭취량 37L',
                        style: TextStyle(
                          fontSize: 17.5,
                          fontWeight: FontWeight.bold,
                          color: Colors.grey,
                        ),
                      ),
                    ),
                    SizedBox(height: 20),
                    Padding(
                      padding: EdgeInsets.symmetric(horizontal: 20.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            '이번 달 비카페인 음료 섭취량 3.2L',
                            style: TextStyle(
                              fontSize: 17.5,
                              fontWeight: FontWeight.bold,
                              color: Colors.grey,
                            ),
                          ),
                        ],
                      ),
                    ),
                    SizedBox(height: 20),
                    Padding(
                      padding: EdgeInsets.symmetric(horizontal: 20.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            '이번 달 카페인 음료 섭취량 82L',
                            style: TextStyle(
                              fontSize: 17.5,
                              fontWeight: FontWeight.bold,
                              color: Colors.grey,
                            ),
                          ),
                        ],
                      ),
                    ),
                    SizedBox(height: 20),
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

int waterDrank = 370;
int waterGoal = 800;
double achievementRate = (waterDrank / waterGoal) * 100;

List<IntakeData> getChartData() {
  final List<IntakeData> chartData = [
    IntakeData('월요일', 1234, 567, 890),
    IntakeData('화요일', 1000, 200, 300),
    IntakeData('수요일', 0, 0, 0),
    IntakeData('목요일', 1357, 246, 789),
    IntakeData('금요일', 500, 600, 700),
    IntakeData('토요일', 800, 500, 300),
    IntakeData('일요일', 100, 200, 300),
  ];

  return chartData;
}

class IntakeData {
  IntakeData(
      this.date, this.waterIntake, this.caffeineIntake, this.noncaffeineIntake);
  final String date;
  final double waterIntake;
  final double caffeineIntake;
  final double noncaffeineIntake;
}
