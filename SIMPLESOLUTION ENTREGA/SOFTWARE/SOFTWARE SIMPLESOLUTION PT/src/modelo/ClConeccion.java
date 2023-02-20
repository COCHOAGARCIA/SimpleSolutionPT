package modelo;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import controlador.ClObjetosCombo;

/*CLASE ENCARGADA DE CONECTAR CON LA BASE DE DATOS, ES LA ENCARGADA DE IMPORTAR LA LIBRERIA DE SQL, AQUI SE EJECUTARA TODO LO RELACIONADO CON LA BD
 YA SEA CREAR,LEER,MODIFICAR Y ELIMINAR LA INFORMACIÓN*/


public class ClConeccion {
	/*sE DECLARAN LAS VARIABLES A UTILIZAR DURANTE LA EJECUCIÓN DE LA CLASE*/
	private String driver;
	private String database;
	private String hostname;
	private String port;
	private String user;
	private String password;
	private String url;
	private static int idUsuario; /*VARIABLE DE TIPO STATIC QUE AYUDARA A GUARDAR EL ID DEL USUARIO QUE INICIA SESIÓN*/
	Connection conn = null;
	
	/*CONSTRUCTOR DE CLASE*/
	public ClConeccion(){
		driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; /*DRIVER CON EL QUE NOS CONECTAREMOS A SQL*/
		database = "simpleSolutionPrueba"; /*NOMBRE DE LA BD A LA CUAL NOS CONECTAREMOS*/
		user = "sa"; /*USUARIO INDISPENSABLE CAMBIAR EL METODO DE AUTENTICACIÓN EN SQL(ESCOGER AUTENTICACION POR WINDOWS Y POR USUARIO)*/
		password = "123456789"; /*CONTRASEÑA DEL USUARIO*/
		port = "1433"; /*PUERTO DE CONECCION*/
		hostname = "localhost"; /*SERVIDOR DONDE NOS VAMOS A CONECTAR*/
		/*CON LA VARIABLE URL VAMOS A UNIR TODOS LOS CAMPOS RECIBIDOS PARA ENVIAR UNA UNICA CADENA DE CONECCION*/
		url="jdbc:sqlserver://" + hostname + ":" + port + ";database=" + database + ";user=" + user + ";password=" + password + ";encrypt=true;"
			+ "trustServerCertificate=true;"
			+ "loginTimeout=30;";
	}
	
	/*METODO PUBLICO ENCARGADO DE CONECTAR CON LA BASE DE DATOS, AGREGAMOS UNA EXCEPCION EN CASO DE QUE SE PRESENTE UN ERROR AL CONECTAR A LA BASE DE DATOS*/
	public Connection conectarSQL() {		
		try {
			Class.forName(driver); /*SE CARGA EL DRIVER*/
			conn = DriverManager.getConnection(url); /*ASIGNAMOS AL OBJETO CONN LA CONEXION A LA BASE DE DATOS(GRACIAS AL DRIVER Y A LA CADEN DE CONECCIÓN)*/
		}catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showConfirmDialog(null, "Se presento el error" + e.toString(),"Conexion a base de datos",JOptionPane.ERROR_MESSAGE);
		}
		return conn; /*RETORNAMOS EL VALOR*/
	}
	
	/*METODO ENCARGADO DE CERRAR LA CONEXION CON LA BD, ESTO NOS AYUDA A TERMINAR LAS INSTANCIAS Y DEJAR UNA CONEXION INTERNA ACTIVA
	 UTILIZAMOS LA MISMA METODOLOGIA, UNA CAPTURA DE EXCEPCIÓN PARA CONTROLAR LOS ERRORES*/
	public void CerrarBD() {
		try {
			conn.close();
		} catch(Exception ex) {
			JOptionPane.showConfirmDialog(null, "Se presento el error" + ex.toString(),"Conexion a base de datos",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/*METODO ENCARGADO DE VALIDAR SI EL USUARIO Y CONTRASEÑA INDICANDO EN EL LOGIN SE ENCUENTRAN CREADOS EN LA BASE DE DATOS*/
	public boolean boolBuscarUsuario(String strSentencia) {
		
		boolean boolEjecucion = false;
		
		try {
			conectarSQL(); /*INVOCA EL METODO CONECTAR*/
			/*PROCEDEMOS A EJECUTAR UNA SENTENCIA ESTATICA GRACIAS AL OBJETO STATEMENT*/
			Statement s = conectarSQL().createStatement();
			ResultSet rs = s.executeQuery(strSentencia);
			/*SE VERIFICA QUE LA SENTENCIA RETORNE ALGUN VALOR, UTILIZAMOS EL METODO NEXT PARA RECORRER EL RESULTSET DEVUELTO*/
			if(rs.next()) {
				idUsuario = rs.findColumn("idUsuario"); /*ALMACENE LA INFORMACIÓN DEVUELTA EN LA COLUMNA IDUSUARIO*/
				boolEjecucion = true;
			}
			s.close();
			rs.close(); /*CIERRE EL RESULTSET*/
		}catch (Exception e) {
			JOptionPane.showConfirmDialog(null,"se presento el error " + e.toString(),"Conexion a base de datos",JOptionPane.ERROR_MESSAGE);
		} 
		finally {
			CerrarBD();/*CIERRE LA CONEXION A BD*/
		}
		return boolEjecucion;
	}
	
	/*METODO QUE SERVIRA COMO SETTER PARA ENVIAR EL ID DE USUARIO RECUPERADO*/
	public int getIdUsuario() {
		return idUsuario;
	}

	/*METODO ENCARGADO DE EJECUTAR LOS PROCEDURE QUE NO RETORNEN VALORES (UPDATE, INSERT Y DELETE)*/
	public boolean boolEjecutarStoredUpdate(String strSentencia) {
		boolean EjecutarOperacion = false;
		try {
			conectarSQL();
			CallableStatement ejecutarStored = conn.prepareCall(strSentencia); /*CREAMOS UN METODO QUE NOS AYUDARA A LLAMAR AL PRCEDIMIENTO ALMACENADO*/
			int operacion = ejecutarStored.executeUpdate(); /*EJECUTAMOS EL STORE PROCEDURE*/
			if (operacion == 1) {
				/*SI LA OPERACIÓN SE EJECUTO CORRECTAMENTE, ASIGNAMOS UN TRUE A LA VARIABLE A DEVOLVER*/
				EjecutarOperacion = true;
			}		
			ejecutarStored.close();
		}catch (Exception e){ /*CAPTURAMOS LA EXCEPCION EN CASO DE ERROR*/
			JOptionPane.showConfirmDialog(null,"La operación no se pudo realizar debido a problemas con los datos ingresados","Actualizando la información",JOptionPane.ERROR_MESSAGE);
		}finally {
			CerrarBD(); /*CIERRE LA BD AL EJECUTARSE TODO EL BLOQUE DEL CODIGO*/
		}
		return EjecutarOperacion;
	}

	public ArrayList<String[]> devolverArrayList(String strSentencia,int intColumnas) {
		ArrayList<String[]> tabla = new ArrayList<String[]>(); 
		ResultSet datos = null;
		String Datos[] = new String[intColumnas];
		try {
			conectarSQL(); /*CONECTAMOS LA BASE DE DATOS*/
			CallableStatement ejecutarStored = conn.prepareCall(strSentencia); /*LLAMAMOS AL STORE PROCEDURE QUE VAMOS A EJECUTAR(LO RECIBIMOS COMO PARAMETRO)*/
			datos = ejecutarStored.executeQuery();
			while(datos.next()) {
				Datos = new String[intColumnas];
				for(int i=0;i<intColumnas;++i) {
					Datos[i] = datos.getObject(i+1).toString();
				}
				tabla.add(Datos);
			}
			ejecutarStored.close();
			datos.close();
		}catch(Exception e) {
			JOptionPane.showConfirmDialog(null,"se presento el error " + e.toString(),"Actualizando la información",JOptionPane.ERROR_MESSAGE);
		}finally {
			CerrarBD();
		}
		return tabla;
	}
	
	/*METODO QUE SE ENCARGARA DE EJECUTAR LOS STORE PROCEDURE QUE RETORNEN VALORES Y LOS COMBOBOX NECESARIOS*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean boolEjecutarStoredLlenarCombo(String strSentencia,JComboBox combo) {
		boolean EjecutarOperacion = false;
		try {
			conectarSQL(); /*CONECTAMOS LA BASE DE DATOS*/
			CallableStatement ejecutarStored = conn.prepareCall(strSentencia); /*LLAMAMOS AL STORE PROCEDURE QUE VAMOS A EJECUTAR(LO RECIBIMOS COMO PARAMETRO)*/
			ResultSet rs = ejecutarStored.executeQuery(); /*EJECUTAMOS EL STORE PROCEDURE Y LO ALMACENAMOS EN UN OBJETO DE TIPO RESULTSET*/
			while(rs.next()) { /*RECORREMOS EL RESULTSET CON EL METODO NEXT*/ 
				combo.addItem(new ClObjetosCombo( /*CREAMOS UN OBJETO DONDE VAMOS A ALMACENAR LOS DATOS RECUPERADOS DE LA SENTENCIA Y A SU VEZ, LOS AGREGAMOS AL COMBOBOX RECIBIDO POR PARAMETRO*/
						Integer.parseInt(rs.getString(1)),rs.getString(2)
						));
			}
			combo.setSelectedIndex(-1);
			ejecutarStored.close();
			rs.close(); 
			EjecutarOperacion=true;
		}catch (Exception e){
			JOptionPane.showConfirmDialog(null,"se presento el error " + e.toString(),"Actualizando la información",JOptionPane.ERROR_MESSAGE);
		}finally {
			CerrarBD();
		}
		return EjecutarOperacion;
	}
	
}