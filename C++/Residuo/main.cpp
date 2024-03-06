#include <iostream>
#include <conio.h>
using namespace std;

/* cree un programa que devuelva el residuo de la division d dos numeros*/


int main(){
	
	int dividendo, divisor, cociente, residuo, resultado;
	
	cout<<"Digite el numero dividendo: ";
	cin>>dividendo;
	
	cout<<"\n";
	
	cout<<"Digite el numero divisor: ";
	cin>>divisor;
	
	cociente = dividendo / divisor;
	resultado = cociente * divisor;
	residuo = dividendo - resultado;
	
	cout<<"\n";
	
		cout<<"\nEl valor del residuo es:\n";
		cout<<residuo<<endl;
	
getch();
return 0;
}
