import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

class CoasterLink extends StatefulWidget {
  final BluetoothDevice connectdevice;
  const CoasterLink({super.key, required this.connectdevice});

  @override
  State<CoasterLink> createState() => _CoasterLinkState();
}

class _CoasterLinkState extends State<CoasterLink> {
  FlutterBluePlus flutterBlue = FlutterBluePlus.instance;

  // 연결상태
  String stateText = 'Stand by';
  // 연결상태 버튼
  String connectButtonText = 'disconnect';
  // 현재 연결상태 저장용
  BluetoothDeviceState deviceState = BluetoothDeviceState.disconnected;
  // 연결된 장치의 서비스 정보 저장용
  List<BluetoothService> bluetoothService = [];
  // 연결된 장치가 보내는 데이터 저장용
  Map<String, List<int>> notifyDatas = {};

  // late leng;
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          children: [
            const SizedBox(
              height: 30,
            ),
            const Text(
              "이곳은 커넥팅 페이지",
              style: TextStyle(fontSize: 32),
            ),
            const SizedBox(
              height: 30,
            ),
            Text(
              stateText,
              style: const TextStyle(fontSize: 16),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                OutlinedButton(
                  onPressed: connect,
                  child: const Text(
                    "연결",
                    style: TextStyle(fontSize: 16),
                  ),
                ),
                const SizedBox(
                  width: 5,
                ),
                OutlinedButton(
                  onPressed: disconnect,
                  child: const Text(
                    "연결해제",
                    style: TextStyle(fontSize: 16),
                  ),
                ),
              ],
            ),
            SizedBox(
              height: 300,
              child: ListView.builder(
                itemCount: bluetoothService.length,
                itemBuilder: (context, index) {
                  return characteristicInfo(bluetoothService[index]);
                },
              ),
            ),
            // Text("길이는? ${leng.toString()}")
          ],
        ),
      ),
    );
  }

  // 블루투스 연결
  Future<bool> connect() async {
    Future<bool>? returnValue;

    await widget.connectdevice
        .connect(autoConnect: false) // 자동연결 해제
        .timeout(const Duration(milliseconds: 10000), onTimeout: () {
      returnValue = Future.value(false); // 지연시간동안 연결 안되면 false
    }).then((data) async {
      if (returnValue == null) {
        //timeout이 되지 않았다면 연결 성공한 것
        returnValue == Future.value(true);

        debugPrint('connection successful');

        List<BluetoothService> bleService =
            await widget.connectdevice.discoverServices();

        setState(() {
          // 연결상태 출력
          stateText = 'Connecting';
          // 연결상태 정보 할당
          bluetoothService = bleService;
        });

        for (BluetoothService service in bleService) {
          for (BluetoothCharacteristic c in service.characteristics) {
            await widget.connectdevice.requestMtu(223);

            // List<int> value = await c.read();
            // print("배고파요");
            // print(value);
            if (!c.isNotifying) {
              // leng = c.value.length;
              try {
                await c.setNotifyValue(true);
                // 받을 데이터 변수 Map 형식으로 키 생성
                notifyDatas[c.uuid.toString()] = List.empty();

                c.value.listen((value) {
                  // 데이터 읽기 처리!
                  print('받은 데이터 : ${c.uuid}: $value');
                  // print("리드에 뭐가 들었나 ${c.read().toString()}");
                  setState(() {
                    // 받은 데이터 저장 화면 표시용
                    notifyDatas[c.uuid.toString()] = value;
                  });
                });

                // 설정 후 일정시간 지연
                // await Future.delayed(const Duration(milliseconds: 500));
              } catch (e) {
                debugPrint('error ${c.uuid} $e');
              }
            }
          }
        }
      }
    });
    return returnValue ?? Future.value(false);
  }

  // 연결 해제
  void disconnect() {
    try {
      setState(() {
        stateText = 'Disconnecting';
      });
      widget.connectdevice.disconnect();
    } catch (e) {}
  }

  Widget characteristicInfo(BluetoothService r) {
    String info = '';
    String properties = '';
    String data = '';

    // 캐릭터리스틱을 한개씩 꺼내서 표시
    for (BluetoothCharacteristic c in r.characteristics) {
      properties = '';
      data = '';
      info += '\t\t${c.uuid}\n';
      if (c.properties.write) {
        properties += 'Write ';
      }
      if (c.properties.read) {
        properties += 'Read ';
      }
      if (c.properties.notify) {
        properties += 'Notify ';
        if (notifyDatas.containsKey(c.uuid.toString())) {
          // notify 데이터가 존재한다면
          if (notifyDatas[c.uuid.toString()]!.isNotEmpty) {
            data += "블루투스 데이터 받아오기 \n";
            data += notifyDatas[c.uuid.toString()].toString();
            data = dataConvert(notifyDatas[c.uuid.toString()]!);
          }
        }
      }
      if (c.properties.writeWithoutResponse) {
        properties += 'WriteWR ';
      }
      if (c.properties.indicate) {
        properties += 'Indicate ';
      }
      info += '\t\t\tProperties: $properties\n';
      if (data.isNotEmpty) {
        // 받은 데이터 화면에 출력!
        info += '\t\t\t\t$data\n';
      }
    }

    return Text(info);
  }
}

String dataConvert(List<int> notifyDatas) {
  String convertdata;
  convertdata = ascii.decode(notifyDatas);
  return convertdata;
}
