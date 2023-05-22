class Usersmodel {
  final String userKey, nickname, imgUrl;
  final int goal;

  Usersmodel.fromJson(Map<String, dynamic> json)
      : userKey = json['userKey'],
        nickname = json['nickname'],
        imgUrl = json['imgUrl'],
        goal = json['goal'];
}
