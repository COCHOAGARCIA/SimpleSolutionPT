package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.ClFormLogin;

public class FmLogin extends JFrame implements ActionListener {
	private JPanel panelLogin;
	private JTextField txtUsuario,txtPassword;
	private JButton btnSalir,btnIngresar;
	private JLabel lblTitulo,lblUsuario,lblPassword;
	
	public FmLogin() { /*CONSTRUCTOR QUE SE EJECUTARA INMEDICATAMENTE SE INGRESE AL FORMULARIO*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /*CAMBIAMOS LA FORMA DE CERRAR EL FORMULARIO, POR DEFECTO ESTA PARA CERRAR LA APLICACIÓN
														Y CON ESTE CAMBIO, SOLO SE CERRARA EL FORMULARIO*/
		setTitle("INGRESO DE USUARIO"); /*ASIGNAMOS UN TITULO AL FORMULARIO*/
		inicializar(); /*INVOCAMOS EL METODO INICIALIZAR, ESTE CARGARA TODOS LOS OBJETOS QUE TIENE EL FORMULARIO, COMO JTEXTBOX, JCOBOBOX, EL PANEL Y ENTRE OTROS*/
	}
	
	public void inicializar() {
		
		setBounds(100, 100, 450, 300);
		panelLogin = new JPanel();
		panelLogin.setForeground(new Color(82, 66, 189));
		panelLogin.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelLogin);
		panelLogin.setLayout(null);
		
		lblTitulo = new JLabel("FORMULARIO DE INGRESO");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTitulo.setBounds(10, 11, 416, 29);
		panelLogin.add(lblTitulo);
		
		lblUsuario = new JLabel("INGRESE EL USUARIO");
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsuario.setBounds(41, 54, 354, 29);
		panelLogin.add(lblUsuario);
		
		lblPassword = new JLabel("INGRESE LA CONTRASEÑA");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(41, 115, 354, 29);
		panelLogin.add(lblPassword);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(80, 84, 273, 20);
		panelLogin.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(80, 144, 273, 20);
		panelLogin.add(txtPassword);
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.setBounds(80, 207, 99, 29);
		panelLogin.add(btnIngresar);
		btnIngresar.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(254, 207, 99, 29);
		panelLogin.add(btnSalir);
		btnSalir.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
	}
	
	/*CREAMOS EL EVENTO QUE SE ENCARGARA DE ESCUCHAR CUANDO SE HAGA CLICK SOBRE LOS BOTONES*/
	@Override
	public void actionPerformed(ActionEvent e) {
		/*SI SE PRESIONA EL BOTON SALIR, SE CERRARA EL SISTEMA*/
		if(btnSalir==e.getSource()) {
			System.exit(0);
		}
		
		if(btnIngresar == e.getSource()) {
			/*VERIFICAMOS CON LOS PARAMETROS RECIBIDOS QUE EL BOTON GUARDAR ES EL QUE ESTA ESCUCHANDO EN EL MOMENTO*/
			if(txtPassword.getText().isEmpty() || txtUsuario.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debe ingresar el nombre de usuario y la contraseña", " Panel de control de usuarios", JOptionPane.ERROR_MESSAGE);
			}else {
				/*CREAMOS EL OBJETO FMLOGIN QUE NOS AYUDARA A VERIFICAR EL INICIO DE SESION*/
				ClFormLogin fmLogin = new ClFormLogin(txtUsuario.getText(),txtPassword.getText());
				if (fmLogin.boolValidar()==true) { /*VERIFICAMOS QUE LA EJECUCIÓN DEL METODO SEA VERDAD(QUE INICIO SESION)*/
					this.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "No se reconoce el nombre de usuario o contraseña", " Panel de control de usuarios", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}