 bottomNavigationBar: BottomNavigationBar(
        backgroundColor: Colors.white,
        unselectedItemColor: Colors.blue,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.thumb_up), label: 'Like'),
          BottomNavigationBarItem(icon: Icon(Icons.restore), label: 'Restore'),
          BottomNavigationBarItem(
              icon: Icon(Icons.thumb_down), label: 'Dislike'),
        ],
        onTap: (index) {
          if (index == 0) {
            counter++;
          } else {
            if (index == 1) {
              counter = 0;
            } else {
              counter--;
            }
          }
          setState(() {});
        },
      ),

      //para ordenar al principio
      floatingActionButtonLocation: FloatingActionButtonLocation.miniStartFloat,
      floatingActionButton: Column(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          FloatingActionButton(
            child: const Icon(Icons.thumb_up),
            backgroundColor: Colors.blue,
            onPressed: () {
              counter++;
              setState(() {});
            },
          ),
          FloatingActionButton(
            child: const Icon(Icons.restore),
            backgroundColor: Colors.blue,
            onPressed: () {
              counter = 0;
              setState(() {});
            },
          ),
          FloatingActionButton(
            child: const Icon(Icons.thumb_down),
            backgroundColor: Colors.blue,
            onPressed: () {
              counter--;
              setState(() {});
            },
          ),
        ],
      ),

      CODIGO DE OTROS FLOATINH BUTTOM
      
      floatingActionButton: FloatingActionButton.extended(
        onPressed: () {
          counter++;
          setState(() {});
        },
        label: const Text('Like'),
        icon: const Icon(Icons.thumb_up),
        backgroundColor: Colors.blue,
      ),
      
      floatingActionButtonLocation: FloatingActionButtonLocation.miniStartFloat,
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.thumb_up),
        onPressed: () {
          counter++;
          setState(() {});
          //print('Counter: $counter')
        },
      ),
      
      