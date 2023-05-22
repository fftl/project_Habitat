import 'package:habitat/api/base_url.dart';

void postRequestCode({
  required dynamic Function(dynamic) success,
  required Function(String error) fail,
  Map<String, dynamic>? body,
}) {
  baseApi(
    path: 'friends/request/code',
    requestType: RequestType.post,
    body: body,
    success: success,
    fail: fail,
  );
}

class ApiSendRequestCode {
  // final String baseurl = BaseUrl().rooturl;

  // void postRequestCode(String friendCode, String userKey) async {
  //   Uri url = Uri.http(baseurl, 'friends/request/code', {
  //     'userKey': userKey,
  //   });

  //   final response = await http.post(
  //     url,
  //     headers: {"Content-Type": "application/json"},
  //     body: jsonEncode({
  //       'friendCode': friendCode,
  //     }),
  //   );
  //   if (response.statusCode == 200) {
  //     debugPrint('성공');
  //   } else {
  //     debugPrint('에러남');
  //   }
  // }
}
