import 'social_login.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:kakao_flutter_sdk_user/kakao_flutter_sdk_user.dart' as kakao;
import 'package:firebase_auth/firebase_auth.dart';

class LoginViewModel {
  final SocialLogin _socialLogin;
  final GoogleSignIn _googleSignIn = GoogleSignIn();
  final FirebaseAuth _auth = FirebaseAuth.instance;

  bool isKakaoLogined = false;
  bool isGoogleLogined = false;
  kakao.User? kakaoUser;
  User? googleUser;

  LoginViewModel(this._socialLogin);

  Future kakaoLogin() async {
    isKakaoLogined = await _socialLogin.login();
    if (isKakaoLogined) {
      kakaoUser = await kakao.UserApi.instance.me();
    }
  }

  Future googleLogin() async {
    GoogleSignInAccount? googleUser = await _googleSignIn.signIn();
    final GoogleSignInAuthentication? googleAuth =
        await googleUser?.authentication;

    final credential = GoogleAuthProvider.credential(
      accessToken: googleAuth?.accessToken,
      idToken: googleAuth?.idToken,
    );

    await _auth.signInWithCredential(credential);

    final User? firebaseUser = _auth.currentUser;
    if (firebaseUser != null) {
      isGoogleLogined = true;
      googleUser = firebaseUser as GoogleSignInAccount?;
    }
  }

  Future logout() async {
    await _socialLogin.logout();
    await _googleSignIn.signOut();
    isKakaoLogined = false;
    isGoogleLogined = false;
    kakaoUser = null;
    googleUser = null;
  }
}
