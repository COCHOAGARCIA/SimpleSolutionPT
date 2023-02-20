package vista;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import controlador.ClFormResumen;
import controlador.ClLimpiarControles;
import controlador.ClObjetosCombo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class FmInforme extends JFrame implements ActionListener,ItemListener {

	private JPanel formInforme;
	private JLabel lblTitulo,lblProyecto,lblVersion,lblCiclo;
	private JTable tablaResumen;
	private JButton btnSalir,btnLimpiar,btnBuscar;
	private JComboBox cbCiclo,cbProyecto,cbVersion;
	

	/*CREAMOS EL OBJETO QUE NOS PERMITIRA ACCEDER A LA CLASE LIMPIAR*/
	ClLimpiarControles limpiar = new ClLimpiarControles(); 
	
	ClFormResumen resumen = new ClFormResumen();
	
	String strProcedure = "{Call cargarComboProyecto}";  /*LLENAMOS LA VARIABLE CON EL NOMBRE DEL STORE PROCEDURE QUE VAMOS A UTILIZAR*/
	String strCondicion = "";
	
	public FmInforme() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("FORMULARIO REPORTES");
		
		inicializar();
		cargarCombo(cbProyecto);
		
	}
	
	@SuppressWarnings("serial")
	public void inicializar(){
		setBounds(100, 100, 919, 580);
		formInforme = new JPanel();
		formInforme.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(formInforme);
		formInforme.setLayout(null);
		
		lblTitulo = new JLabel("FORMULARIO DE RESUMEN PARA LOS PROYECTOS ");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 11, 796, 34);
		formInforme.add(lblTitulo);
		
		/*CREACION DE LA TABLA*/
		tablaResumen = new JTable(resumen.devolverModelo("")){
			public boolean isCellEditable(int rowIndex,int vColIndex) {
				return false;
			}
		};
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(826, 63, 74, 22);
		formInforme.add(btnBuscar);
		btnBuscar.addActionListener(this);
		
		/*CREAMOS UN OBJETO DE TIPO SCROLLPANE Y AGREGAMOS LA TABLA DENTRO DE EL*/
		JScrollPane scroll = new JScrollPane(tablaResumen);
		
		/*AGREGAMOS EL OBJETO CREADO AL PANEL*/
		this.getContentPane().add(scroll, null);
		
		/*DAMOS FORMATO AL SCROLL Y VOLVEMOS LOS OBJETOS VISIBLES*/
		scroll.setBounds(10,97,883,375);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(360, 493, 99, 37);
		formInforme.add(btnLimpiar);
		btnLimpiar.addActionListener(this);
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(469, 493, 99, 37);
		formInforme.add(btnSalir);
		btnSalir.addActionListener(this);
		
		lblProyecto = new JLabel("Escoger Proyecto");
		lblProyecto.setBounds(20, 56, 124, 29);
		formInforme.add(lblProyecto);
		
		cbProyecto = new JComboBox();
		cbProyecto.setBounds(154, 64, 141, 22);
		formInforme.add(cbProyecto);
		cbProyecto.addItemListener(this);
		
		lblVersion = new JLabel("Escoger Version");
		lblVersion.setBounds(305, 64, 101, 22);
		formInforme.add(lblVersion);
		
		cbVersion = new JComboBox();
		cbVersion.setBounds(404, 64, 164, 22);
		formInforme.add(cbVersion);
		cbVersion.addItemListener(this);
		
		lblCiclo = new JLabel("Escoger el Ciclo:");
		lblCiclo.setBounds(578, 64, 106, 22);
		formInforme.add(lblCiclo);
		
		cbCiclo = new JComboBox();
		cbCiclo.setBounds(688, 64, 118, 22);
		formInforme.add(cbCiclo);
		
		scroll.setVisible(true);
		tablaResumen.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(btnSalir == e.getSource()) {
			this.dispose();
		}	
		
		if(btnLimpiar == e.getSource()) {
			actualizarFormulario();
		}
		
		if(btnBuscar == e.getSource()) {
			if(cbProyecto.getSelectedItem() != null && cbVersion.getSelectedItem() == null && cbCiclo.getSelectedItem() == null) {
				ClObjetosCombo proyecto = (ClObjetosCombo) cbProyecto.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				strCondicion = " WHERE p.idProyecto = " + proyecto.getId();
				actualizarTabla();
			}
			
			if(cbProyecto.getSelectedItem() != null && cbVersion.getSelectedItem() != null) {
				ClObjetosCombo proyecto = (ClObjetosCombo) cbProyecto.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				ClObjetosCombo version = (ClObjetosCombo) cbVersion.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				strCondicion = " WHERE p.idProyecto = " + proyecto.getId() + " AND v.idVersion = " + version.getId();
				actualizarTabla();
			}
			
			if (cbProyecto.getSelectedItem() == null && cbVersion.getSelectedItem() != null && cbCiclo.getSelectedItem() == null) {
				ClObjetosCombo version = (ClObjetosCombo) cbVersion.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				strCondicion = " WHERE v.idVersion = " + version.getId();
				actualizarTabla();
			}
			
			if(cbVersion.getSelectedItem() != null && cbCiclo.getSelectedItem() != null) {
				ClObjetosCombo version = (ClObjetosCombo) cbVersion.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				ClObjetosCombo ciclo = (ClObjetosCombo) cbCiclo.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				strCondicion = " WHERE v.idVersion = " + version.getId() + " AND cp.idCiclo = " + ciclo.getId();
				actualizarTabla();
			}
			
			if (cbProyecto.getSelectedItem() != null && cbVersion.getSelectedItem() != null && cbCiclo.getSelectedItem() != null) {
				ClObjetosCombo version = (ClObjetosCombo) cbVersion.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				ClObjetosCombo proyecto = (ClObjetosCombo) cbProyecto.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				ClObjetosCombo ciclo = (ClObjetosCombo) cbCiclo.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				strCondicion = " WHERE v.idVersion = " + version.getId() + " AND p.idProyecto = " + proyecto.getId() + " AND cp.idCiclo = " + ciclo.getId();
				actualizarTabla();
			}
			
		}
		
	}
	
	/*METODO ENCARGADO DE LIMPIAR Y ACTUALIZAR LA TABLA DEL FORMULARIO*/
	public void actualizarFormulario() {
		limpiar.LimpiarControles(formInforme); /*EJECUTAMOS EL METODO QUE SE ENCARGARA DE LIMPIAR LOS OBJETOS DEL FORMULARIO*/
		actualizarTabla();
	}
	
	/*METODO ENCARGADO DE ASIGNAR UN MODELO DE DATOS A LA TABLA*/
	public void actualizarTabla() {
		tablaResumen.setModel(resumen.devolverModelo(strCondicion));
	}
	
	/*METODO ENCARGADO DE LLENAR LOS COMBOBOX DEL FORMULARIO*/
	public void cargarCombo(JComboBox combo) {
		try {
			resumen.CargarCombo(combo,strProcedure); /*LLAMA EL METODO CARGARCOMBO DEL OBJETO CREAR, SE ENVIA EL COMBOBOX Y EL STORE PROCEDURE QUE VAMOS A EJECUATR*/
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Se presento un error cargando la información, intentelo nuevamente"); /*CAPTURAMOS LA EXCEPCION EN CASO DE ERROR*/
		}
	}
	
	public void itemStateChanged(ItemEvent e) {
		if (cbProyecto == e.getSource()) {
			if (e.getStateChange() == ItemEvent.SELECTED) /*CAPTURAMOS EL EVENTO SELECCIONAR*/ {
				ClObjetosCombo proyecto = (ClObjetosCombo) cbProyecto.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				cbVersion.removeAllItems(); /*BORRAMOS LOS ELEMENTOS QUE TENGA EL COMBOBOX VERSION*/
				strProcedure = "{call cargarVersiones("+ proyecto.getId() +")}";
				this.cargarCombo(cbVersion); /*SE EJECUTA EL METODO CARGAR COMBO PARA ACTUALIZAR EL OBJETO CON LA NUEVA SELECCION*/
			}
		}
		if (cbVersion == e.getSource()) {
			if (e.getStateChange() == ItemEvent.SELECTED) /*CAPTURAMOS EL EVENTO SELECCIONAR*/ {
				ClObjetosCombo version = (ClObjetosCombo) cbVersion.getSelectedItem(); /*INSTANCIAMOS UN OBJETO DE TIPO OBJETOS COMBO EL CUAL NOS AYUDARA A RECUPERAR EL ID DEL COMBOBOX*/
				cbCiclo.removeAllItems(); /*BORRAMOS LOS ELEMENTOS QUE TENGA EL COMBOBOX VERSION*/
				strProcedure = "{call cargarCiclo("+ version.getId() + ")}";
				this.cargarCombo(cbCiclo); /*SE EJECUTA EL METODO CARGAR COMBO PARA ACTUALIZAR EL OBJETO CON LA NUEVA SELECCION*/
			}
		}
	}
}
