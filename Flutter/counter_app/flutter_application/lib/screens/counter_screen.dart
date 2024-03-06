import 'package:flutter/material.dart' show AppBar, BuildContext, Center, Colors, Column, FloatingActionButton, FloatingActionButtonLocation, FontWeight, Icon, Icons, Key, MainAxisAlignment, Row, Scaffold, State, StatefulWidget, Text, TextStyle, Widget;

class CounterScreen extends StatefulWidget {
  const CounterScreen({Key? key}) : super(key: key);
  @override
  State<CounterScreen> createState() => _CounterScreenState();
}

class _CounterScreenState extends State<CounterScreen> {
  //variable
  int counter = 0;
  static const fontSize30 = TextStyle(
    fontSize: 30,
    fontWeight: FontWeight.bold,
    color: Colors.blue,
  );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('AlonsoDev - 2021'),
        elevation: 0,
        shadowColor: Colors.grey,
        centerTitle: false,
      ),
      backgroundColor: Colors.white,
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'Counter by AlonsoDev',
              style: TextStyle(
                color: Colors.black,
                fontSize: 20,
              ),
            ),
            Text('$counter', style: fontSize30),
          ],
        ),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
      floatingActionButton: Column(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          Center(
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                FloatingActionButton(
                  child: const Icon(Icons.plus_one_outlined),
                  backgroundColor: Colors.black,
                  onPressed: () {
                    counter++;
                    setState(() {});
                  },
                ),
                FloatingActionButton(
                  child: const Icon(Icons.iso),
                  backgroundColor: Colors.black,
                  onPressed: () {
                    counter = 0;
                    setState(() {});
                  },
                ),
                FloatingActionButton(
                  child: const Icon(Icons.exposure_neg_1),
                  backgroundColor: Colors.black,
                  onPressed: () {
                    if (counter >= 1) {
                      counter--;
                    }
                    setState(() {});
                  },
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
