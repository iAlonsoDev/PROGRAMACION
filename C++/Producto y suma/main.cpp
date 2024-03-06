#include <iostream>
#include <conio.h>
using namespace std;

/* cree un programa que devuelva el residuo de la division d dos numeros*/


int main(){
	
	int n1, n2, res1, n3, n4, res2;
	
	cout<<"Digite el primer numero: ";
	cin>>n1;
	
	cout<<"\n";
	
	cout<<"Digite el segundo numero: ";
	cin>>n2;
	
	cout<<"\n";
	
	cout<<"Digite el tercer numero: ";
	cin>>n3;
	
	cout<<"\n";
	
	cout<<"Digite el cuarto numero: ";
	cin>>n4;
	
	cout<<"\n";
		
	
	res1 = n1 * n2;
	res2 = n3 + n4;
	
	cout<<"\n";
	
		cout<<"\nEl producto de la multiplicacion es:\n";
		cout<<res1<<endl;
		
	cout<<"\n";

	cout<<"\nLa suma de los ultimos dos numeros es:\n";
	cout<<res2<<endl;
	
getch();
return 0;
}
