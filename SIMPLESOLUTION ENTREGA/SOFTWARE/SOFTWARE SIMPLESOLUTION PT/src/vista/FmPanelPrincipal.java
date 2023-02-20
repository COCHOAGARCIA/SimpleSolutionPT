package vista;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class FmPanelPrincipal extends JFrame implements ActionListener {
	
	private JPanel fmPanelPrincipal;
	private JButton btnCiclo,btnProyecto,btnVersion,btnMetrica,btnRegistro,btnSalir,btnInforme;
	private JLabel lblTitulo;
	
	public FmPanelPrincipal() { /*CONSTRUCTOR QUE SE EJECUTARA INMEDICATAMENTE SE INGRESE AL FORMULARIO*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /*EVENTO QUE SE ENCARGARA DE CERRAR LA APLICACIÓN SI ESTE FORMULARIO SE CIERRA*/
		setTitle("PANEL PRINCIPAL"); /*ASIGNAMOS UN TITULO AL FORMULARIO*/
		
		inicializar(); /*INVOCAMOS EL METODO INICIALIZAR, ESTE CARGARA TODOS LOS OBJETOS QUE TIENE EL FORMULARIO, COMO JTEXTBOX, JCOBOBOX, EL PANEL Y ENTRE OTROS*/
	}
	
	public void inicializar() {
		
		setBounds(100, 100, 450, 300);
		fmPanelPrincipal = new JPanel();
		fmPanelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(fmPanelPrincipal);
		fmPanelPrincipal.setLayout(null);
		
		lblTitulo = new JLabel("PANEL PRINCIPAL");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTitulo.setBounds(10, 11, 414, 29);
		fmPanelPrincipal.add(lblTitulo);
		
		btnProyecto = new JButton("Proyectos");
		btnProyecto.setBounds(10, 50, 101, 34);
		fmPanelPrincipal.add(btnProyecto);
		btnProyecto.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnVersion = new JButton("Versiones");
		btnVersion.setBounds(170, 50, 101, 34);
		fmPanelPrincipal.add(btnVersion);
		btnVersion.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnCiclo = new JButton("Ciclos");
		btnCiclo.setBounds(325, 50, 101, 34);
		fmPanelPrincipal.add(btnCiclo);
		btnCiclo.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnMetrica = new JButton("Metricas");
		btnMetrica.setBounds(10, 120, 101, 34);
		fmPanelPrincipal.add(btnMetrica);
		btnMetrica.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnRegistro = new JButton("Registrar");
		btnRegistro.setBounds(170, 120, 101, 34);
		fmPanelPrincipal.add(btnRegistro);
		btnRegistro.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(325, 120, 101, 34);
		fmPanelPrincipal.add(btnSalir);
		btnSalir.addActionListener(this); /*COLOCAMOS A ESCUCHAR EL BOTON, ESTO EN CASO DE QUE SEA PRESIONADO*/
		
		btnInforme = new JButton("Informes");
		btnInforme.setBounds(108, 183, 222, 34);
		fmPanelPrincipal.add(btnInforme);
		btnInforme.addActionListener(this);
		
	}
	
	/*CREAMOS EL EVENTO QUE SE ENCARGARA DE ESCUCHAR CUANDO SE HAGA CLICK SOBRE LOS BOTONES*/
	@Override
	public void actionPerformed(ActionEvent e) {
		/*DESDE AQUI SE INVOCA EL METODO ABRIR FORMULARIO, EL CUAL SE ENCARGARA DE PASAR COMO PARAMETRO EL OBJETO FORMULARIO*/ 
		if(btnSalir== e.getSource()) {
			System.exit(0);
		}
		if (btnProyecto== e.getSource()) {
			FmProyectos fmProyecto = new FmProyectos();
			abrirFormulario(fmProyecto);
		}
		if(btnVersion == e.getSource()) {
			FmVersion fmVersion = new FmVersion();
			abrirFormulario(fmVersion);
		}
		
		if(btnMetrica == e.getSource()) {
			FmMetrica fmMetrica = new FmMetrica();
			abrirFormulario(fmMetrica);
		}
		
		if(btnCiclo == e.getSource()) {
			FmCiclo fmCiclo = new FmCiclo();
			abrirFormulario(fmCiclo);
		}
		if(btnRegistro== e.getSource()) {
			FmRegistroPruebas fmRegistro = new FmRegistroPruebas();
			abrirFormulario(fmRegistro);
		}
		if (btnInforme == e.getSource()) {
			FmInforme fmInforme = new FmInforme();
			abrirFormulario(fmInforme);
		}
	}
	
	/*ESTE METODO SE ENCARGA DE ABRIR EL FORMULARIO, ANTES VERIFICARA SI ESTE SE ENCUENTRA O NO ABIERTO*/
	public void abrirFormulario(JFrame form) {
		if(form.isVisible()) { /*SE VERIFICA QUE EL FORMULARIO ESTA DE FORMA VISIBLE*/
			JOptionPane.showMessageDialog(this, "El formulario ya se encuentra abierto");
			form.toFront(); /*TRAEMOS EL FORMULARIO AL FRENTE*/
		}else{
			/*SI NO ESTA ABIERTO, ESTE LO VUELVE VISIBLE PARA EL USUARIO*/
			form.setVisible(true); 
		}
	}
}