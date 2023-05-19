class FriendRequestModel {
  final int friendRequestKey;
  final String userKey;
  final String nickname;
  final String imgUrl;

  FriendRequestModel.fromJson(Map<String, dynamic> json)
      : friendRequestKey = json['friendRequestKey'] ?? 0,
        userKey = json['userKey'] ?? '',
        nickname = json['nickname'] ?? '',
        imgUrl = json['imgUrl'] ?? '';
}
