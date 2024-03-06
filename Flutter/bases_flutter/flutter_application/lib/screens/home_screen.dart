import 'package:flutter/material.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int currentPage = 1;
  // ignore: unnecessary_new
  final PageController pageController = new PageController(initialPage: 1);

  @override
  Widget build(BuildContext context) {
    //Scaffold crea el lienzo
    return Scaffold(
      //* AppBar
      appBar: AppBar(
        title: Text('Hola Mundo $currentPage'),
        centerTitle: true,
        elevation: 0,
      ),

      //? Cambiar pantalla
      body: PageView(
        controller: pageController,
        physics: const NeverScrollableScrollPhysics(),
        //ignore: prefer_const_literals_to_create_immutables
        children: [
          const CustomScreen(color: Colors.blue),
          const CustomScreen(color: Colors.pink),
          const CustomScreen(color: Colors.indigo),
        ],
      ),

      //? para comentar asi debemos shit + alt + a
      // body: currentPage == 0
      //   ? const CustomScreen(color: Colors.pink)
      //   : currentPage == 1
      //       ? const CustomScreen(color: Colors.indigo)
      //       : const CustomScreen(color: Colors.green),

      //* Tabs
      bottomNavigationBar: BottomNavigationBar(
        //indica la pagina donde estamos posicionados
        currentIndex: currentPage,
        onTap: (index) {
          currentPage = index;
          pageController.animateToPage(index,
              duration: const Duration(milliseconds: 300),
              curve: Curves.easeInOut);
          setState(() {});
        },

        backgroundColor: Colors.blue,
        selectedItemColor: Colors.white,
        unselectedItemColor: Colors.white.withOpacity(0.5),
        // ignore: prefer_const_literals_to_create_immutables
        items: [
          const BottomNavigationBarItem(
              icon: Icon(Icons.verified_user_outlined), label: 'User'),
          const BottomNavigationBarItem(
              icon: Icon(Icons.settings), label: 'Setting'),
          const BottomNavigationBarItem(
              icon: Icon(Icons.card_membership_outlined), label: 'Card'),
        ],
      ),
    );
  }
}

//para llamar
class CustomScreen extends StatelessWidget {
  const CustomScreen({Key? key, required this.color}) : super(key: key);

  //recibir el color por argumento
  final Color color;

  @override
  Widget build(BuildContext context) {
    return Container(
      color: color,
      child: const Center(
        child: Text('Custom Screen'),
      ),
    );
  }
}
