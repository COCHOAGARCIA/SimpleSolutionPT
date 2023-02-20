package vista;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import controlador.ClFormProyectos;
import controlador.ClLimpiarControles;

public class FmProyectos extends JFrame implements MouseListener,ActionListener{
	
	private JPanel panelProyecto;
	private JTextField txtNombreProyecto,txtIdProyecto;
	private JTable tablaProyectos;
	private JButton btnSalir,btnGuardar,btnEliminar,btnLimpiar;
	private JLabel lblTitulo,lblNomProyecto,lblIdProyecto;
	private JScrollPane scroll;
	
	ClFormProyectos proyecto = new ClFormProyectos();
	ClLimpiarControles limpiar = new ClLimpiarControles(); /*CREAMOS EL OBJETO QUE NOS PERMITIRA ACCEDER A LA CLASE LIMPIAR*/
	
	public FmProyectos() { /*CONSTRUCTOR QUE SE EJECUTARA INMEDICATAMENTE SE INGRESE AL FORMULARIO*/
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); /*CAMBIAMOS LA FORMA DE CERRAR EL FORMULARIO, POR DEFECTO ESTA PARA CERRAR LA APLICACIÓN
														   Y CON ESTE CAMBIO, SOLO SE CERRARA EL FORMULARIO*/
		setTitle("PANEL DE PROYECTOS"); /*ASIGNAMOS UN TITULO AL FORMULARIO*/
		
		inicializar(); /*INVOCAMOS EL METODO INICIALIZAR, ESTE CARGARA TODOS LOS OBJETOS QUE TIENE EL FORMULARIO, COMO JTEXTBOX, JCOBOBOX, EL PANEL Y ENTRE OTROS*/
	}
	
	public void inicializar() {
		
		setBounds(100, 100, 507, 362);
		panelProyecto = new JPanel();
		panelProyecto.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelProyecto);
		panelProyecto.setLayout(null);
		
		lblTitulo = new JLabel("CREACION DE PROYECTOS");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTitulo.setBounds(10, 11, 471, 29);
		panelProyecto.add(lblTitulo);
		
		lblNomProyecto = new JLabel("Nombre del Proyecto");
		lblNomProyecto.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomProyecto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNomProyecto.setBounds(20, 91, 132, 29);
		panelProyecto.add(lblNomProyecto);
		
		txtNombreProyecto = new JTextField();
		txtNombreProyecto.setColumns(10);
		txtNombreProyecto.setBounds(178, 94, 95, 25);
		panelProyecto.add(txtNombreProyecto);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(46, 277, 86, 35);
		panelProyecto.add(btnGuardar);
		btnGuardar.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(334, 277, 86, 35);
		panelProyecto.add(btnSalir);
		btnSalir.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		/*CREACION DE LA TABLA*/
		tablaProyectos = new JTable(proyecto.devolverModelo()){
			public boolean isCellEditable(int rowIndex,int vColIndex) {
				return false;
			}
		};
		tablaProyectos.addMouseListener(this);
		tablaProyectos.setVisible(true);
		tablaProyectos.addMouseListener(this);
		
		/*CREAMOS UN OBJETO DE TIPO SCROLLPANE Y AGREGAMOS LA TABLA DENTRO DE EL*/
		scroll = new JScrollPane(tablaProyectos);
		
		/*AGREGAMOS EL OBJETO CREADO AL PANEL*/
		this.getContentPane().add(scroll, null);
		/*DAMOS FORMATO AL SCROLL Y VOLVEMOS LOS OBJETOS VISIBLES*/
		scroll.setBounds(46,131,379,130);
		scroll.setVisible(true);
		
		lblIdProyecto = new JLabel("id  del Proyecto");
		lblIdProyecto.setHorizontalAlignment(SwingConstants.CENTER);
		lblIdProyecto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblIdProyecto.setBounds(20, 52, 132, 29);
		panelProyecto.add(lblIdProyecto);
		
		txtIdProyecto = new JTextField();
		txtIdProyecto.setEditable(false);
		txtIdProyecto.setColumns(10);
		txtIdProyecto.setBounds(178, 55, 95, 25);
		panelProyecto.add(txtIdProyecto);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(142, 277, 86, 35);
		panelProyecto.add(btnEliminar);
		btnEliminar.addActionListener(this);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(238, 277, 86, 35);
		panelProyecto.add(btnLimpiar);
		btnLimpiar.addActionListener(this);
		
	}
	
	/*CREAMOS EL EVENTO QUE SE ENCARGARA DE ESCUCHAR CUANDO SE HAGA CLICK SOBRE LOS BOTONES*/
	@Override
	public void actionPerformed(ActionEvent e) {
		/*VERIFICAMOS CON LOS PARAMETROS RECIBIDOS QUE EL BOTON SALIR ES EL QUE ESTA ESCUCHANDO EN EL MOMENTO*/
		if(btnSalir== e.getSource()) {
			this.dispose();
		}
		
		/*VERIFICAMOS CON LOS PARAMETROS RECIBIDOS QUE EL BOTON GUARDAR ES EL QUE ESTA ESCUCHANDO EN EL MOMENTO*/
		if(btnGuardar == e.getSource()) {
			String strMensaje = "";
			if (txtNombreProyecto.getText().toString().isEmpty()) {
				JOptionPane.showInternalMessageDialog(null, "Recuerde diligenciar los campos obligatorios antes de continuar","Proyectos",JOptionPane.ERROR_MESSAGE);
			}else{
				/*CREAMOS EL OBJETO VALIDAR, ESTO NOS AYUDARA A ACCEDER A LOS METODOS DE LA CLASE Y EJECUTAR EL STORE PROCEDURE QUE ESTA ALMACENADO EN ELLA*/
				int intIdProyecto = 0;
				if(!txtIdProyecto.getText().toString().isEmpty()) {
					intIdProyecto = Integer.parseInt(txtIdProyecto.getText());
				}
				proyecto.setIdProyecto(intIdProyecto);
				proyecto.setNomProyecto(txtNombreProyecto.getText());
				strMensaje = proyecto.strlEjecutarStore(true);
				JOptionPane.showMessageDialog(this, strMensaje);
				actualizarFormulario();
			}
		}
		
		if(btnEliminar == e.getSource()){
			String strMensaje = "";
			
			if(!txtIdProyecto.getText().toString().isEmpty()) {
				int resp = JOptionPane.showConfirmDialog(this, "Desea borrar el registro seleccionado??");
				if (resp == 0) {
					proyecto.setIdProyecto(Integer.parseInt(txtIdProyecto.getText()));
					strMensaje = proyecto.strlEjecutarStore(false);
					JOptionPane.showMessageDialog(this, strMensaje);
					actualizarFormulario();
				}
			}else {
				JOptionPane.showMessageDialog(null, "Debes escoger el proyecto a eliminar","Proyectos",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if(btnLimpiar == e.getSource()) {
			actualizarFormulario();
		}
	}
	
	/*EVENTO QUE SE VA A DISPARAR UNA VEZ SE HAGA CLICK CON EL MOUSE*/
	@Override
	public void mouseClicked(MouseEvent e) {
		if(tablaProyectos == e.getSource()) {
			int seleccion = tablaProyectos.rowAtPoint(e.getPoint());
			txtIdProyecto.setText(String.valueOf(tablaProyectos.getValueAt(seleccion, 0)));
			txtNombreProyecto.setText(String.valueOf(tablaProyectos.getValueAt(seleccion, 1)));
		}	
	}

	/*METODO ENCARGADO DE LIMPIAR Y ACTUALIZAR LA TABLA DEL FORMULARIO*/
	public void actualizarFormulario() {
		limpiar.LimpiarControles(panelProyecto); /*EJECUTAMOS EL METODO QUE SE ENCARGARA DE LIMPIAR LOS OBJETOS DEL FORMULARIO*/
		actualizarTabla();
	}
	
	/*METODO ENCARGADO DE ASIGNAR UN MODELO DE DATOS A LA TABLA*/
	public void actualizarTabla() {
		tablaProyectos.setModel(proyecto.devolverModelo());
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