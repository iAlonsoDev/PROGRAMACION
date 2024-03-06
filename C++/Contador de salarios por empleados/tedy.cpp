#include <iostream>
#include <cstdlib>
#include <string>
#include <conio.h>
#include <stdio.h>
using namespace::std;

int Arreglo = 2;

int main()
   {  

	char seguir;
    int acumulador, contador, numero;

    acumulador = 0;
    contador = 0;

    float A[Arreglo + 1];
    int B[Arreglo + 1], C[11] = {0};
    
    
    int i, j, k, acu;
    float ventas;
    float ventasB;

    cout << "\n\nCALCULOS DE SALARIOS OBTENIDOS " << Arreglo <<" EMPLEADOS. "<< endl;
    for ( i = 1; i <= Arreglo; i++ )
      {
	   		cout << endl << endl << endl <<"INTRODUZCA LAS VENTAS HECHAS POR EL EMPLEADO " << i << endl;
      
              ventas = 0;
              numero = 0;
              acumulador = 0;
		      cout<<"Introduzca n para hacer la evaluacion:\n"; 
			do
		    {
		        scanf( "%d", &numero);
		
		        acumulador += numero;
		        contador++;
		
		        scanf( "%c", &seguir);
		    } while ( seguir != 'n' );
		
     	
			   ventas = acumulador - numero;
		       A[i] = (static_cast<float>(9)/100)*ventas + 200.00;
		       cout << "EL SALARIO ES 200, LA VENTA ES " << ventas << " LA GANANCIA TOTAL ES: " <<  A[i] << endl;
		       B[i] =  static_cast< int >( A[i] )/100;
		       
		       
	}
	

    for ( k = 1; k <= Arreglo; k++ )
      {
       if ( B[k] < 10)
       C[B[k]]++;
       else
       C[Arreglo]++;
      }
      
   for ( j = 2; j < Arreglo; j++ )

     {
      cout << endl << "EXISTEN " << C[j] <<" QUE COBRAN ENTRE "  << (j * 100) << " y ";
      cout << (( j + 1 ) * 100 ) - 1 << " pesos " <<endl;
     }

   cout <<endl<<"EXISTEN " << C[(Arreglo)] << " EMPLEADOS QUE COBRAN > DE 1000"<<endl <<endl << endl;
  
    
   system("PAUSE");
   
   }
