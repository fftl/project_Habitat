import 'dart:convert';

import 'package:habitat/api/base_url.dart';
import 'package:habitat/models/friends_code_model.dart';
import 'package:http/http.dart' as http;

class ApiFriendcode {
  final String baseurl = BaseUrl().rooturl;

  Future<String> getCode(String id) async {
    late String userCode;

    Uri url = Uri.http(baseurl, 'friends/code', {'userKey': id});
    final response = await http.get(url);
    if (response.statusCode == 200) {
      final temp = jsonDecode(response.body);
      final friendcodeModel = FriendcodeModel.fromJson(temp);
      userCode = friendcodeModel.code;
      print(friendcodeModel);
    }

    return userCode;
  }
}
