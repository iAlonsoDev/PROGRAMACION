
import 'package:flutter/material.dart';
import 'package:flutter_application/screens/home_screen.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      //para quitar el debug banner
      debugShowCheckedModeBanner: false,
      title: 'Test',
      home: HomeScreen(),
    );
  }
}
