/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea2_2;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author APOYO
 */
public class Tarea2_2 {
    
    //se añade al final throws classNotFoundException, SQLException para tratar los errores con la base de datos.
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        // Se indica el directorio para obtener el formato XML
        String reportSource = "./reports/templates/productosreport.jrxml";
        //Se indica el directorio para almacenar el informe genereado
        String reportDest = "./reports/results/informe.html";
        
        //parametros para crear la cabecera del titulo
        Map<String, Object> params = new HashMap<>();
        params.put("reportTitle", "LISTA DE PRODUCTOS");
        params.put("author", "Diogenes Miaja Perez");
        params.put("startDate", (new java.util.Date()).toString());
        
        try{
            
            //Metodo para obtener la plantilla XML y copilar a formato byte code
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
         
            //Se habilita el driver necesario para realizar la conexion
            Class.forName("org.apache.derby.jdbc.ClientDriver");
         
            //se realiza la conexion con la base de datos
            java.sql.Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            
            //Mediante este metodo se genera el archivo XML con los datos obtenidos de la conesion a la base de datos y parametros
            JasperPrint print = JasperFillManager.fillReport(jasperReport, params, conn);
           
            //Metodo para exportar el XML generado a formato HTML en el directorio indicado
            JasperExportManager.exportReportToHtmlFile(print, reportDest);
            
            //Ejecuta la consulta y la visualiza.
            JasperViewer.viewReport(print);
            
        }catch(JRException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
