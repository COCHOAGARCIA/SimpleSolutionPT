package controlador;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.ClConeccion;

public class ClFormResumen {
	
	/*CREAMOS E INSTANCIAMOS EL OBJETO CON DE LA CLASE CONECCION*/
	ClConeccion con = new ClConeccion();
	
	public ClFormResumen(){
		
	}
	
	/*METODO ENCARGADO DE CARGAR LOS COMBOBOX, EJECUTARA CON AYUDA DE LA CLASE CONEXION EL METODO LLENAR COMBO, RECIBE POR PARAMETRO EL STORE A EJECUTAR
	 * Y EL JCOMBOBOX DONDE SE DEBE ASIGNAR*/
	@SuppressWarnings("rawtypes")
	public void CargarCombo(JComboBox combo,String strProcedure) {
		try {
			con.boolEjecutarStoredLlenarCombo(strProcedure,combo); /*EJECUTAMOS EL STORE PROCEDURE CON AYUDA DE LA BASE DE DATOS*/
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Se presento un error actualizando la información");
		}
	}
	
	/*METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA DEL FORMULARIO*/
	public DefaultTableModel devolverModelo(String strCondicion) {
		/*CREACION DE MODELO CON LA DEFINICION DE LAS COLUMNAS*/
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(new Object[] {"Nom Proyecto","Version","Nom ciclo","Metrica","Prue Realizadas","Prue Correctas","Porcent Aprobacion"});
		try {
			ArrayList<String[]> array = con.devolverArrayList("SELECT p.nomProyecto,v.nomVersion,cp.nomCiclo,m.nomMetrica,rp.cantPruebas,rp.cantPruebasCorrectas,(CAST(rp.cantPruebasCorrectas AS FLOAT)/CAST(rp.cantPruebas AS FLOAT))*100 AS Porcentaje "
					+ "FROM (((Registro_Pruebas AS rp INNER JOIN Ciclo_Prueba AS cp ON rp.ciclo_pruebaIdCiclo = cp.IdCiclo)"
					+ "INNER JOIN Metricas AS m ON m.idMetrica = rp.metricasIdMetrica) INNER JOIN Versiones AS v"
					+ " ON cp.versionesIdVersion = v.idVersion)INNER JOIN Proyectos AS p ON v.proyectosIdProyecto = p.idProyecto" + strCondicion, 7);
			for(int i=0;i<array.size();i++) {
				modelo.addRow(array.get(i));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SQLException:\n" + e, "Error: Se presento un error ejecutando la operación", JOptionPane.ERROR_MESSAGE);
		}
		return modelo;
	}
		
	
	
}
