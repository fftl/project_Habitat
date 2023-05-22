import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

class Bluetooth extends StatefulWidget {
  const Bluetooth({super.key});

  @override
  State<Bluetooth> createState() => _BluetoothState();
}

class _BluetoothState extends State<Bluetooth> {
  late BluetoothDevice _device;
  late BluetoothCharacteristic _characteristic;
  FlutterBluePlus flutterBlue = FlutterBluePlus.instance;

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          ElevatedButton(
            onPressed: _scanForDevices,
            child: const Text('Scan for devices'),
          ),
          const SizedBox(height: 16),
          ElevatedButton(
            onPressed: _connectToDevice,
            child: const Text('Connect to device'),
          ),
          const SizedBox(height: 16),
          ElevatedButton(
            onPressed: _sendData,
            child: const Text('Send data'),
          ),
          ElevatedButton(
            onPressed: _stopForDevices,
            child: const Text('stop data'),
          ),
        ],
      ),
    );
  }

  void _scanForDevices() async {
    // Start scanning
    print('start scanning');
    await flutterBlue.startScan(timeout: const Duration(seconds: 4));

    print(flutterBlue.scanResults.first.toString());
    // Listen to scan results
    var subscription = flutterBlue.scanResults.listen((results) {
      // do something with scan results
      print("나의 $results");
      for (ScanResult r in results) {
        print("okokyo");
        print('${r.device.name} found! rssi: ${r.rssi}');
        print("okokyo!!!!!!!!!!!!");
      }
    });
  }

  void _stopForDevices() async {
    // Stop scanning
    flutterBlue.stopScan();
    print('stop scanning');
  }

  void _connectToDevice() async {
    try {
      // Connect to the selected device
      await _device.connect();

      // Discover the services and characteristics of the device
      List<BluetoothService> services = await _device.discoverServices();

      for (BluetoothService service in services) {
        List<BluetoothCharacteristic> characteristics = service.characteristics;
        for (BluetoothCharacteristic characteristic in characteristics) {
          if (characteristic.uuid.toString() ==
              "00002a00-0000-1000-8000-00805f9b34fb") {
            // This is the characteristic we want to use for data transfer
            _characteristic = characteristic;
          }
        }
      }
      print('Connected to device');
    } catch (e) {
      print('Error connecting to device: $e');
    }
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

class BluetoothScanBoutton extends StatefulWidget {
  const BluetoothScanBoutton({super.key});

  @override
  State<BluetoothScanBoutton> createState() => _BluetoothScanBoutton();
}

class _BluetoothScanBoutton extends State<BluetoothScanBoutton> {
  late BluetoothDevice _device;
  late BluetoothCharacteristic _characteristic;
  FlutterBluePlus flutterBlue = FlutterBluePlus.instance;

  @override
  Widget build(BuildContext context) {
    return TextButton(
      onPressed: _scanForDevices,
      child: const Text("button"),
    );
  }

  void _scanForDevices() async {
    // Start scanning
    print('start scanning');
    await flutterBlue.startScan(timeout: const Duration(seconds: 4));

    print(flutterBlue.scanResults.first.toString());
    // Listen to scan results
    var subscription = flutterBlue.scanResults.listen((results) {
      // do something with scan results
      print("나의 $results");
      for (ScanResult r in results) {
        print("okokyo");
        print('${r.device.name} found! rssi: ${r.rssi}');
        print("okokyo!!!!!!!!!!!!");
      }
    });
  }
}
