package controlador;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.ClConeccion;

/*CLASE QUE SE ENCARGARA DE APLICAR LA LOGICA DE NEGOCIO AL FORMULARIO CREAR VERSIONES*/
public class ClFormVersion {
	
	/*SE DECLARAN LAS VARIABLES CON LAS QUE SE TRABAJARAN*/
	private String strVersion;
	private int intIdVersion,intIdProyecto;
	
	ClConeccion con = new ClConeccion(); /*CREAMOS E INSTANCIAMOS EL OBJETO CON DE LA CLASE CONECCION*/
	
	/*SE CREA EL CONSTRUCTOR*/
	public ClFormVersion(){
		
	}
	
	/*DECLARACION DE METODOS SETER ENCARGADOS DE RECIBIR LA INFORMACIÓN DESDE EL FORMULARIO*/
	public void setIdProyecto(int intIdProyecto) {
		this.intIdProyecto = intIdProyecto;
	}
	
	public void setIdVersion(int intIdVersion) {
		this.intIdVersion = intIdVersion;
	}

	public void setNomVersion(String strNomVersion) {
		this.strVersion = strNomVersion;
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
				strSentencia = "{call actualizarVersion(" + this.intIdProyecto + ",'" + this.strVersion + "'," + con.getIdUsuario()  +","+ this.intIdVersion + ")}";
			}else {
				strSentencia = "{call eliminarVersion (" + this.intIdVersion + ")}";
			}
			
			boolEjecutar = con.boolEjecutarStoredUpdate(strSentencia);
			if (boolEjecutar == true) {
				strMensaje = "La información se actualizo correctamente";
			}else {
				strMensaje = "La operación no se pudo realizar, se presento un error al actualizar la información";
			}
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, "La operación no se pudo realizar, verifique que no existan datos relacionados con esta versión", "Error: Se presento un error ejecutando la operación", JOptionPane.ERROR_MESSAGE);
		}
		return strMensaje; /*DEVUELVE EL VALOR OBTENIDO AL EJECUTAR LA SENTENCIA */
	}

	/*METODO ENCARGADO DE CARGAR LOS DATOS A LA TABLA DEL FORMULARIO*/
	public DefaultTableModel devolverModelo() {
		/*CREACION DE MODELO CON LA DEFINICION DE LAS COLUMNAS*/
		DefaultTableModel modelo = new DefaultTableModel();
		String strProcedure = "{call cargarVersionesTabla}";
		modelo.setColumnIdentifiers(new Object[] {"id Versión","Nombre de la Versión","Fecha de Creacion","Id del Proyecto","Nombre Proyecto"});
		try {
			ArrayList<String[]> array = con.devolverArrayList(strProcedure, 5);
			for(int i=0;i<array.size();i++) {
				modelo.addRow(array.get(i));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SQLException:\n" + e, "Error: Se presento un error ejecutando la operación", JOptionPane.ERROR_MESSAGE);
		}
		return modelo;
	}
	
	/*METODO ENCARGADO DE CARGAR LOS COMBOBOX, EJECUTARA CON AYUDA DE LA CLASE CONEXION EL METODO LLENAR COMBO, RECIBE POR PARAMETRO EL JCOMBOBOX DONDE SE DEBE ASIGNAR*/
	public void CargarCombo(JComboBox<?> combo) {
		try {
			con.boolEjecutarStoredLlenarCombo("{Call cargarComboProyecto}",combo); /*EJECUTAMOS EL STORE PROCEDURE CON AYUDA DE LA BASE DE DATOS*/
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Se presento un error actualizando la información");
		}
	}
	
}
