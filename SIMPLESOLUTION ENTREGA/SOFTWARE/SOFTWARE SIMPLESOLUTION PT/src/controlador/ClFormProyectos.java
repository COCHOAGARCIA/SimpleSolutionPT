package controlador;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.ClConeccion;

public class ClFormProyectos {
	
	/*SE DECLARAN LAS VARIABLES CON LAS QUE SE TRABAJARAN*/
	private String strNomProyecto;
	private int intIdProyecto;
	
	/*CREAMOS E INSTANCIAMOS EL OBJETO CON DE LA CLASE CONECCION*/
	ClConeccion con = new ClConeccion();
	
	/*SE CREA EL CONSTRUCTOR*/
	public ClFormProyectos() {

	}
	
	public void setNomProyecto(String strNomProyecto) {
		this.strNomProyecto = strNomProyecto;
	}
	
	public void intIdProyecto(int intIdProyecto) {
		this.intIdProyecto = intIdProyecto;
	}
	
	public void setIdProyecto(int intIdProyecto) {
		this.intIdProyecto = intIdProyecto;
	}
	
	/*METODO ENCARGADO DE EJECUTAR EL STORE QUE NO DEVUELVE UNA LISTA DE RESULTADO*/
	public String strlEjecutarStore(boolean boolActualizar) {
		boolean boolEjecutar = false;
		String strMensaje="";
		String strSentencia = "";
		try {
			/*EJECUTAMOS EL STORE PRECEDURE ACTUALIZAR CICLO Y ENVIAMOS POR PARAMETRO EL VALOR DE LAS VARIABLES RECUPERADAS, UTILIZAMOS EL METODO GETID PARA
			 * ENVIAR EL USUARIOQ UE SE AUTENTICO*/
			if(boolActualizar == true) {
				strSentencia = "{call verificarProyecto('" + this.strNomProyecto + "'," + con.getIdUsuario() + "," + this.intIdProyecto  + ")}";
			}else {
				strSentencia = "{call eliminarProyecto (" + this.intIdProyecto + ")}";
			}
			
			boolEjecutar = con.boolEjecutarStoredUpdate(strSentencia);
			if (boolEjecutar == true) {
				strMensaje = "La información se actualizo correctamente";
			}else {
				strMensaje = "La operación no se pudo realizar, se presento un error al actualizar la información";
			}
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, "La operación no se pudo realizar, verifique que no existan datos relacionados con este proyecto", "Error: Se presento un error ejecutando la operación", JOptionPane.ERROR_MESSAGE);
		}
		return strMensaje; /*DEVUELVE EL VALOR OBTENIDO AL EJECUTAR LA SENTENCIA */
	}
	
	/*METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA DEL FORMULARIO*/
	public DefaultTableModel devolverModelo() {
		/*CREACION DE MODELO CON LA DEFINICION DE LAS COLUMNAS*/
		DefaultTableModel modelo = new DefaultTableModel();
		String strProcedure = "{call cargarComboProyecto}";
		modelo.setColumnIdentifiers(new Object[] {"id Proyecto","Nombre del proyecto","Fecha de Creacion"});
		try {
			ArrayList<String[]> array = con.devolverArrayList(strProcedure, 3);
			for(int i=0;i<array.size();i++) {
				modelo.addRow(array.get(i));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SQLException:\n" + e, "Error: Se presento un error ejecutando la operación", JOptionPane.ERROR_MESSAGE);
		}
		return modelo;
	}
}