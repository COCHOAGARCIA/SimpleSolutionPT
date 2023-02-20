package controlador;

/*CLASE ENCARGADA DE ALMACENAR LOS ITEMS DEL COMBOBOX JUNTO CON SU ID, LUEGO DEBE DEVOLVER EL ID SEGUN EL ITEM SOLICITADO*/
public class ClObjetosCombo {
	/*SE CREAN LAS VARIABLES DE CLASE*/
	int id;
	String nombre;
	
	/*SE DECLARA EL CONSTRUCTOR QUE SE ENCARGARA DE ASIGNAR VALOR A LAS VARIABLES DE CLASE CON LO RECIBIDO POR PARAMETRO*/
	public ClObjetosCombo(int id, String nombre) {
		 this.id = id;
		 this.nombre = nombre;
	}
	
	/*METODOS QUE FUNCIONAN COMO GET Y SET, GET DEVUELVE LA INFORMACIÓN Y SET LA RECIBE*/
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/*DEVUELVE EL NOMBRE DEL ITEM ALMACENADO*/
	@Override
	 public String toString() {
		return nombre.toString();
	 }
}