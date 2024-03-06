#include <iostream>
#include <conio.h>
using namespace std;

/* cree un programa que devuelva el residuo de la division d dos numeros*/


int main(){
	
	int sueldo, deduccion, resultado;
	
	cout<<"Digite el sueldo: ";
	cin>>sueldo;
	
	cout<<"\n";
	
	cout<<"Digite la deduccion: ";
	cin>>deduccion;
	
	resultado = sueldo - deduccion;
	
	cout<<"\n";
	
		cout<<"\nEl sueldo neto es:\n";
		cout<<resultado<<endl;
	
getch();
return 0;
}
