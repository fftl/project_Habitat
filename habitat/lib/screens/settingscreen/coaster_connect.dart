import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:habitat/screens/settingscreen/coaster_link.dart';

class CoasterConnect extends StatefulWidget {
  const CoasterConnect({super.key});

  @override
  State<CoasterConnect> createState() => _CoasterConnectState();
}

class _CoasterConnectState extends State<CoasterConnect> {
  late BluetoothCharacteristic _characteristic;
  FlutterBluePlus flutterBlue = FlutterBluePlus.instance;
  List<ScanResult> scanResultList = [];
  bool _isScanning = false;

  @override
  void initState() {
    super.initState();
  }

  List drinkType = ['water', 'coffee', 'non-coffee'];
  var selectedWeatherValue;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Column(
        children: [
          const SizedBox(
            height: 50,
          ),
          ElevatedButton(
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.lightBlue,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(12.0),
              ),
            ),
            onPressed: _isScanning ? null : _scanForDevices,
            child: const Text(
              "기기 검색",
              style: TextStyle(
                fontSize: 26,
                color: Colors.white,
              ),
            ),
          ),
          const SizedBox(
            height: 10,
          ),
          Builder(builder: (context) {
            if (_isScanning) {
              return const Text(
                "블루투스 스캔중...",
                style: TextStyle(
                  fontSize: 14,
                  color: Colors.black,
                ),
              );
            } else {
              return const Text(
                "스캔 완료",
                style: TextStyle(
                  fontSize: 14,
                  color: Colors.black,
                ),
              );
            }
          }),
          SizedBox(
            height: 500,
            child: ListView.builder(
              itemCount: scanResultList.length,
              itemBuilder: (context, index) {
                var device = scanResultList[index];
                return Container(
                  height: 50,
                  padding: const EdgeInsets.all(0),
                  margin:
                      const EdgeInsets.symmetric(horizontal: 50, vertical: 10),
                  decoration: const BoxDecoration(
                    borderRadius: BorderRadius.all(
                      Radius.circular(16.0),
                    ),
                  ),
                  child: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      disabledBackgroundColor: Colors.lightBlue,
                      shape: const RoundedRectangleBorder(
                        borderRadius: BorderRadius.all(Radius.circular(16)),
                      ),
                    ),
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) =>
                                CoasterLink(connectdevice: device.device)),
                      );
                    },
                    child: Text(
                      deviceName(device),
                      style: const TextStyle(
                        fontSize: 18,
                        color: Colors.black,
                      ),
                    ),
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  // 신호값 출력
  Widget deviceSignal(ScanResult r) {
    return Text(r.rssi.toString());
  }

  // MAC 주소 출력
  Widget deviceMacAddress(ScanResult r) {
    return Text(r.device.id.id);
  }

  // 장치 명 출력
  String deviceName(ScanResult r) {
    String name = '';

    if (r.device.name.isNotEmpty) {
      name = r.device.name;
    } else if (r.advertisementData.localName.isNotEmpty) {
      name = r.advertisementData.localName;
    } else {
      name = "no name";
    }

    return name;
  }

  void _scanForDevices() async {
    // 스캔 결과 초기화
    setState(() {
      scanResultList.clear();
    });

    debugPrint("블루투스 스캔 시작");
    _isScanning = true;
    //스캔 시작 제한시간 5초
    await flutterBlue.startScan(timeout: const Duration(seconds: 5));
    // 스캔 결과 할당
    flutterBlue.scanResults.listen(
      (results) {
        debugPrint("검색이 되는가? : $results");

        setState(() {
          for (var element in results) {
            debugPrint("블루투스 기기 검색 : ${element.device.name}");
            if (element.device.name != '') {
              if (scanResultList
                      .indexWhere((e) => e.device.id == element.device.id) <
                  0) {
                scanResultList.add(element);
              }
            }
          }
        });
      },
    );
    _isScanning = false;
  }

  void _sendData() async {
    try {
      // Encode the data as bytes
      String data = "Hello from Flutter!";
      List<int> bytes = utf8.encode(data);

      // Send the data to the characteristic
      await _characteristic.write(bytes);

      print('Data sent: $data');
    } catch (e) {
      print('Error sending data: $e');
    }
  }
}
