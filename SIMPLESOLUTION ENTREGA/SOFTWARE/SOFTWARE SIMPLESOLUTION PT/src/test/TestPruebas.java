package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import controlador.ClFormLogin;
import controlador.ClFormRegistro;

class TestPruebas {

	@Test
	/*SE PUEDE PRESENTAR UN ERROR DEBIDO A QUE EL USUARIO SE OBTIENE DESDE LA CLASE CONEXION, POR LO TANTO DEBE ENVIARSE POR PARAMETRO (DESCOMENTE EL SETTER EN LA CLASE CLFORMREGISTRO) */
	public void Guardar_información_en_BD(){ /*Se envian valores correctos a la BD y se espera que actualice correctamente*/
		String resultEsperado = "La información se actualizo correctamente";
		ClFormRegistro registro = new ClFormRegistro();
		registro.setCantPruebas(10);
		registro.setPrueCorrectas(10);
		registro.setCiclo(1);
		registro.setMetrica(1);
		/*registro.setIdUsuario(1);*/ //SOLO DESCOMENTAR SI YA SE DESCOMENTO LA LINEA DEL SETTER EN LA CLASE CLFORMREGISTROS
		final String resultado = registro.strlEjecutarStore(true);
		Assertions.assertEquals(resultEsperado,resultado);
	}
	
	@Test
	public void Ingresar_Valores(){ /*se envian valores mayores a 10 y se espera un error al ejecutar*/
		String resultEsperado = "La operación no se pudo realizar, se presento un error al actualizar la información";
		ClFormRegistro registro = new ClFormRegistro();
		registro.setCantPruebas(11); //se ingresan valores mayores a 10, por lo que se espera un error de actualización
		registro.setPrueCorrectas(11); //se ingresan valores mayores a 10, por lo que se espera un error de actualización
		registro.setCiclo(1);
		registro.setMetrica(1);
		final String resultado = registro.strlEjecutarStore(true);
		Assertions.assertEquals(resultEsperado,resultado);
	}
	
	@Test
	public void Ingreso_Aplicacion(){
		boolean resultadoEsperado = true;
		ClFormLogin login = new ClFormLogin("cochoa","123456789");
		final boolean resultado = login.boolValidar();
		Assertions.assertEquals(resultadoEsperado,resultado);
	}

}
