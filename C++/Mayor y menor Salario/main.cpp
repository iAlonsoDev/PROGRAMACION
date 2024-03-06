#include <iostream>
#include <conio.h>
using namespace std;

/* run this program using the console pauser or add your own getch, system("pause") or input loop */

struct Empleado{
	char nombre[20];
	float salario;
} emp[100];

int main(){
	
	int n_empleados, posM=0, posm=0;
	float mayor=0, menor = 9999999;
	
	cout<<"Digite la cantidad de empleados: ";
	cin>>n_empleados;
	
	for(int i=0;i<n_empleados;i++){
		fflush(stdin);
		cout<<i+1<<". Digite su nombre: ";
		cin.getline(emp[i].nombre, 20, '\n');
		cout<<i+1<<". Digite su salario: ";
		cin>>emp[i].salario;
		
	
		//empleado con mayor salario
		if(emp[i].salario > mayor){
		mayor = emp[i].salario;
		posM = i;
		}
		
		
		//empleado con menor salario
		if(emp[i].salario < menor){
		menor = emp[i].salario;
		posm = i;
		}
		
		cout<<"\n";
	}
	
		cout<<"\nDatos del empleado con mayor salario:.\n";
		cout<<"Nombre: " <<emp[posM].nombre<<endl;
		cout<<"Salario: " <<emp[posM].salario<<endl;
		
		cout<<"\nDatos del empleado con menor salario:.\n";
		cout<<"Nombre: " <<emp[posm].nombre<<endl;
		cout<<"Salario: " <<emp[posm].salario<<endl;
	
getch();
return 0;
}
