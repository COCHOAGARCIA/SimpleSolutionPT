package controlador;

import javax.swing.JOptionPane;

import modelo.ClConeccion;
import vista.FmPanelPrincipal;

/*CLASE QUE SE ENCARGARA DE APLICAR LA LOGICA DE NEGOCIO AL FORMULARIO LOGIN*/
public class ClFormLogin {
	/*SE DECLARAN LAS VARIABLES CON LAS QUE SE TRABAJARAN*/
	private String strUsuario;
	private String strContrasena;
	
	ClConeccion con = new ClConeccion(); /*CREAMOS E INSTANCIAMOS EL OBJETO CON DE LA CLASE CONECCION*/
	
	public ClFormLogin(String strUsuario, String strContrasena) {
		this.strUsuario = strUsuario;
		this.strContrasena = strContrasena;
	}
	
	public boolean boolValidar() {
		try {
			/*SE CREA LA VARIABLE*/
			String strSentencia = "SELECT idUsuario,nombre FROM Usuarios WHERE nomUsuario = '" + this.strUsuario +"' AND pass = '"+ this.strContrasena + "'";
			if(con.boolBuscarUsuario(strSentencia)==true){ /*SE VERIFICA QUE LA EJECUCIÓN DEL METODO BUSCAR USUARIO SE EJECUTE CORRECTAMENTE*/
				FmPanelPrincipal fmPanelPrincipal = new FmPanelPrincipal(); /*SE CREA UN OBJETO Y SE INSTANCIA, DE TIPO FORMULARIO PRINCIPAL*/
				fmPanelPrincipal.setVisible(true); /*SE COLOCA EL FORMULARIO VISIBLE*/
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SQLException:\n" + e, "Error: Se presento un error ejecutando la operación", JOptionPane.ERROR_MESSAGE);
			return false;
		}	
	}
	/*METODO ENCARGADO DE VALIDAR QUE EL USUARIO Y CONTRASEÑA ENVIADOS EN LA BASE DE DATOS SI EXISTAN*/
	
}
