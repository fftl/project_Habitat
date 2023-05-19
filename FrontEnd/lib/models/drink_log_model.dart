class Drinklogmodel {
  final int drinkLogKey, drink;
  final bool isCoaster;
  final String drinkType, createdAt;

  Drinklogmodel.fromJson(Map<String, dynamic> json)
      : drinkLogKey = json['drinkLogKey'],
        drink = json['drink'],
        isCoaster = json['isCoaster'],
        drinkType = json['drinkType'],
        createdAt = json['createdAt'];
}
