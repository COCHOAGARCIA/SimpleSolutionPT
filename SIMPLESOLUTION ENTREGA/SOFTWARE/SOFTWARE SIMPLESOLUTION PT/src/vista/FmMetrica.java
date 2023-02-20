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

import controlador.ClFormMetrica;
import controlador.ClLimpiarControles;

/*FORMULARIO ENCARGADO DE CREAR LAS METRICAS QUE SE UTILIZARAN PARA CALIFICAR UN CICLO, UTILIZAMOS EL EVENTO ACTION */
public class FmMetrica extends JFrame implements ActionListener, MouseListener{
	
	private JPanel fmMetrica;
	private JTextField txtNomMetrica,txtId;
	private JButton btnSalir,btnGuardar,btnEliminar;
	private JTable tablaMetrica;
	private JLabel lblNombre,lblId;
	
	/*CREAMOS EL OBJETO QUE NOS PERMITIRA EJECUTAR LOS METODOS QUE SE ENCUENTRAN EN LA CLASE*/
	ClFormMetrica metrica = new ClFormMetrica();
	ClLimpiarControles limpiar = new ClLimpiarControles(); 
	private JButton btnLimpiar;/*CREAMOS EL OBJETO QUE NOS PERMITIRA ACCEDER A LA CLASE LIMPIAR*/
	
	public FmMetrica() { /*CONSTRUCTOR QUE SE EJECUTARA INMEDICATAMENTE SE INGRESE AL FORMULARIO*/
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); /*CAMBIAMOS LA FORMA DE CERRAR EL FORMULARIO, POR DEFECTO ESTA PARA CERRAR LA APLICACIÓN
	       												  Y CON ESTE CAMBIO, SOLO SE CERRARA EL FORMULARIO*/
		setTitle("PANEL DE CREACIÓN DE METRICAS"); /*ASIGNAMOS UN TITULO AL FORMULARIO*/
		inicializar(); /*INVOCAMOS EL METODO INICIALIZAR, ESTE CARGARA TODOS LOS OBJETOS QUE TIENE EL FORMULARIO, COMO JTEXTBOX, JCOBOBOX, EL PANEL Y ENTRE OTROS*/
	}
	
	public void inicializar() {
		
		setBounds(100, 100, 561, 391);
		fmMetrica = new JPanel();
		fmMetrica.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(fmMetrica);
		fmMetrica.setLayout(null);
		
		JLabel lblTitulo = new JLabel("CREACION DE METRICAS");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTitulo.setBounds(114, 10, 206, 21);
		fmMetrica.add(lblTitulo);
		
		lblNombre = new JLabel("Nombre de Metrica");
		lblNombre.setBounds(10, 89, 87, 21);
		fmMetrica.add(lblNombre);
		
		txtNomMetrica = new JTextField();
		txtNomMetrica.setBounds(104, 87, 249, 26);
		fmMetrica.add(txtNomMetrica);
		txtNomMetrica.setColumns(10);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(10, 307, 99, 37);
		fmMetrica.add(btnGuardar);
		btnGuardar.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(419, 307, 99, 37);
		fmMetrica.add(btnSalir);
		btnSalir.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		/*CREACION DE LA TABLA*/
		tablaMetrica = new JTable(metrica.devolverModelo()){
			public boolean isCellEditable(int rowIndex,int vColIndex) {
				return false;
			}
		};
		tablaMetrica.addMouseListener(this);
		
		/*CREAMOS UN OBJETO DE TIPO SCROLLPANE Y AGREGAMOS LA TABLA DENTRO DE EL*/
		JScrollPane scroll = new JScrollPane(tablaMetrica);
		
		/*AGREGAMOS EL OBJETO CREADO AL PANEL*/
		this.getContentPane().add(scroll, null);
		
		/*DAMOS FORMATO AL SCROLL Y VOLVEMOS LOS OBJETOS VISIBLES*/
		scroll.setBounds(10,124,508,144);
		scroll.setVisible(true);
		tablaMetrica.setVisible(true);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(148, 307, 99, 37);
		fmMetrica.add(btnEliminar);
		btnEliminar.addActionListener(this);
		
		lblId = new JLabel("Id");
		lblId.setBounds(10, 55, 87, 21);
		fmMetrica.add(lblId);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setColumns(10);
		txtId.setBounds(104, 53, 249, 26);
		fmMetrica.add(txtId);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(288, 307, 99, 37);
		fmMetrica.add(btnLimpiar);
		btnLimpiar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*VERIFICAMOS CON LOS PARAMETROS RECIBIDOS QUE EL BOTON SALIR ES EL QUE ESTA ESCUCHANDO EN EL MOMENTO*/
		if(btnSalir== e.getSource()) {
			this.dispose();
		}
		
		if(btnEliminar == e.getSource()){
			String strMensaje = "";
			
			if(!txtId.getText().toString().isEmpty()) {
				int resp = JOptionPane.showConfirmDialog(this, "Desea borrar el registro seleccionado??");
				if (resp == 0) {
					metrica.setIdMetrica(Integer.parseInt(txtId.getText()));
					strMensaje = metrica.strlEjecutarStore(false);
					JOptionPane.showMessageDialog(this, strMensaje);
					actualizarFormulario();
				}
			}else {
				JOptionPane.showMessageDialog(null, "Debes escoger la metrica a eliminar","Proyectos",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (btnLimpiar == e.getSource()) {
			actualizarFormulario();
		}
		
		/*VERIFICAMOS CON LOS PARAMETROS RECIBIDOS QUE EL BOTON GUARDAR ES EL QUE ESTA ESCUCHANDO EN EL MOMENTO*/
		if(btnGuardar == e.getSource()) {
			String strMensaje = "";
			if (txtNomMetrica.getText().toString().isEmpty()) {
				JOptionPane.showInternalMessageDialog(null, "Recuerde diligenciar los campos obligatorios antes de continuar","Metrica",JOptionPane.ERROR_MESSAGE);
			}else{
				/*CREAMOS EL OBJETO VALIDAR, ESTO NOS AYUDARA A ACCEDER A LOS METODOS DE LA CLASE Y EJECUTAR EL STORE PROCEDURE QUE ESTA ALMACENADO EN ELLA*/
				int intIdMetrica = 0;
				if(!txtId.getText().toString().isEmpty()) {
					intIdMetrica = Integer.parseInt(txtId.getText());
				}
				metrica.setIdMetrica(intIdMetrica);
				metrica.setNomMetrica(txtNomMetrica.getText());
				strMensaje = metrica.strlEjecutarStore(true);
				JOptionPane.showMessageDialog(this, strMensaje);
				actualizarFormulario();
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(tablaMetrica== e.getSource()) {
			int seleccion = tablaMetrica.rowAtPoint(e.getPoint());
			txtId.setText(String.valueOf(tablaMetrica.getValueAt(seleccion, 0)));
			txtNomMetrica.setText(String.valueOf(tablaMetrica.getValueAt(seleccion, 1)));
		}
		
	}

	/*METODO ENCARGADO DE LIMPIAR Y ACTUALIZAR LA TABLA DEL FORMULARIO*/
	public void actualizarFormulario() {
		limpiar.LimpiarControles(fmMetrica); /*EJECUTAMOS EL METODO QUE SE ENCARGARA DE LIMPIAR LOS OBJETOS DEL FORMULARIO*/
		actualizarTabla();
	}
	
	/*METODO ENCARGADO DE ASIGNAR UN MODELO DE DATOS A LA TABLA*/
	public void actualizarTabla() {
		tablaMetrica.setModel(metrica.devolverModelo());
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