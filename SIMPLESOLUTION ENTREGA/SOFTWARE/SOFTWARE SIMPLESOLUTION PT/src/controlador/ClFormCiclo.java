package controlador;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.ClConeccion;

/*CLASE QUE SE ENCARGARA DE APLICAR LA LOGICA DE NEGOCIO AL FORMULARIO CREAR CICLO*/
public class ClFormCiclo {
	/*SE DECLARAN LAS VARIABLES CON LAS QUE SE TRABAJARAN*/
	private int idVersion;
	private String strCiclo;
	private int intIdCiclo;
	
	/*CREAMOS E INSTANCIAMOS EL OBJETO CON DE LA CLASE CONECCION*/
	ClConeccion con = new ClConeccion();
	
	/*SE CREA EL CONSTRUCTOR*/
	public ClFormCiclo() {

	}
	
	/*METODO ENCARGADO DE RECIBIR EL VALOR DEL ID Y ASIGNARLO A LA VARIABLE VERSION*/
	public void setidVersion(int idVersion) {
		this.idVersion = idVersion;
	}
	
	public void setIdCiclo(int intIdCiclo) {
		this.intIdCiclo = intIdCiclo;
	}
	
	/*METODO ENCARGADO DE RECIBIR EL VALOR DEL NOMBRE DEL CICLO Y ASIGNARLO A LA VARIABLE CICLO*/
	public void setNomCiclo(String strCiclo) {
		this.strCiclo = strCiclo;
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
	public DefaultTableModel devolverModelo() {
		/*CREACION DE MODELO CON LA DEFINICION DE LAS COLUMNAS*/
		DefaultTableModel modelo = new DefaultTableModel();
		String strProcedure = "{call cargarCicloTabla}";
		modelo.setColumnIdentifiers(new Object[] {"id Ciclo","Nombre del Ciclo","Fecha de Creacion","Id de la Version","Versión","Id proyecto","Nomb.Proyecto"});
		try {
			ArrayList<String[]> array = con.devolverArrayList(strProcedure, 7);
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
		try {
			/*EJECUTAMOS EL STORE PRECEDURE ACTUALIZAR CICLO Y ENVIAMOS POR PARAMETRO EL VALOR DE LAS VARIABLES RECUPERADAS, UTILIZAMOS EL METODO GETID PARA
			 * ENVIAR EL USUARIOQ UE SE AUTENTICO*/
			if(boolActualizar == true) {
				strSentencia = "{call actualizarCiclo(" + this.idVersion +",'" + this.strCiclo + "'," + con.getIdUsuario() + "," + this.intIdCiclo + ")}";
			}else {
				strSentencia = "{call eliminarCiclo (" + this.intIdCiclo + ")}";
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
	
}
