import 'package:flutter/material.dart';
import 'dart:math' as math;

class HomeScreen extends StatelessWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text("Alonso Dev"),
          centerTitle: true,
        ),
        body: (LayoutBuilder(
          builder: (BuildContext context, BoxConstraints constraints) {
            return CustomPaint(
              painter: MyPainter(),
              size: Size(constraints.maxWidth, constraints.maxHeight),
            );
          },
        )));
  }
}

class MyPainter extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    Paint linePaint = Paint();

    canvas.drawLine(const Offset(50, 150), const Offset(150, 200), linePaint);
    canvas.drawLine(
        Offset(size.width - 50, 150), Offset(size.width - 150, 200), linePaint);

    Paint circlePaint = Paint();
    canvas.drawCircle(const Offset(100, 250), 20, circlePaint);
    canvas.drawCircle(Offset(size.width - 100, 250), 20, circlePaint);

    Paint arcPainter = Paint()
      ..strokeWidth = 10
      ..style = PaintingStyle.stroke;
    Rect rect = const Rect.fromLTWH(90, 350, 220, 300);
    canvas.drawArc(rect, math.pi, math.pi, true, arcPainter);
  }

  @override
  // ignore: avoid_renaming_method_parameters
  bool shouldRepaint(CustomPainter old) {
    return true;
  }
}
