package controlador;

import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClLimpiarControles {
	/*CLASE ENCARGADA DE LIMPIAR TODOS LOS CONTROLES DEL FORMULARIO*/
	public ClLimpiarControles() {
		
	}
	
	public void LimpiarControles(JPanel panel) {
		/*SE CREA EL OBJETO COMPONENTE Y SE LE ASIGNA COMO VALORES LOS COMPONENTES DEL FORMULARIO*/
		Component[] componentes =panel.getComponents();
		for(int i=0;i<componentes.length;i++) { /*SE RECORRE EL TAMAÑO DEL OBJETO*/
			if(componentes[i] instanceof JTextField) { /*SE VERIFICA QUE LA INSTANCIA SEA UN TEXTFIEL*/
				((JTextField)componentes[i]).setText(null); /*SE ASIGNA COMO VALOR NULL*/
			}else {
				if(componentes[i] instanceof JComboBox) { /*SE VERIFICA QUE LA INSTANCIA DEL OBJETO SEA UN JCOMBOBOX*/
					((JComboBox<?>)componentes[i]).setSelectedIndex(-1); /*SE COLOCA VACIO*/
				}else {
					if (componentes[i] instanceof JTextArea) {
						((JTextArea)componentes[i]).setText(null); /*SE ASIGNA COMO VALOR NULL*/
					}
				}
			}
				
		}
	}
}
