import 'package:flutter/material.dart';

class LoginScreen extends StatelessWidget {
  const LoginScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text("Google"),
        ),
        body: const Center(
          child: Column(children: [
            // TextButton(
            //   onPressed: signInWithGoogle,
            //   child: Text("Sign in with Google"),
            // )
          ]),
        ));
  }
}
