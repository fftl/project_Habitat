import 'package:flutter/material.dart';

class PlantCollectionModal extends StatelessWidget {
  final int flowerKey, userStatus;
  final String name, story, getCondition;

  const PlantCollectionModal(
      {super.key,
      required this.flowerKey,
      required this.userStatus,
      required this.name,
      required this.story,
      required this.getCondition});

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      style: ElevatedButton.styleFrom(
        minimumSize: const Size(80, 80),
        maximumSize: const Size(80, 80),
        backgroundColor: Colors.white,
      ),
      onPressed: () {
        if (userStatus == 2) {
          _showdialog(context);
        }
      },
      child: Builder(builder: (context) {
        if (userStatus == 2) {
          return Image.asset("lib/assets/images/flowers/$flowerKey/6.png");
        } else {
          return const Icon(
            Icons.question_mark_rounded,
            color: Colors.black,
          );
        }
      }),
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
                  Image.asset('lib/assets/images/flowers/$flowerKey/6.png'),
                  Text(
                    name,
                    style: const TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  Text(
                    story,
                    style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.w600,
                        color: Colors.grey[400]),
                  ),
                  Text(
                    "획득조건 : $getCondition",
                    style: TextStyle(
                        fontSize: 12,
                        fontWeight: FontWeight.w600,
                        color: Colors.grey[400]),
                  ),
                  TextButton(
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    child: const Text('닫기'),
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
