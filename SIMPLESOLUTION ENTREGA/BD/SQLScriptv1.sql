/*Habilitamos el usuario sa y le asignamos una contraseña, con esto nos autenticaremos desde el sistema*/
ALTER LOGIN sa ENABLE 
GO

ALTER LOGIN sa WITH PASSWORD = '123456789'
GO

USE master /*Usamos la base de datos master para poder borrar la BD simpleSolutionPrueba sin problemas*/
GO

DROP DATABASE IF EXISTS simpleSolutionPrueba /*Borramos la BD en caso de que exista*/
GO
/*Se crea y se utiliza la base de datos que recien creamos, se debe primero crear antes de usar*/
CREATE DATABASE simpleSolutionPrueba
GO

use simpleSolutionPrueba
GO


/*****************************************************************************/
/*******************INICIO SECCION DE TABLAS**********************************/
/*****************************************************************************/

/*INICIO DE SECCION PARA CREAR LAS TABLAS*/
/*Se crea la tabla Cargos, esta tendra un ID autonumerico e incrementara de 1 a 1, el nombre del cargo sera un registro unico*/
CREATE TABLE Cargos(
	idCargo INT IDENTITY(1,1) NOT NULL,
	nomCargo VARCHAR(20) not null UNIQUE,
	PRIMARY KEY (idCargo)
)

/*Se crea la tabla Usuarios, esta contiene una relación con la tabla Cargos, a su vez cuenta con un autonumerico incremental y
un registro unico de nombre de usuario*/
CREATE TABLE Usuarios(
	idUsuario INT IDENTITY(1,1) NOT NULL,
	nomUsuario VARCHAR(20) NOT NULL UNIQUE,
	pass VARCHAR(15) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	correo VARCHAR(100) NOT NULL,
	cargosCargoId INT NOT NULL
	PRIMARY KEY (idUsuario)
	FOREIGN KEY (cargosCargoId) REFERENCES Cargos
)

/*Se crea la tabla Proyectos, esta sera la que controlara todo el programa y el punto de partida para las demas tablas,
contara con un Id incremental, un registro unico de nombre de proyecto y se utiliza el metodo getdate para asignar la fecha en que se agrega el registro*/

CREATE TABLE Proyectos(
	idProyecto INT IDENTITY(1,1) NOT NULL,
	nomProyecto VARCHAR(20) NOT NULL UNIQUE,
	usuariosIdUsuario INT NOT NULL,
	fechaCreacion DATE DEFAULT GETDATE(),
	fechaInactivo DATE
	PRIMARY KEY (idProyecto)
	FOREIGN KEY (usuariosIdUsuario) REFERENCES Usuarios
)

/*La tabla Versiones tambien cuenta con un registro ID autonumerico, se crea la fecha por defecto de cuando se agrega el registro y
tiene una relación con la tabla proyectos(un proyecto puede tener multiples versiones) y la tabla usuario, el proyecto no se podran agregar versiones 
una vez cuente con fecha de finalización o fechaInactivo*/
CREATE TABLE Versiones (
	idVersion INT IDENTITY(1,1) NOT NULL,
	proyectosIdProyecto INT NOT NULL,
	nomVersion VARCHAR(20) NOT NULL,
	usuariosIdUsuario INT NOT NULL,
	fechaCreacion DATE DEFAULT GETDATE(),
	fechaInactivo DATE
	PRIMARY KEY (idVersion)
	FOREIGN KEY (proyectosIdProyecto) REFERENCES Proyectos,
	FOREIGN KEY (usuariosIdUsuario) REFERENCES Usuarios
)

/*La tabla ciclo de prueba esta relacionada con las versiones del sistema, ellas a su vez, pueden tener varios ciclos relacionads
todo se va a registrar mediante un ID automatico*/
CREATE TABLE Ciclo_Prueba(
	IdCiclo INT IDENTITY(1,1) NOT NULL,
	nomCiclo VARCHAR(20) NOT NULL,
	fechaCreacion DATE DEFAULT GETDATE() NOT NULL,
	fechaInactivo DATE,
	versionesIdVersion INT NOT NULL,
	usuariosIdUsuario INT NOT NULL
	PRIMARY KEY (IdCiclo)
	FOREIGN KEY (versionesIdVersion) REFERENCES Versiones,
	FOREIGN KEY (usuariosIdUsuario) REFERENCES Usuarios
)

/*La tabla metricas sera un registro semi independiente, contendra valor especificos que se podran evaluar durante la ejecución de un ciclo, 
no dependera de ninguna otra tabla y su funcion es generar un estandar de los criterios a calificar*/
CREATE TABLE Metricas (
	idMetrica INT IDENTITY(1,1) NOT NULL,
	nomMetrica VARCHAR(100) NOT NULL UNIQUE,
	usuariosIdUsuario INT NOT NULL,
	fechaCreacion DATE DEFAULT GETDATE()
	PRIMARY KEY (idMetrica)
	FOREIGN KEY (usuariosIdUsuario) REFERENCES Usuarios
)

/*La tabla registros es donde se almacenara la calificación de la ejecución de la metrica, se guardaran los intentos de prueba, la metrica utilizada, 
cuantos de esos casos funcionaron y el ciclo que a su vez esta relacionado con las versiones,tendra una validación que solo permitira agregar numeros del 0
al 10*/
CREATE TABLE Registro_Pruebas(
	IdPrueba INT IDENTITY(1,1) NOT NULL,
	metricasIdMetrica INT NOT NULL,
	ciclo_pruebaIdCiclo INT NOT NULL,
	cantPruebas INT CHECK (cantPruebas >= 0 AND cantPruebas <= 10) NOT NULL,
	cantPruebasCorrectas INT NOT NULL,
	comentario VARCHAR(50) NOT NULL,
	usuariosIdUsuario INT NOT NULL,
	fechaCreacion DATE DEFAULT GETDATE()
	PRIMARY KEY (IdPrueba)
	FOREIGN KEY (metricasIdMetrica) REFERENCES Metricas,
	FOREIGN KEY (usuariosIdUsuario) REFERENCES Usuarios,
	FOREIGN KEY (ciclo_pruebaIdCiclo) REFERENCES Ciclo_Prueba
)

GO

/*Insertamos registros a la tabla cargos y usuarios, con el fin de que el sistema nos permita ingresar al sistema con estos datos*/
INSERT INTO Cargos(nomCargo) VALUES ('DESARROLLADOR')
INSERT INTO Usuarios(nomUsuario,pass,nombre,cargosCargoId,correo) VALUES ('cochoa','123456789','CRISTIAN OCHOA',1,'CALA-3020@HOTMAIL.COM')

GO

/*****************************************************************************/
/*******************FIN SECCION DE TABLAS*************************************/
/*****************************************************************************/



/*****************************************************************************/
/*******************INICIO SECCION DE PROYECTOS*******************************/
/*****************************************************************************/

/*Se crea el precedimiento almacenado que tendra como funcion verificar si el proyecto existe, sino, crearlo*/
CREATE PROCEDURE verificarProyecto
/*Declaramos las variables a utilizar donde el procedimiento*/
@strNomProyecto Varchar(20),
@intIdUsuario int,
@idProyecto int
as
/*Verificamos si la consulta ejecutada devuelve algun valor(Se verifica si ya esta creado un proyecto con este nombre)*/
IF NOT EXISTS (SELECT idProyecto FROM Proyectos WHERE idProyecto=@idProyecto)
/*Si no existe, proceda a insertar los datos recibidos por variables*/
INSERT INTO Proyectos(
	nomProyecto,
	usuariosIdUsuario
) VALUES (
	@strNomProyecto,
	@intIdUsuario
)
/*Si existe use nombre de proyecto, no devuelva datos y asi verificamos en el programa si se ejecuto o no*/
ELSE

UPDATE Proyectos SET nomProyecto = @strNomProyecto WHERE idProyecto = @idProyecto
GO

/*Se crea un procedimiento almacenado que nos ayudara a cargar la información necesaria a los ComboBox del sistema, solo mostraremos el Id y el nombre
Se va a organizar de forma Ascendente con el nombre*/
CREATE PROCEDURE cargarComboProyecto
AS
SELECT idProyecto,nomProyecto,fechaCreacion FROM Proyectos ORDER BY idProyecto  DESC
GO

CREATE PROCEDURE eliminarProyecto
@idProyecto int
AS
DELETE Proyectos WHERE idProyecto = @idProyecto
GO

/*****************************************************************************/
/*******************FIN SECCION DE PROYECTOS**********************************/
/*****************************************************************************/



/*****************************************************************************/
/*******************INICIO SECCION DE VERSIONES*******************************/
/*****************************************************************************/
/*Se crea el procedimiento almacenado el cual verificara que no exista una misma versión con el mismo nombre y con el mismo ID del proyecto,
si recupera datos, returna un nulo y si no lo recupera, inserte la información recibida por las variables*/
CREATE PROCEDURE actualizarVersion
@idProyecto int,
@nomVersion Varchar(20),
@usuariosIdUsuario int,
@idVersion int
AS
IF NOT EXISTS (SELECT idVersion FROM Versiones WHERE nomVersion = @nomVersion AND proyectosIdProyecto = @idProyecto)
	IF NOT EXISTS (SELECT idVersion FROM Versiones WHERE idVersion = @idVersion AND proyectosIdProyecto = @idProyecto)

		INSERT INTO Versiones(
			proyectosIdProyecto,
			nomVersion,
			usuariosIdUsuario
		) VALUES(
			@idProyecto,
			@nomVersion,
			@usuariosIdUsuario
		)

	ELSE

	UPDATE Versiones SET nomVersion = @nomVersion,proyectosIdProyecto =@idProyecto  WHERE idVersion = @idVersion
ELSE
RETURN NULL
GO

/*STORE QUE PERMITIRA VER LA TABLA DE LAS VERSIONES, SE UNE CON PROYECTO*/
CREATE PROCEDURE cargarVersionesTabla
AS
SELECT v.idVersion,v.nomVersion,v.fechaCreacion,v.proyectosIdProyecto,p.nomProyecto FROM Versiones AS v INNER JOIN Proyectos AS p ON p.idProyecto = v.proyectosIdProyecto   ORDER BY v.nomVersion ASC
GO

/*Este Procedure nos ayudara a cargar la información de las versiones que dependan del ID del proyecto que enviamos por variable, 
solo cargara al combobox la información que corresponda al proyecto seleccionado*/
CREATE PROCEDURE cargarVersiones
@idProyecto int
AS
SELECT idVersion,nomVersion FROM Versiones WHERE proyectosIdProyecto = @idProyecto ORDER BY nomVersion ASC
GO

CREATE PROCEDURE eliminarVersion
@idVersion int
AS
DELETE Versiones WHERE idVersion = @idVersion
GO

/*****************************************************************************/
/*******************FIN SECCION DE VERSIONES**********************************/
/*****************************************************************************/



/*****************************************************************************/
/*******************INICIO SECCION DE METRICAS********************************/
/*****************************************************************************/

/*Utilizando la misma metodologia de los anteriores, verificamos que no exista una metrica con el mismo nombre y asi poder crearla*/
CREATE PROCEDURE actualizarMetrica
@nomMetrica Varchar(100),
@idUsuario int,
@idMetrica int
AS

IF NOT EXISTS (SELECT idMetrica FROM Metricas WHERE  idMetrica = @idMetrica)

INSERT INTO Metricas(
	nomMetrica,
	usuariosIdUsuario
) VALUES (
	@nomMetrica,
	@idUsuario
)

ELSE
	UPDATE Metricas SET nomMetrica = @nomMetrica
	WHERE idMetrica = @idMetrica
						
GO

/*Buscamos todas las metricas creadas en la Base de datos*/
CREATE PROCEDURE cargarMetrica
AS
SELECT idMetrica,nomMetrica FROM Metricas ORDER BY idMetrica ASC
GO

CREATE PROCEDURE eliminarMetrica
@idVersion int
AS
DELETE Metricas WHERE idMetrica = @idVersion
GO

INSERT INTO Metricas(nomMetrica,usuariosIdUsuario) VALUES ('Cuantos bugs encontrados por modulo?',1)
INSERT INTO Metricas(nomMetrica,usuariosIdUsuario) VALUES ('Tiempo de respuesta entre botones?',1)
INSERT INTO Metricas(nomMetrica,usuariosIdUsuario) VALUES ('La información se almacena exitosamente en la BD?',1)
INSERT INTO Metricas(nomMetrica,usuariosIdUsuario) VALUES ('Se encontraron problemas en seguridad?',1)
INSERT INTO Metricas(nomMetrica,usuariosIdUsuario) VALUES ('Se cumplen con todos los casos de prueba?',1)
INSERT INTO Metricas(nomMetrica,usuariosIdUsuario) VALUES ('Errores encontrados en el ciclo de regresion?',1)
INSERT INTO Metricas(nomMetrica,usuariosIdUsuario) VALUES ('Cantidad de modulos funcionales con respecto al primer ciclo?',1)
INSERT INTO Metricas(nomMetrica,usuariosIdUsuario) VALUES ('Tasa de exito desde el ultimo sprint?',1)
INSERT INTO Metricas(nomMetrica,usuariosIdUsuario) VALUES ('Los modulos son entendibles e intuitibos?',1)
INSERT INTO Metricas(nomMetrica,usuariosIdUsuario) VALUES ('Se realizan las validaciones con respecto a los datos permitidos',1)
INSERT INTO Metricas(nomMetrica,usuariosIdUsuario) VALUES ('Las excepciones se manejan correctamente y es informativo?',1)
GO
/*****************************************************************************/
/*******************FIN SECCION DE METRICAS***********************************/
/*****************************************************************************/



/*****************************************************************************/
/*******************INICIO SECCION DE CICLOS**********************************/
/*****************************************************************************/

/*Procedure que ayudara a buscar si existe un ciclo con el mismo nombre y relacionado con la misma versión seleccionado
utilizamos la misma metodologia, si no existe, se procede a crear, sino, devuelve un valor nulo para controlar la aplicación desde el programa*/
CREATE PROCEDURE actualizarCiclo
@versionesIdVersion int,
@nomCiclo Varchar(20),
@idUsuario int,
@idCiclo int
AS
IF NOT EXISTS (SELECT IdCiclo FROM Ciclo_Prueba WHERE nomCiclo = @nomCiclo AND versionesIdVersion = @versionesIdVersion)
	IF NOT EXISTS (SELECT IdCiclo FROM Ciclo_Prueba WHERE IdCiclo = @idCiclo AND versionesIdVersion = @versionesIdVersion)
		INSERT INTO Ciclo_Prueba(
		nomCiclo,
		versionesIdVersion,
		usuariosIdUsuario
	) VALUES(
		@nomCiclo,
		@versionesIdVersion,
		@idUsuario
	)
	ELSE
	UPDATE Ciclo_Prueba SET nomCiclo = @nomCiclo,			
						    versionesIdVersion = @versionesIdVersion
	WHERE IdCiclo = @idCiclo
ELSE
RETURN NULL
GO

/*Procedure que nos ayudara a buscar todos los datos que coincidan con el ID de la versión recibida por la variable*/
CREATE PROCEDURE cargarCiclo
@versionesIdVersion INT
AS
SELECT IdCiclo,nomCiclo FROM Ciclo_Prueba WHERE versionesIdVersion = @versionesIdVersion  ORDER BY IdCiclo ASC
GO

/*STORE QUE PERMITIRA VER LA TABLA DE LOS CICLOS CREADOS, SE UNE CON VERSIÓN Y PROYECTO*/
CREATE PROCEDURE cargarCicloTabla
AS
SELECT IdCiclo,nomCiclo,cp.fechaCreacion,versionesIdVersion,v.nomVersion,v.proyectosIdProyecto,p.nomProyecto FROM (Ciclo_Prueba AS cp INNER JOIN Versiones AS v ON cp.versionesIdVersion = v.idVersion) INNER JOIN Proyectos AS p ON v.proyectosIdProyecto = p.idProyecto
GO

CREATE PROCEDURE eliminarCiclo
@idVersion int
AS
DELETE Ciclo_Prueba WHERE IdCiclo = @idVersion
GO

/*****************************************************************************/
/*******************FIN SECCION DE CICLOS*************************************/
/*****************************************************************************/




/*****************************************************************************/
/*******************INICIO SECCION DE REGISTROS*******************************/
/*****************************************************************************/

/*STORE QUE MOSTRARA TODOS LOS REGISTROS REALIZADOS*/

CREATE PROCEDURE cargarRegistros
AS
SELECT IdPrueba,p.idProyecto,p.nomProyecto,v.idVersion,v.nomVersion,m.idMetrica,m.nomMetrica,cp.IdCiclo,cp.nomCiclo,cantPruebas,cantPruebasCorrectas,comentario FROM ((( Registro_Pruebas AS rp INNER JOIN Metricas As m ON rp.metricasIdMetrica = m.idMetrica)
	   INNER JOIN Ciclo_Prueba AS cp ON rp.ciclo_pruebaIdCiclo = cp.IdCiclo)INNER JOIN Versiones AS v ON cp.versionesIdVersion = v.idVersion) INNER JOIN Proyectos AS p ON v.proyectosIdProyecto = p.idProyecto
GO

CREATE PROCEDURE eliminarRegistro
@idRegistro int
AS
DELETE Registro_Pruebas WHERE IdPrueba = @idRegistro
GO

/*Este Store Procedure nos ayudara a crear el registro de la calificación de la metrica validad, esto ayudara a que el ciclo se ejecute correctamente o no*/
CREATE PROCEDURE actualizarRegistro
@metricasIdMetrica int,
@ciclo_pruebaIdCiclo int,
@cantPruebas int,
@cantPruebasCorrectas int,
@comentario varchar(100),
@usuariosIdUsuario int
AS
	INSERT INTO Registro_Pruebas(
		metricasIdMetrica,
		ciclo_pruebaIdCiclo,
		cantPruebas,
		cantPruebasCorrectas,
		comentario,
		usuariosIdUsuario
	) VALUES (
		@metricasIdMetrica,
		@ciclo_pruebaIdCiclo,
		@cantPruebas,
		@cantPruebasCorrectas,
		@comentario,
		@usuariosIdUsuario
	)

GO
/*****************************************************************************/
/*******************FIN SECCION DE REGISTROS**********************************/
/*****************************************************************************/



