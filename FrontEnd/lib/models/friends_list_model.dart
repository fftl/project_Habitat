class FriendsListModel {
  final String userKey;
  final String nickname;
  final String imgUrl;
  final String recent;

  FriendsListModel.fromJson(Map<String, dynamic> json)
      : userKey = json['userKey'] ?? '',
        nickname = json['nickname'] ?? '',
        imgUrl = json['imgUrl'] ?? '',
        recent = json['recent'] ?? '';
}
