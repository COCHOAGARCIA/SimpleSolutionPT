package vista;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

import controlador.ClLimpiarControles;
import controlador.ClObjetosCombo;
import controlador.ClFormVersion;

public class FmVersion extends JFrame implements ActionListener,MouseListener{
	
	private JPanel fmVersion;
	private JTextField txtVersion,txtIdVersion;
	private Button btnSalir,btnGuardar,btnEliminar,btnLimpiar;
	private JComboBox<?> cbProyecto;
	private JTable tablaVersiones;
	private JLabel lblIdVersion,lblTitulo,lblProyecto,lblVersion;
	
	ClFormVersion version = new ClFormVersion(); /*CREAMOS UN OBJETO QUE NOS AYUDARA A UTILIZAR TODOS LOS METODOS DE LA CLASE CREARVERSION*/
	ClLimpiarControles limpiar = new ClLimpiarControles(); /*CREAMOS EL OBJETO QUE NOS PERMITIRA ACCEDER A LA CLASE LIMPIAR*/
	
	
	public FmVersion() { /*CONSTRUCTOR QUE SE EJECUTARA INMEDICATAMENTE SE INGRESE AL FORMULARIO*/
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);/*CAMBIAMOS LA FORMA DE CERRAR EL FORMULARIO, POR DEFECTO ESTA PARA CERRAR LA APLICACIÓN  Y CON ESTE CAMBIO, SOLO SE CERRARA EL FORMULARIO*/
		setTitle("PANEL DE VERSIONES"); /*ASIGNAMOS UN TITULO AL FORMULARIO*/
		
		inicializar(); /*INVOCAMOS EL METODO INICIALIZAR, ESTE CARGARA TODOS LOS OBJETOS QUE TIENE EL FORMULARIO, COMO JTEXTBOX, JCOBOBOX, EL PANEL Y ENTRE OTROS*/
		cargarCombo(); /*LLAMAMOS EL METODO ENCARGADO DE CARGAR EL COMBOBOX AL FORMULARIO*/
	}
	public void inicializar() {
		
		setBounds(100, 100, 564, 405);
		fmVersion = new JPanel();
		fmVersion.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(fmVersion);
		fmVersion.setLayout(null);
		
		lblTitulo = new JLabel("CREACION DE VERSIONES DE UN PROYECTO");
		lblTitulo.setBounds(10, 11, 471, 29);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 17));
		fmVersion.add(lblTitulo);
		
		cbProyecto = new JComboBox();
		cbProyecto.setBounds(183, 97, 169, 22);
		fmVersion.add(cbProyecto);
		
		lblProyecto = new JLabel("Escoger el Proyecto");
		lblProyecto.setBounds(10, 97, 128, 22);
		fmVersion.add(lblProyecto);
		
		lblVersion = new JLabel("Ingrese el nombre de la versión");
		lblVersion.setBounds(10, 130, 163, 22);
		fmVersion.add(lblVersion);
		
		txtVersion = new JTextField();
		txtVersion.setBounds(183, 131, 134, 20);
		fmVersion.add(txtVersion);
		txtVersion.setColumns(10);
		
		btnGuardar = new Button("Guardar");
		btnGuardar.setForeground(new Color(0, 0, 0));
		btnGuardar.setBounds(86, 321, 86, 35);
		fmVersion.add(btnGuardar);
		btnGuardar.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnSalir = new Button("Salir");
		btnSalir.setForeground(new Color(0, 0, 0));
		btnSalir.setBounds(374, 321, 86, 35);
		fmVersion.add(btnSalir);
		btnSalir.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnLimpiar = new Button("Limpiar");
		btnLimpiar.setForeground(new Color(0, 0, 0));
		btnLimpiar.setBounds(278, 321, 86, 35);
		fmVersion.add(btnLimpiar);
		btnLimpiar.addActionListener(this);
		
		/*CREACION DE LA TABLA*/
		tablaVersiones = new JTable(version.devolverModelo()){
			public boolean isCellEditable(int rowIndex,int vColIndex) {
				return false;
			}
		};
		tablaVersiones.addMouseListener(this);
		
		/*CREAMOS UN OBJETO DE TIPO SCROLLPANE Y AGREGAMOS LA TABLA DENTRO DE EL*/
		JScrollPane scroll = new JScrollPane(tablaVersiones);
		/*AGREGAMOS EL OBJETO CREADO AL PANEL*/
		this.getContentPane().add(scroll, null);
		/*DAMOS FORMATO AL SCROLL Y VOLVEMOS LOS OBJETOS VISIBLES*/
		scroll.setBounds(20,162,508,144);
		scroll.setVisible(true);
		tablaVersiones.setVisible(true);
		
		btnEliminar = new Button("Eliminar");
		btnEliminar.setForeground(new Color(0, 0, 0));
		btnEliminar.setBounds(182, 321, 86, 35);
		fmVersion.add(btnEliminar);
		btnEliminar.addActionListener(this);
		
		lblIdVersion = new JLabel("Id de la versión:");
		lblIdVersion.setBounds(10, 65, 163, 22);
		fmVersion.add(lblIdVersion);
		
		txtIdVersion = new JTextField();
		txtIdVersion.setEditable(false);
		txtIdVersion.setColumns(10);
		txtIdVersion.setBounds(183, 66, 134, 20);
		fmVersion.add(txtIdVersion);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/*VERIFICAMOS CON LOS PARAMETROS RECIBIDOS QUE EL BOTON SALIR ES EL QUE ESTA ESCUCHANDO EN EL MOMENTO*/
		if(btnSalir==e.getSource()) {
			this.dispose();
		}
		
		if(btnLimpiar == e.getSource()) {
			actualizarFormulario();
		}
		
		if(btnEliminar == e.getSource()) {
			String strMensaje = "";
			if(!txtIdVersion.getText().toString().isEmpty()) {
				int resp = JOptionPane.showConfirmDialog(this, "Desea borrar el registro seleccionado??");
				if (resp == 0) {
					version.setIdVersion(Integer.parseInt(txtIdVersion.getText()));
					strMensaje = version.strlEjecutarStore(false);
					JOptionPane.showMessageDialog(this, strMensaje);
					actualizarFormulario();
				}
			}else {
				JOptionPane.showMessageDialog(null, "Debes escoger la versión a eliminar","Versiones",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		/*VERIFICAMOS CON LOS PARAMETROS RECIBIDOS QUE EL BOTON GUARDAR ES EL QUE ESTA ESCUCHANDO EN EL MOMENTO*/
		if (btnGuardar == e.getSource()) {
			/*VERIFICAMOS QUE LOS DATOS DEL FORMULARIO (NECESARIOS) NO ESTEN VACIOS, ESTO INCLUYENDO LOS JCOMBOBOX*/
			String strMensaje = "";
			if(cbProyecto.getSelectedItem() == null || txtVersion.getText().toString().isEmpty()) {
				JOptionPane.showInternalMessageDialog(null, "Recuerde diligenciar los campos obligatorios antes de continuar","Version",JOptionPane.ERROR_MESSAGE);
			}else {
				/*CREAMOS EL OBJETO VALIDAR, ESTO NOS AYUDARA A ACCEDER A LOS METODOS DE LA CLASE Y EJECUTAR EL STORE PROCEDURE QUE ESTA ALMACENADO EN ELLA*/
				int intIdVerSion = 0;
				if(!txtIdVersion.getText().toString().isEmpty()) {
					intIdVerSion = Integer.parseInt(txtIdVersion.getText());
				}
				version.setNomVersion(txtVersion.getText());
				version.setIdVersion(intIdVerSion);
				
				ClObjetosCombo idProyecto = (ClObjetosCombo) cbProyecto.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				version.setIdProyecto(idProyecto.getId());
				
				strMensaje = version.strlEjecutarStore(true);
				JOptionPane.showMessageDialog(this, strMensaje);
				actualizarFormulario();
			}
		}
	}
	
	/*METODO ENCARGADO DE LIMPIAR Y ACTUALIZAR LA TABLA DEL FORMULARIO*/
	public void actualizarFormulario() {
		limpiar.LimpiarControles(fmVersion); /*EJECUTAMOS EL METODO QUE SE ENCARGARA DE LIMPIAR LOS OBJETOS DEL FORMULARIO*/
		actualizarTabla();
	}
	
	/*METODO ENCARGADO DE ASIGNAR UN MODELO DE DATOS A LA TABLA*/
	public void actualizarTabla() {
		tablaVersiones.setModel(version.devolverModelo());
	}
	
	//METODO ENCARGADO DE VALIDAR CUANDO EL USUARIO SELECCIONE UN ITEM DE LA LISTA, MOSTRARA LOS DATOS EN EL FORMULARIO
	@Override
	public void mouseClicked(MouseEvent e) {
		//SE VALIDA QUE LA TABLA VERSIONES SEA LA QUE ESTE ESCUCHANDO EL EVENTO
		if(tablaVersiones == e.getSource()) {
			int seleccion = tablaVersiones.rowAtPoint(e.getPoint()); //UTILIZAMOS EL METODO ROWATPOINT PARA OBTENER EL INDICE DE LA FILA DENTRO DE LA TABLA
			txtIdVersion.setText(String.valueOf(tablaVersiones.getValueAt(seleccion, 0))); //CON AYUDA DEL INDICE QUE OBTUVIMOS, ASIGNAMOS EL VALOR AL TEXTBOX CON LO QUE SE ENCUENTRA EN LA COLUMNA
			txtVersion.setText(String.valueOf(tablaVersiones.getValueAt(seleccion, 1)));
			for (int i = 0; i < cbProyecto.getItemCount(); i++) { //RECORREMOS TODO EL OBJETO COMBOBOX CON EL FIN DE BUSCAR LA POSICION DEL ITEM SELECCIONADO EN LA TABLA
			    ClObjetosCombo proyecto = (ClObjetosCombo) cbProyecto.getItemAt(i); //RECUPERAMOS LOS DATOS DEL OBJETO CON LO QUE SE ENCUENTRA EN EL INDICE QUE ESTAMOS RECORRIENDO
			    if (proyecto.getId() == Integer.valueOf((String) tablaVersiones.getValueAt(seleccion, 3)) && // VALIDAMOS QUE LOS DATOS DEL OBJETO CORRESPONDAN A LOS INDICADOS EN LA TABLA
			    	proyecto.getNombre().equals(String.valueOf(tablaVersiones.getValueAt(seleccion, 4)))) {  
			        cbProyecto.setSelectedItem(proyecto); //SI EL ITEM SELECCIONADO ES IGUAL O EXISTE EN EL OBJETO, LO AGREGA AL COMBOBOX(ESTO SE HACE GRACIAS AL INDICE)
			        break;
			    }
			}
		}	
	}

	/*METODO ENCARGADO DE LLENAR LOS COMBOBOX DEL FORMULARIO*/
	public void cargarCombo() {
		try {
			version.CargarCombo(cbProyecto); /*LLAMA EL METODO CARGARCOMBO DEL OBJETO CREAR, SE ENVIA EL COMBOBOX Y EL STORE PROCEDURE QUE VAMOS A EJECUATR*/
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Se presento un error cargando la información, intentelo nuevamente"); /*CAPTURAMOS LA EXCEPCION EN CASO DE ERROR*/
		}
	}
	
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