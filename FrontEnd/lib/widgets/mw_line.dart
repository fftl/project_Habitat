import 'dart:math';

import 'package:flutter/material.dart';

class MwLine extends StatefulWidget {
  const MwLine({super.key});

  @override
  State<MwLine> createState() => _MwLineState();
}

class _MwLineState extends State<MwLine> {
  final linelist = [
    "간지러워요.",
    "제로 콜라엔 설탕은 없지만, 카페인이 들어있어요.",
    "탄산수는 물 대신 마셔도 되지만, 사람에 따라서는 위에 안 좋을 수 있어요.",
    "녹차, 홍차, 우롱차에는 카페인이 들어있어요.",
    "물을 너무 한꺼번에 마시는 것은 좋지 않아요.",
    "카페인이 들어간 음료를 마시면, 이뇨작용으로 인해 오히려 갈증이 날 수 있어요.",
    "디카페인 커피에도 카페인이 없는 것은 아니에요.",
    "곡물차를 물 대신 마시는 것도 권장되지는 않아요.",
    "이온음료는 물 대신 마셔도 되지만, 설탕 과잉섭취에 주의하세요!",
    "설탕이 든 음료를 마시는 것은 권장되지 않지만, 물을 아예 마시지 않는 것보다는 나아요.",
    "땀을 많이 흘린 날엔 평소보다 자주, 조금씩 물을 마셔주세요.",
    "저 오늘 예쁘죠?"
  ];

  late String line = '';

  onChangeLine() {
    line = linelist[Random().nextInt(12)];
    setState(() {});
  }

  @override
  void initState() {
    super.initState();
    onChangeLine();
  }

  @override
  Widget build(BuildContext context) {
    debugPrint(linelist[Random().nextInt(12)]);
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            width: 250,
            height: 80,
            padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
            transformAlignment: Alignment.center,
            alignment: Alignment.center,
            decoration: const BoxDecoration(
              color: Color.fromRGBO(250, 250, 250, 0.3),
              borderRadius: BorderRadius.all(
                Radius.circular(24),
              ),
            ),
            child: Text(
              line,
              style: const TextStyle(
                fontSize: 17,
              ),
            ),
          ),
          IconButton(
            onPressed: onChangeLine,
            icon: Image.asset('lib/assets/images/characters/blueMangWull.png'),
            iconSize: 52,
            padding: const EdgeInsets.only(left: 18),
          )
        ],
      ),
    );
  }
}
