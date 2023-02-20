package controlador;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.ClConeccion;

public class ClFormRegistro {

	/*SE DECLARAN LAS VARIABLES CON LAS QUE SE TRABAJARAN*/
	private int intCantPruebas,intCantPruebasCorrec,intCiclo,intMetrica,intIdRegistro,idUsuario;
	private String strComentario;
	
	ClConeccion con = new ClConeccion();  /*CREAMOS E INSTANCIAMOS EL OBJETO CON DE LA CLASE CONECCION*/
	
	/*SE CREA EL CONSTRUCTOR*/
	public ClFormRegistro() {
		
	}
	
	/*METODOS QUE FUNCIONAN COMO SETTER, RECIBEN LOS DATOS COMO PARAMETROS Y LOS ASIGNAN A LAS VARIABLES DE CLASE*/
	public void setMetrica(int intMetrica) {
		this.intMetrica = intMetrica;
	}
	
	//SOLO DESCOMENTAR EN CASO DE REALIZAR LOS TEST, YA QUE EL USUARIO SE OBTIENE DESDE LA CLASE CONEXION UNA VEZ SE INICIA SESION
	//public void setIdUsuario(int intIdUsuario) {
		//this.idUsuario = intIdUsuario;
	//}
	
	public void setIdRegistro(int intIdRegistro) {
		this.intIdRegistro = intIdRegistro;
	}
	
	public void setPrueCorrectas(int intPruebasCorrectas) {
		this.intCantPruebasCorrec = intPruebasCorrectas;
	}
	
	public void setCiclo(int intCiclo) {
		this.intCiclo = intCiclo;
	}
	
	public void setCantPruebas(int intCalificacion) {
		this.intCantPruebas = intCalificacion;
	}
	
	public void setComentario(String strComentario) {
		this.strComentario = strComentario;
	}
	
	/*METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA DEL FORMULARIO*/
	public DefaultTableModel devolverModelo() {
		/*CREACION DE MODELO CON LA DEFINICION DE LAS COLUMNAS*/
		DefaultTableModel modelo = new DefaultTableModel();
		String strProcedure = "{call cargarRegistros}";
		modelo.setColumnIdentifiers(new Object[] {"id Reg.","id Pro.","Proyecto","id Ver.","Vers.","id Met.","nom metrica","Id Cicl.","nom Ciclo","Cant de pruebas","Cant de pruebas correctas","Comentarios"});
		try {
			ArrayList<String[]> array = con.devolverArrayList(strProcedure, 12);
			for(int i=0;i<array.size();i++) {
				modelo.addRow(array.get(i));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SQLException:\n" + e, "Error: Se presento un error ejecutando la operación", JOptionPane.ERROR_MESSAGE);
		}
		return modelo;
	}
	
	/*METODO ENCARGADO DE EJECUTAR EL STORE QUE NO DEVUELVE UNA LISTA DE RESULTADO*/
	public String strlEjecutarStore(boolean boolActualizar) {
		boolean boolEjecutar = false;
		String strMensaje="";
		String strSentencia = "";
		idUsuario = con.getIdUsuario();
		try {
			/*EJECUTAMOS EL STORE PRECEDURE ACTUALIZAR CICLO Y ENVIAMOS POR PARAMETRO EL VALOR DE LAS VARIABLES RECUPERADAS, UTILIZAMOS EL METODO GETID PARA
			 * ENVIAR EL USUARIOQ UE SE AUTENTICO*/
			if(boolActualizar == true) {
				strSentencia = "{call actualizarRegistro(" + this.intMetrica + "," + this.intCiclo + "," + this.intCantPruebas + ","
							    + this.intCantPruebasCorrec + ",'" + this.strComentario + "',"  + this.idUsuario +")}";
			}else {
				strSentencia = "{call eliminarRegistro (" + this.intIdRegistro + ")}";
			}
			
			boolEjecutar = con.boolEjecutarStoredUpdate(strSentencia);
			if (boolEjecutar == true) {
				strMensaje = "La información se actualizo correctamente";
			}else {
				strMensaje = "La operación no se pudo realizar, se presento un error al actualizar la información";
			}
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, "La operación no se pudo realizar, verifique que no existan datos relacionados con este ciclo", "Error: Se presento un error ejecutando la operación", JOptionPane.ERROR_MESSAGE);
		}
		return strMensaje; /*DEVUELVE EL VALOR OBTENIDO AL EJECUTAR LA SENTENCIA */
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
	
}
