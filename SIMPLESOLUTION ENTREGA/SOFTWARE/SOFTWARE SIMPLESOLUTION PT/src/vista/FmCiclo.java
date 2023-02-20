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

import controlador.ClFormCiclo;
import controlador.ClLimpiarControles;
import controlador.ClObjetosCombo;

/*FORMULARIO ENCARGADO DE CREAR LOS CICLOS DE PRUEBA QUE SE VAN A ASIGNAR A LAS VERSIONES, UTILIZAMOS DOS EVENTOS, EL ACTION Y EL ITEM */

public class FmCiclo extends JFrame implements ActionListener,ItemListener,MouseListener{
	
	private JPanel fmCiclos;
	private JTextField txtNomCiclo,txtId;
	private JButton btnSalir,btnGuardar,btnEliminar,btnLimpiar;
	private JComboBox cbProyecto,cbVersion;
	private JTable tablaCiclos;
	private JLabel lblId,lblTitulo,lblProyecto,lblVersion,lblNomCiclo;
	
	String strProcedure = "{Call cargarComboProyecto}";  /*LLENAMOS LA VARIABLE CON EL NOMBRE DEL STORE PROCEDURE QUE VAMOS A UTILIZAR*/
	
	/*CREAMOS EL OBJETO QUE NOS PERMITIRA ACCEDER A LA CLASE LIMPIAR*/
	ClLimpiarControles limpiar = new ClLimpiarControles(); 
	/*CREAMOS UN OBJETO QUE NOS AYUDARA A UTILIZAR TODOS LOS METODOS DE LA CLASE CREARCICLO*/
	ClFormCiclo ciclo = new ClFormCiclo();
	
	public FmCiclo() { /*CONSTRUCTOR QUE SE EJECUTARA INMEDICATAMENTE SE INGRESE AL FORMULARIO*/
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); /*CAMBIAMOS LA FORMA DE CERRAR EL FORMULARIO, POR DEFECTO ESTA PARA CERRAR LA APLICACIÓN
														   Y CON ESTE CAMBIO, SOLO SE CERRARA EL FORMULARIO*/
		setTitle("CREACION DE CICLO"); /*ASIGNAMOS UN TITULO AL FORMULARIO*/
	
		inicializar(); /*INVOCAMOS EL METODO INICIALIZAR, ESTE CARGARA TODOS LOS OBJETOS QUE TIENE EL FORMULARIO, COMO JTEXTBOX, JCOBOBOX, EL PANEL Y ENTRE OTROS*/
		cargarCombo(cbProyecto); /*LLAMAMOS EL METODO ENCARGADO DE CARGAR EL COMBOBOX AL FORMULARIO*/
		
	}
	
	public void inicializar() {
		setBounds(0, -27, 568, 436);
		fmCiclos = new JPanel();
		fmCiclos.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(fmCiclos);
		fmCiclos.setLayout(null);
		
		lblTitulo = new JLabel("CREACION DE CICLOS");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTitulo.setBounds(10, 11, 518, 29);
		fmCiclos.add(lblTitulo);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(20, 348, 108, 41);
		fmCiclos.add(btnGuardar);
		btnGuardar.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(420, 348, 108, 41);
		fmCiclos.add(btnSalir);
		btnSalir.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		lblProyecto = new JLabel("Escoger Proyecto");
		lblProyecto.setBounds(20, 113, 96, 29);
		fmCiclos.add(lblProyecto);
		
		cbProyecto = new JComboBox();
		cbProyecto.setBounds(154, 116, 217, 22);
		fmCiclos.add(cbProyecto);
		cbProyecto.addItemListener(this); /*AGREGAMOS ESTE METODO EL CUAL COLOCARA A ESCUCHAR LA LISTA DESPLEGABLE Y PODAMOS EJECUTAR UNA ACCION*/
		
		lblVersion = new JLabel("Escoger Version");
		lblVersion.setBounds(20, 153, 96, 29);
		fmCiclos.add(lblVersion);
		
		cbVersion = new JComboBox();
		cbVersion.setBounds(154, 156, 217, 22);
		fmCiclos.add(cbVersion);
		
		lblNomCiclo = new JLabel("Digite el nombre del ciclo");
		lblNomCiclo.setBounds(20, 193, 124, 29);
		fmCiclos.add(lblNomCiclo);
		
		txtNomCiclo = new JTextField();
		txtNomCiclo.setBounds(154, 193, 183, 29);
		fmCiclos.add(txtNomCiclo);
		txtNomCiclo.setColumns(10);
		
		/*CREACION DE LA TABLA*/
		tablaCiclos = new JTable(ciclo.devolverModelo()) {
			public boolean isCellEditable(int rowIndex,int vColIndex) {
				return false;
			}
		};
		tablaCiclos.addMouseListener(this);
		
		/*CREAMOS UN OBJETO DE TIPO SCROLLPANE Y AGREGAMOS LA TABLA DENTRO DE EL*/
		JScrollPane scroll = new JScrollPane(tablaCiclos);
		
		/*AGREGAMOS EL OBJETO CREADO AL PANEL*/
		this.getContentPane().add(scroll, null);
		
		/*DAMOS FORMATO AL SCROLL Y VOLVEMOS LOS OBJETOS VISIBLES*/
		scroll.setBounds(20,232,508,113);
		scroll.setVisible(true);
		tablaCiclos.setVisible(true);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(154, 348, 108, 41);
		fmCiclos.add(btnEliminar);
		btnEliminar.addActionListener(this);
		
		lblId = new JLabel("Id");
		lblId.setBounds(20, 81, 124, 29);
		fmCiclos.add(lblId);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setColumns(10);
		txtId.setBounds(154, 81, 183, 29);
		fmCiclos.add(txtId);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(289, 348, 108, 41);
		fmCiclos.add(btnLimpiar);
		btnLimpiar.addActionListener(this);
		
	}
	
	/*METODO ENCARGADO DE LIMPIAR Y ACTUALIZAR LA TABLA DEL FORMULARIO*/
	public void actualizarFormulario() {
		limpiar.LimpiarControles(fmCiclos); /*EJECUTAMOS EL METODO QUE SE ENCARGARA DE LIMPIAR LOS OBJETOS DEL FORMULARIO*/
		actualizarTabla();
	}
	
	/*METODO ENCARGADO DE ASIGNAR UN MODELO DE DATOS A LA TABLA*/
	public void actualizarTabla() {
		tablaCiclos.setModel(ciclo.devolverModelo());
	}
	
	/*METODO ENCARGADO DE LLENAR LOS COMBOBOX DEL FORMULARIO*/
	public void cargarCombo(JComboBox combo) {
		try {
			ciclo.CargarCombo(combo,strProcedure); /*LLAMA EL METODO CARGARCOMBO DEL OBJETO CREAR, SE ENVIA EL COMBOBOX Y EL STORE PROCEDURE QUE VAMOS A EJECUATR*/
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Se presento un error cargando la información, intentelo nuevamente"); /*CAPTURAMOS LA EXCEPCION EN CASO DE ERROR*/
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (btnSalir == e.getSource()) {
			this.dispose();
		}
		if(btnLimpiar == e.getSource()) {
			actualizarFormulario();
		}
		if(btnEliminar == e.getSource()) {
			String strMensaje = "";
			if(!txtId.getText().toString().isEmpty()) {
				int resp = JOptionPane.showConfirmDialog(this, "Desea borrar el registro seleccionado??");
				if (resp == 0) {
					ciclo.setIdCiclo(Integer.parseInt(txtId.getText()));
					strMensaje = ciclo.strlEjecutarStore(false);
					JOptionPane.showMessageDialog(this, strMensaje);
					actualizarFormulario();
				}
			}else {
				JOptionPane.showMessageDialog(null, "Debes escoger el cliclo a eliminar","Ciclo",JOptionPane.ERROR_MESSAGE);
			}
		}
		/*VERIFICAMOS CON LOS PARAMETROS RECIBIDOS QUE EL BOTON GUARDAR ES EL QUE ESTA ESCUCHANDO EN EL MOMENTO*/
		if (btnGuardar == e.getSource()) {
			/*VERIFICAMOS QUE LOS DATOS DEL FORMULARIO (NECESARIOS) NO ESTEN VACIOS, ESTO INCLUYENDO LOS JCOMBOBOX*/
			String strMensaje = "";
			if(cbVersion.getSelectedIndex()==-1 || cbProyecto.getSelectedIndex()==-1 || 
			   txtNomCiclo.getText().toString().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Se encontraron datos obligatorios vacios");
			}else {
				/*CREAMOS EL OBJETO VALIDAR, ESTO NOS AYUDARA A ACCEDER A LOS METODOS DE LA CLASE Y EJECUTAR EL STORE PROCEDURE QUE ESTA ALMACENADO EN ELLA*/
				int intIdCiclo = 0;
				if(!txtId.getText().toString().isEmpty()) {
					intIdCiclo = Integer.parseInt(txtId.getText());
				}
				ClObjetosCombo version = (ClObjetosCombo) cbVersion.getSelectedItem();/*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				ciclo.setidVersion(version.getId()); /*CON AYUDA DEL METODO GETID VAMOS A RECUPERAR EL ID DEL ITEM SELECCIONADO EN LA LISTA*/
				ciclo.setIdCiclo(intIdCiclo);
				ciclo.setNomCiclo(txtNomCiclo.getText());
				
				strMensaje = ciclo.strlEjecutarStore(true);
				JOptionPane.showMessageDialog(this, strMensaje);
				actualizarFormulario();
			}
		}
		
	}
	/*CREAMOS EL METODO QUE SE ENCARGARA DE ESCUCHAR CUANDO SE CAMBIE UN ELEMENTO DE LA LISTA*/
	@Override
	/*ESTE METODO LO QUE HARA ES LLENAR EL JCOMBOBOX CUANDO SE SELECCIONE UN ITEM DEL COMBOBOXPROYECTO*/
	public void itemStateChanged(ItemEvent e) {
		if (cbProyecto == e.getSource()) {
			if (e.getStateChange() == ItemEvent.SELECTED) /*CAPTURAMOS EL EVENTO SELECCIONAR*/ {
				ClObjetosCombo version = (ClObjetosCombo) cbProyecto.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				cbVersion.removeAllItems(); /*BORRAMOS LOS ELEMENTOS QUE TENGA EL COMBOBOX VERSION*/
				strProcedure = "{call cargarVersiones("+ version.getId() +")}";
				this.cargarCombo(cbVersion); /*SE EJECUTA EL METODO CARGAR COMBO PARA ACTUALIZAR EL OBJETO CON LA NUEVA SELECCION*/
			}
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int seleccion = tablaCiclos.rowAtPoint(e.getPoint());
		txtId.setText(String.valueOf(tablaCiclos.getValueAt(seleccion, 0)));
		txtNomCiclo.setText(String.valueOf(tablaCiclos.getValueAt(seleccion, 1)));
		
		int idProyecto = Integer.valueOf((String) tablaCiclos.getValueAt(seleccion, 5));
		String strNomProyecto = String.valueOf(tablaCiclos.getValueAt(seleccion, 6));
		int intIdVersion = Integer.valueOf((String) tablaCiclos.getValueAt(seleccion, 3));
		String strNomVersion = String.valueOf(tablaCiclos.getValueAt(seleccion, 4));

		for (int i = 0; i < cbProyecto.getItemCount(); i++) { //RECORREMOS TODO EL OBJETO COMBOBOX CON EL FIN DE BUSCAR LA POSICION DEL ITEM SELECCIONADO EN LA TABLA
			ClObjetosCombo proyecto = (ClObjetosCombo) cbProyecto.getItemAt(i); //RECUPERAMOS LOS DATOS DEL OBJETO CON LO QUE SE ENCUENTRA EN EL INDICE QUE ESTAMOS RECORRIENDO
			//BUSCAMOS EL INDEX DEL CBPROYECTO
			if (proyecto.getId() == idProyecto && proyecto.getNombre().equals(strNomProyecto)) { // VALIDAMOS QUE LOS DATOS DEL OBJETO CORRESPONDAN A LOS INDICADOS EN LA TABLA
				cbProyecto.setSelectedItem(proyecto); //SI EL ITEM SELECCIONADO ES IGUAL O EXISTE EN EL OBJETO, LO AGREGA AL COMBOBOX(ESTO SE HACE GRACIAS AL INDICE)
			}
		}
		
		for (int i = 0; i < cbVersion.getItemCount(); i++) { //RECORREMOS TODO EL OBJETO COMBOBOX CON EL FIN DE BUSCAR LA POSICION DEL ITEM SELECCIONADO EN LA TABLA
			ClObjetosCombo version = (ClObjetosCombo) cbVersion.getItemAt(i); //RECUPERAMOS LOS DATOS DEL OBJETO CON LO QUE SE ENCUENTRA EN EL INDICE QUE ESTAMOS RECORRIENDO
			//BUSCAMOS EL INDEX DEL CBVERSION
			if (version.getId() == intIdVersion && version.getNombre().equals(strNomVersion)) { // VALIDAMOS QUE LOS DATOS DEL OBJETO CORRESPONDAN A LOS INDICADOS EN LA TABLA
				cbVersion.setSelectedItem(version); //SI EL ITEM SELECCIONADO ES IGUAL O EXISTE EN EL OBJETO, LO AGREGA AL COMBOBOX(ESTO SE HACE GRACIAS AL INDICE)
				break;
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