class FriendcodeModel {
  final String code;

  FriendcodeModel.fromJson(Map<String, dynamic> json)
      : code = json['friend_code'];
}
