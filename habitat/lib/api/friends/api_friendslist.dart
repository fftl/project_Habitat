import 'dart:convert';

import 'package:habitat/api/base_url.dart';
import 'package:habitat/models/friends_list_model.dart';
import 'package:http/http.dart' as http;

class ApiFriendsList {
  final String baseurl = BaseUrl().rooturl;

  Future<List<FriendsListModel>> getFriendsList(String id) async {
    List<FriendsListModel> friendslist = [];

    Uri url = Uri.http(baseurl, 'friends/all', {'userKey': id});
    final response = await http.get(url);
    if (response.statusCode == 200) {
      final List<dynamic> temp = jsonDecode(utf8.decode(response.bodyBytes));
      for (var e in temp) {
        friendslist.add(FriendsListModel.fromJson(e));
      }
    }

    return friendslist;
  }
}
