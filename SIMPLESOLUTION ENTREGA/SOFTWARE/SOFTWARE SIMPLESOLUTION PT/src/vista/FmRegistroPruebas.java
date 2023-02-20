package vista;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.ClFormRegistro;
import controlador.ClLimpiarControles;
import controlador.ClObjetosCombo;

import javax.swing.JTextArea;

/*FORMULARIO ENCARGADO DE CREAR LOS REGISTROS DE PRUEBA QUE SE VAN A ASIGNAR A LOS CICLOS, UTILIZAMOS DOS EVENTOS, EL ACTION Y EL ITEM */

public class FmRegistroPruebas extends JFrame implements ActionListener,ItemListener,MouseListener{
	private JPanel fmRegistro;
	private JComboBox cbVersion1,cbMetrica,cbCiclo,cbProyecto1;
	private JTextField txtPrueCorrecta,txtCantPruebas,txtId;
	private JButton btnSalir,btnGuardar,btnLimpiar,btnEliminar;
	private JTable tablaRegistro;
	private JLabel lblTitulo,lblProyecto,lblVersion,lblCiclo,lblMetrica,lblCantPruebas,lblComentario,lblCantPruebCorrectas,lblId;
	private JTextArea txtArea;
	
	String strProcedure = "";
	
	/*CREAMOS UN OBJETO QUE NOS AYUDARA A UTILIZAR TODOS LOS METODOS DE LA CLASE REGISTROS DE PRUEBA*/
	ClFormRegistro registro = new ClFormRegistro();
	/*CREAMOS EL OBJETO QUE NOS PERMITIRA ACCEDER A LA CLASE LIMPIAR*/
	ClLimpiarControles limpiar = new ClLimpiarControles(); 
	
	public FmRegistroPruebas() { /*CLASE QUE SE EJECUTARA INMEDICATAMENTE SE INGRESE AL FORMULARIO*/
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); /*CAMBIAMOS LA FORMA DE CERRAR EL FORMULARIO, POR DEFECTO ESTA PARA CERRAR LA APLICACIÓN
														   Y CON ESTE CAMBIO, SOLO SE CERRARA EL FORMULARIO*/
		setTitle("PANEL DE REGISTRO DE PRUEBAS"); /*ASIGNAMOS UN TITULO AL FORMULARIO*/
		inicializar(); /*INVOCAMOS EL METODO INICIALIZAR, ESTE CARGARA TODOS LOS OBJETOS QUE TIENE EL FORMULARIO, COMO JTEXTBOX, JCOBOBOX, EL PANEL Y ENTRE OTROS*/
		
		/*LLENAMOS LA VARIABLE CON EL NOMBRE DEL STORE PROCEDURE QUE VAMOS A UTILIZAR*/
		cargarCombo(cbProyecto1,"{Call cargarComboProyecto}");
		cargarCombo(cbMetrica,"{Call cargarMetrica}");		
		
	}
	public void inicializar() {
		
		setBounds(100, 100, 824, 660);
		fmRegistro = new JPanel();
		fmRegistro.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(fmRegistro);
		fmRegistro.setLayout(null);
		
		lblTitulo = new JLabel("REGISTRO DE PRUEBAS");
		lblTitulo.setBounds(10, 10, 712, 21);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 17));
		fmRegistro.add(lblTitulo);
		
		lblProyecto = new JLabel("Escoger Proyecto:");
		lblProyecto.setBounds(74, 118, 311, 22);
		fmRegistro.add(lblProyecto);
		
		lblVersion = new JLabel("Escoger la versión Proyecto:");
		lblVersion.setBounds(74, 151, 311, 22);
		fmRegistro.add(lblVersion);
		
		lblCiclo = new JLabel("Escoger el Ciclo:");
		lblCiclo.setBounds(74, 184, 311, 22);
		fmRegistro.add(lblCiclo);
		
		cbCiclo = new JComboBox();
		cbCiclo.setBounds(422, 184, 169, 22);
		fmRegistro.add(cbCiclo);
		cbCiclo.addItemListener(this); /*AGREGAMOS ESTE METODO EL CUAL COLOCARA A ESCUCHAR LA LISTA DESPLEGABLE Y PODAMOS EJECUTAR UNA ACCION*/
		
		lblMetrica = new JLabel("Escoger la metrica:");
		lblMetrica.setBounds(74, 217, 311, 22);
		fmRegistro.add(lblMetrica);
		
		cbMetrica = new JComboBox();
		cbMetrica.setBounds(422, 217, 169, 22);
		fmRegistro.add(cbMetrica);
		
		lblCantPruebas = new JLabel("Ingrese la cantidad de pruebas realizadas(No mayor a 10):");
		lblCantPruebas.setBounds(74, 250, 338, 22);
		fmRegistro.add(lblCantPruebas);
		
		txtCantPruebas = new JTextField();
		txtCantPruebas.setBounds(422, 251, 169, 21);
		fmRegistro.add(txtCantPruebas);
		txtCantPruebas.setColumns(10);
		
		lblComentario = new JLabel("Registros Encontrados");
		lblComentario.setBounds(74, 315, 311, 22);
		fmRegistro.add(lblComentario);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(74, 571, 105, 39);
		fmRegistro.add(btnGuardar);
		btnGuardar.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(568, 571, 105, 39);
		fmRegistro.add(btnSalir);
		btnSalir.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		cbVersion1 = new JComboBox();
		cbVersion1.setEditable(true);
		cbVersion1.setBounds(422, 151, 169, 22);
		fmRegistro.add(cbVersion1);
		cbVersion1.addItemListener(this); /*AGREGAMOS ESTE METODO EL CUAL COLOCARA A ESCUCHAR LA LISTA DESPLEGABLE Y PODAMOS EJECUTAR UNA ACCION*/
		
		cbProyecto1 = new JComboBox();
		cbProyecto1.setBounds(422, 118, 169, 22);
		fmRegistro.add(cbProyecto1);
		cbProyecto1.addItemListener(this); /*AGREGAMOS ESTE METODO EL CUAL COLOCARA A ESCUCHAR LA LISTA DESPLEGABLE Y PODAMOS EJECUTAR UNA ACCION*/
		
		lblCantPruebCorrectas = new JLabel("Ingrese la cantidad de pruebas Correctas(No mayor a 10)");
		lblCantPruebCorrectas.setBounds(74, 282, 338, 22);
		fmRegistro.add(lblCantPruebCorrectas);
		
		txtPrueCorrecta = new JTextField();
		txtPrueCorrecta.setBounds(422, 283, 169, 21);
		txtPrueCorrecta.setColumns(10);
		fmRegistro.add(txtPrueCorrecta);
		
		/*CREACION DE LA TABLA*/
		tablaRegistro = new JTable(registro.devolverModelo()){
			public boolean isCellEditable(int rowIndex,int vColIndex) {
				return false;
			}
		};
		tablaRegistro.addMouseListener(this);
		
		/*CREAMOS UN OBJETO DE TIPO SCROLLPANE Y AGREGAMOS LA TABLA DENTRO DE EL*/
		JScrollPane scroll = new JScrollPane(tablaRegistro);
		scroll.setBounds(10, 387, 788, 173);
		
		/*AGREGAMOS EL OBJETO CREADO AL PANEL*/
		this.getContentPane().add(scroll);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(402, 571, 105, 39);
		fmRegistro.add(btnLimpiar);
		btnLimpiar.addActionListener(this);
		
		scroll.setVisible(true);
		tablaRegistro.setVisible(true);
		
		txtArea = new JTextArea();
		txtArea.setBounds(422, 314, 169, 62);
		txtArea.setLineWrap(true);
		fmRegistro.add(txtArea);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(241, 571, 105, 39);
		fmRegistro.add(btnEliminar);
		btnEliminar.addActionListener(this);
		
		lblId = new JLabel("Id");
		lblId.setBounds(74, 85, 311, 22);
		fmRegistro.add(lblId);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setColumns(10);
		txtId.setBounds(422, 86, 169, 21);
		fmRegistro.add(txtId);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (btnSalir == e.getSource()) {
			this.dispose();
		}
		if(btnLimpiar == e.getSource()) {
			actualizarFormulario();
		}
		if(btnEliminar == e.getSource()){
			String strMensaje = "";
			
			if(!txtId.getText().toString().isEmpty()) {
				int resp = JOptionPane.showConfirmDialog(this, "Desea borrar el registro seleccionado??");
				if (resp == 0) {
					registro.setIdRegistro(Integer.parseInt((txtId.getText())));
					strMensaje = registro.strlEjecutarStore(false);
					JOptionPane.showMessageDialog(this, strMensaje);
					actualizarFormulario();
				}
			}else {
				JOptionPane.showMessageDialog(null, "Debes escoger el registro a eliminar","Registros",JOptionPane.ERROR_MESSAGE);
			}
		}
		/*VERIFICAMOS CON LOS PARAMETROS RECIBIDOS QUE EL BOTON GUARDAR ES EL QUE ESTA ESCUCHANDO EN EL MOMENTO*/
		if(btnGuardar == e.getSource()) {
			/*VERIFICAMOS QUE LOS DATOS DEL FORMULARIO (NECESARIOS) NO ESTEN VACIOS, ESTO INCLUYENDO LOS JCOMBOBOX*/
			if(cbVersion1.getSelectedIndex()==-1 || cbMetrica.getSelectedIndex()==-1 ||
					cbCiclo.getSelectedIndex()==-1 || cbProyecto1.getSelectedIndex()==-1 || txtCantPruebas.getText().toString().isEmpty() ||
					txtPrueCorrecta.getText().toString().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Recuerde Diligenciar todos los campos","Registros",JOptionPane.ERROR_MESSAGE);
			}else {
				try {
					/*CONVERTIMOS LOS DATOS RECIBIDOS EN LOS JTEXTBOX EN ENTEROS PARA VERIFICAR QUE LOS DATOS INGRESADOS SEAN CORRECTOS*/
					int cantPruebas = Integer.parseInt(txtCantPruebas.getText());
					int cantPruebasCorrec = Integer.parseInt(txtPrueCorrecta.getText());
					/*SE VERIFICA QUE LAS PRUEBAS CORRECTAS NO SEA MAYOR A LAS PRUEBAS REALIZADAS, TAMBIEN QUE LOS NUMEROS SEAN NUMEROS ENTRE 0 Y 10*/
					if (cantPruebasCorrec <= cantPruebas && cantPruebasCorrec>= 0 && cantPruebasCorrec <= 10 && cantPruebas >= 0 && cantPruebas <= 10) {
						int resp = JOptionPane.showConfirmDialog(this, "Desea guardar este registro? una vez almacenado no permitira su modificación");
						if (resp == 0) {
							if(txtId.getText().toString().isEmpty()) {
								String strMensaje = "";
								registro.setComentario(txtArea.getText());
								registro.setCantPruebas(cantPruebas);
								registro.setPrueCorrectas(cantPruebasCorrec);
								
								ClObjetosCombo ciclo = (ClObjetosCombo) cbCiclo.getSelectedItem();
								registro.setCiclo(ciclo.getId());
								
								ClObjetosCombo metrica = (ClObjetosCombo) cbMetrica.getSelectedItem();
								registro.setMetrica(metrica.getId());
								strMensaje = registro.strlEjecutarStore(true);
								JOptionPane.showMessageDialog(this, strMensaje);
								actualizarFormulario();
							}else {
								JOptionPane.showMessageDialog(this, "Los registros ya ingresados no pueden ser modificados","Registro",JOptionPane.ERROR_MESSAGE);
							}
						}
					}else {
						JOptionPane.showMessageDialog(this, "Revise que la cantidad de pruebas Correctas o realizadas no sean mayor a 10; \n o la cantidad de pruebas correctas no sea mayor a las pruebas realizadas","Registro de pruebas",JOptionPane.ERROR_MESSAGE);
					}
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(this, "Los campos Cant. Pruebas y Cant. Pruebas Correctas deben de ser numeros positivos","Registro",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		/*ESTE METODO LO QUE HARA ES LLENAR EL JCOMBOBOX CUANDO SE SELECCIONE UN ITEM DEL COMBOBOXPROYECTO*/
		if (cbProyecto1 == e.getSource()) {
			if (e.getStateChange() == ItemEvent.SELECTED) /*CAPTURAMOS EL EVENTO SELECCIONAR*/ {
				ClObjetosCombo proyecto = (ClObjetosCombo) cbProyecto1.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				cbVersion1.removeAllItems(); /*BORRAMOS LOS ELEMENTOS QUE TENGA EL COMBOBOX VERSION*/
				strProcedure = "{call cargarVersiones("+ proyecto.getId() +")}";
				this.cargarCombo(cbVersion1,strProcedure); /*SE EJECUTA EL METODO CARGAR COMBO PARA ACTUALIZAR EL OBJETO CON LA NUEVA SELECCION*/
			}
		}
		if (cbVersion1 == e.getSource()) {
			if (e.getStateChange() == ItemEvent.SELECTED) /*CAPTURAMOS EL EVENTO SELECCIONAR*/ {
				ClObjetosCombo cbVersion = (ClObjetosCombo) cbVersion1.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				cbCiclo.removeAllItems(); /*BORRAMOS LOS ELEMENTOS QUE TENGA EL COMBOBOX VERSION*/
				strProcedure = "{call cargarCiclo("+ cbVersion.getId() + ")}";
				this.cargarCombo(cbCiclo,strProcedure); /*SE EJECUTA EL METODO CARGAR COMBO PARA ACTUALIZAR EL OBJETO CON LA NUEVA SELECCION*/
			}
		}
	}
	
	/*METODO ENCARGADO DE LIMPIAR Y ACTUALIZAR LA TABLA DEL FORMULARIO*/
	public void actualizarFormulario() {
		limpiar.LimpiarControles(fmRegistro); /*EJECUTAMOS EL METODO QUE SE ENCARGARA DE LIMPIAR LOS OBJETOS DEL FORMULARIO*/
		actualizarTabla();
	}
	
	/*METODO ENCARGADO DE ASIGNAR UN MODELO DE DATOS A LA TABLA*/
	public void actualizarTabla() {
		tablaRegistro.setModel(registro.devolverModelo());
	}
	
	/*METODO ENCARGADO DE LLENAR LOS COMBOBOX DEL FORMULARIO*/
	public void cargarCombo(JComboBox combo,String strProcedure) {
		try {
			registro.CargarCombo(combo,strProcedure); /*LLAMA EL METODO CARGARCOMBO DEL OBJETO CREAR, SE ENVIA EL COMBOBOX Y EL STORE PROCEDURE QUE VAMOS A EJECUATR*/
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Se presento un error cargando la información, intentelo nuevamente"); /*CAPTURAMOS LA EXCEPCION EN CASO DE ERROR*/
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(tablaRegistro == e.getSource()) {
			int seleccion = tablaRegistro.rowAtPoint(e.getPoint());
			txtId.setText(String.valueOf(tablaRegistro.getValueAt(seleccion, 0)));
			txtPrueCorrecta.setText(String.valueOf(tablaRegistro.getValueAt(seleccion, 10)));
			txtCantPruebas.setText(String.valueOf(tablaRegistro.getValueAt(seleccion, 9)));
			txtArea.setText(String.valueOf(tablaRegistro.getValueAt(seleccion, 11)));
			
			int idProyecto = Integer.valueOf((String) tablaRegistro.getValueAt(seleccion, 1));
			String strNomProyecto = String.valueOf(tablaRegistro.getValueAt(seleccion, 2));
			
			int intIdVersion = Integer.valueOf((String) tablaRegistro.getValueAt(seleccion, 3));
			String strNomVersion = String.valueOf(tablaRegistro.getValueAt(seleccion, 4));
			
			int intIdMetrica = Integer.valueOf((String) tablaRegistro.getValueAt(seleccion, 5));
			String strNomMetrica = String.valueOf(tablaRegistro.getValueAt(seleccion, 6));
			
			int intIdCiclo = Integer.valueOf((String) tablaRegistro.getValueAt(seleccion, 7));
			String strNomCiclo = String.valueOf(tablaRegistro.getValueAt(seleccion, 8));
			
			for (int i = 0; i < cbProyecto1.getItemCount(); i++) { //RECORREMOS TODO EL OBJETO COMBOBOX CON EL FIN DE BUSCAR LA POSICION DEL ITEM SELECCIONADO EN LA TABLA
				ClObjetosCombo proyecto = (ClObjetosCombo) cbProyecto1.getItemAt(i); //RECUPERAMOS LOS DATOS DEL OBJETO CON LO QUE SE ENCUENTRA EN EL INDICE QUE ESTAMOS RECORRIENDO
				//BUSCAMOS EL INDEX DEL CBVERSION
				if (proyecto.getId() == idProyecto && proyecto.getNombre().equals(strNomProyecto)) { // VALIDAMOS QUE LOS DATOS DEL OBJETO CORRESPONDAN A LOS INDICADOS EN LA TABLA
					cbProyecto1.setSelectedItem(proyecto); //SI EL ITEM SELECCIONADO ES IGUAL O EXISTE EN EL OBJETO, LO AGREGA AL COMBOBOX(ESTO SE HACE GRACIAS AL INDICE)
					break;
				}
			}
			
			for (int i = 0; i < cbVersion1.getItemCount(); i++) { //RECORREMOS TODO EL OBJETO COMBOBOX CON EL FIN DE BUSCAR LA POSICION DEL ITEM SELECCIONADO EN LA TABLA
				ClObjetosCombo version = (ClObjetosCombo) cbVersion1.getItemAt(i); //RECUPERAMOS LOS DATOS DEL OBJETO CON LO QUE SE ENCUENTRA EN EL INDICE QUE ESTAMOS RECORRIENDO
				//BUSCAMOS EL INDEX DEL CBVERSION
				if (version.getId() == intIdVersion && version.getNombre().equals(strNomVersion)) { // VALIDAMOS QUE LOS DATOS DEL OBJETO CORRESPONDAN A LOS INDICADOS EN LA TABLA
					cbVersion1.setSelectedItem(version); //SI EL ITEM SELECCIONADO ES IGUAL O EXISTE EN EL OBJETO, LO AGREGA AL COMBOBOX(ESTO SE HACE GRACIAS AL INDICE)
					break;
				}
			}
			
			for (int i = 0; i < cbMetrica.getItemCount(); i++) { //RECORREMOS TODO EL OBJETO COMBOBOX CON EL FIN DE BUSCAR LA POSICION DEL ITEM SELECCIONADO EN LA TABLA
				ClObjetosCombo metrica = (ClObjetosCombo) cbMetrica.getItemAt(i); //RECUPERAMOS LOS DATOS DEL OBJETO CON LO QUE SE ENCUENTRA EN EL INDICE QUE ESTAMOS RECORRIENDO
				//BUSCAMOS EL INDEX DEL CBVERSION
				if (metrica.getId() == intIdMetrica && metrica.getNombre().equals(strNomMetrica)) { // VALIDAMOS QUE LOS DATOS DEL OBJETO CORRESPONDAN A LOS INDICADOS EN LA TABLA
					cbMetrica.setSelectedItem(metrica); //SI EL ITEM SELECCIONADO ES IGUAL O EXISTE EN EL OBJETO, LO AGREGA AL COMBOBOX(ESTO SE HACE GRACIAS AL INDICE)
					break;
				}
			}
			
			for (int i = 0; i < cbCiclo.getItemCount(); i++) { //RECORREMOS TODO EL OBJETO COMBOBOX CON EL FIN DE BUSCAR LA POSICION DEL ITEM SELECCIONADO EN LA TABLA
				ClObjetosCombo ciclo = (ClObjetosCombo) cbCiclo.getItemAt(i); //RECUPERAMOS LOS DATOS DEL OBJETO CON LO QUE SE ENCUENTRA EN EL INDICE QUE ESTAMOS RECORRIENDO
				//BUSCAMOS EL INDEX DEL CBVERSION
				if (ciclo.getId() == intIdCiclo && ciclo.getNombre().equals(strNomCiclo)) { // VALIDAMOS QUE LOS DATOS DEL OBJETO CORRESPONDAN A LOS INDICADOS EN LA TABLA
					cbCiclo.setSelectedItem(ciclo); //SI EL ITEM SELECCIONADO ES IGUAL O EXISTE EN EL OBJETO, LO AGREGA AL COMBOBOX(ESTO SE HACE GRACIAS AL INDICE)
					break;
				}
			}
		}
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}