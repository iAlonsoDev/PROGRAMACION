/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

//LIBRERIAS PARA SQL SERVER
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author jonat
 */
public class Conexion {
    public String usuario = "JAlonsoArt";
    public String password = "6321JART";
    public String url = "jdbc:sqlserver://JALONSOART\\JALONZOART:1433;databaseName=ABAS;";
   
  public Connection cn = null;
  public Statement st = null;
        
  public Statement Conectar() 
        {

            try {
                Connection cn = DriverManager.getConnection(url,usuario,password);

            st=cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            } catch (SQLException i){
               JOptionPane.showMessageDialog(null, i);
            } 
            
        return st;
        }
  
}