class FlowerStatusModel {
  final ExpModel exp;
  final FlowerModel flower;

  FlowerStatusModel.fromJson(Map<String, dynamic> json)
      : exp = ExpModel.fromJson(json['exp']),
        flower = FlowerModel.fromJson(json['flower']);
}

class ExpModel {
  final int flowerKey, exp, maxExp, lv;

  ExpModel.fromJson(Map<String, dynamic> json)
      : flowerKey = json['flowerKey'],
        exp = json['exp'],
        maxExp = json['maxExp'],
        lv = json['lv'];
}

class FlowerModel {
  final int flowerKey;
  final String name, story, getCondition;

  FlowerModel.fromJson(Map<String, dynamic> json)
      : flowerKey = json['flowerKey'],
        name = json['name'],
        story = json['story'],
        getCondition = json['getCondition'];
}

class FlowerCollectionModel {
  final int flowerKey, userStatus;
  final String name, story, getCondition;

  FlowerCollectionModel.fromJson(Map<String, dynamic> json)
      : flowerKey = json['flowerKey'],
        name = json['name'],
        story = json['story'],
        getCondition = json['getCondition'],
        userStatus = json['userStatus'];
}
