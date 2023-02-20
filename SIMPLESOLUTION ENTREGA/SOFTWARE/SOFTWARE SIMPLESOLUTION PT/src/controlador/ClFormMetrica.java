package controlador;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.ClConeccion;

public class ClFormMetrica {
	/*SE DECLARAN LAS VARIABLES CON LAS QUE SE TRABAJARAN*/
	private String strMetrica;
	private int intIdMetrica;
	
	ClConeccion con = new ClConeccion(); /*CREAMOS E INSTANCIAMOS EL OBJETO CON DE LA CLASE CONECCION*/
	
	/*SE CREA EL CONSTRUCTOR*/
	public ClFormMetrica(){
		
	}
	
	/*METODO ENCARGADO DE RECIBIR EL VALOR DEL NOMBRE DE LA METRICA Y ASIGNARLO A LA VARIABLE NOMMETRICA*/
	public void setNomMetrica(String strMetrica) {
		this.strMetrica = strMetrica;
	}
	
	public void setIdMetrica(int intIdMetrica) {
		this.intIdMetrica = intIdMetrica;
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
				strSentencia = "{call actualizarMetrica('" + this.strMetrica + "'," + con.getIdUsuario()+"," + this.intIdMetrica + ")}";
			}else {
				strSentencia = "{call eliminarMetrica (" + this.intIdMetrica + ")}";
			}
			
			boolEjecutar = con.boolEjecutarStoredUpdate(strSentencia);
			if (boolEjecutar == true) {
				strMensaje = "La información se actualizo correctamente";
			}else {
				strMensaje = "La operación no se pudo realizar, se presento un error al actualizar la información";
			}
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, "La operación no se pudo realizar, verifique que no existan datos relacionados con esta metrica", "Error: Se presento un error ejecutando la operación", JOptionPane.ERROR_MESSAGE);
		}
		return strMensaje; /*DEVUELVE EL VALOR OBTENIDO AL EJECUTAR LA SENTENCIA */
	}
	
	/*METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA DEL FORMULARIO*/
	public DefaultTableModel devolverModelo() {
		/*CREACION DE MODELO CON LA DEFINICION DE LAS COLUMNAS*/
		DefaultTableModel modelo = new DefaultTableModel();
		String strProcedure = "{call cargarMetrica}";
		modelo.setColumnIdentifiers(new Object[] {"id Metrica","Nombre de la Metrica"});
		try {
			ArrayList<String[]> array = con.devolverArrayList(strProcedure, 2);
			for(int i=0;i<array.size();i++) {
				modelo.addRow(array.get(i));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SQLException:\n" + e, "Error: Se presento un error ejecutando la operación", JOptionPane.ERROR_MESSAGE);
		}
		return modelo;
	}
	
}
