import 'package:flutter/material.dart';
import 'kakao_login.dart';
// import 'google_login.dart';
import 'login_view_model.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({Key? key}) : super(key: key);

  final String title = "final title";

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final viewModel = LoginViewModel(KakaoLogin());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            // Image.network(
            //     viewModel.user?.kakaoAccount?.profile?.profileImageUrl ?? ''),
            Text(
              '카카오 : ${viewModel.isKakaoLogined}, 구글 : ${viewModel.isGoogleLogined}',
            ),
            ElevatedButton(
              onPressed: () async {
                await viewModel.kakaoLogin();
                setState(() {});
              },
              child: const Text('Kakao'),
            ),
            ElevatedButton(
              onPressed: () async {
                await viewModel.googleLogin();
                setState(() {});
              },
              child: const Text('Google'),
            ),
            ElevatedButton(
              onPressed: () async {
                await viewModel.logout();
                setState(() {});
              },
              child: const Text('Logout'),
            ),
          ],
        ),
      ),
    );
  }
}
