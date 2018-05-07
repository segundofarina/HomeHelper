drop table if EXISTS messages CASCADE;
drop table if exists serviceProviders CASCADE;
drop table if exists serviceTypes CASCADE;
drop table if exists users CASCADE;
drop table if exists reviews CASCADE;
drop table if exists aptitudes CASCADE;
drop table if exists appointment CASCADE;


CREATE TABLE IF NOT EXISTS users (
  userid SERIAL PRIMARY KEY,
  username varchar(100) UNIQUE NOT NULL ,
  password varchar(100) NOT NULL,
  firstname varchar(100),
  lastname varchar(100),
  email varchar(100),
  phone varchar(100)
);

CREATE TABLE IF NOT EXISTS serviceTypes (
  serviceTypeId SERIAL PRIMARY KEY,
  serviceName varchar(256)
);

CREATE TABLE IF NOT EXISTS serviceProviders(
  userId INTEGER REFERENCES users(userid) PRIMARY KEY,
  description varchar(10000)
);

CREATE TABLE IF NOT EXISTS aptitudes(
  aptitudeId SERIAL PRIMARY KEY,
  userId INTEGER REFERENCES serviceProviders(userId),
  serviceTypeId INTEGER REFERENCES serviceTypes(serviceTypeId),
  description varchar(10000)
);

CREATE TABLE IF NOT EXISTS reviews(
  userId INTEGER  REFERENCES users(userId),
  aptitudeId INTEGER REFERENCES aptitudes(aptitudeId),
  reviewdate TIMESTAMP default CURRENT_DATE,
  quality INTEGER CHECK(quality > 0 AND  quality < 6),
  cleanness INTEGER CHECK(cleanness > 0 AND cleanness < 6),
  price INTEGER CHECK(price > 0 AND price < 6),
  punctuality INTEGER CHECK(punctuality > 0 AND punctuality < 6),
  treatment INTEGER CHECK(treatment > 0 AND treatment < 6),
  comment varchar(10000)
);
CREATE TABLE IF NOT EXISTS messages(
  userFrom INTEGER REFERENCES users(userId),
  userTo  INTEGER REFERENCES users(userId),
  message VARCHAR(10000),
  messageDate TIMESTAMP  default CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS appointment(
  appointmentId SERIAL PRIMARY KEY,
  userId INTEGER REFERENCES users(userId),
  providerId INTEGER REFERENCES serviceProviders(userId),
  serviceTypeId INTEGER REFERENCES serviceTypes(serviceTypeId),
  appointmentDate VARCHAR(100),
  address VARCHAR(10000),
  status VARCHAR(20),
  jobDescription VARCHAR(10000)
);

insert into users VALUES (1,'sfarina','dulcedeleche','Segundo Augusto','Fariña','afarina@itba.edu.ar','1541234567');
insert into users VALUES (2,'mvictory','dulcedeleche','Martin','Victory','mvictory@itba.edu.ar','1563498751');
insert into users VALUES (3,'fcavallin','dulcedeleche','Florencia','Cavallin','fcavallin@itba.edu.ar','1563287519');
insert into users VALUES (4,'marcemiozzo','dulcedeleche','Marcelo','Miozzo','marcemiozzo@google.com','1532357421');
insert into users VALUES (5,'nadimer','dulcedeleche','Nadine','Merlino','nadimer@fibertel.com.ar','1561182277');
insert into users VALUES (6,'scavallin','dulcedeleche','Sergio Eduardo','Cavallin','scavallin@pluspetrol.net','1540910023');
insert into users VALUES (7,'bianq','dulcedeleche','Bianca','Fallace','bianqfallace@google.com','1542366653');
insert into users VALUES (8,'alvarocrespo','dulcedeleche','Alvaro','Crespo','acrespo@itba.edu.ar','1563325569');
insert into users VALUES (9,'mfallone','dulcedeleche','Marco','Fallone','mfallon@itba.edu.ar','1562009879');
insert into users VALUES (10,'mtessino','dulcedeleche','Mario','Tessino','maritotessino@outlook.com','1562341209');

insert into serviceProviders VALUES (1,'Mi nombre es Segundo y tengo 22 años, trabajo en el negocio desde 2010. Trabaje en la fabrica de mubles de Noray desde el 2010 al 2015. En el 2015 comence mi propia empresa de mubles a medida. Contactate con nosotros y a la brevedad te contestaremos!!');

insert into serviceProviders VALUES (6,'Soy Sergio, tengo 56. Trabaje de ingeniero quimico durante 30 años, ahora me decido a la pinturería, desde 2012. Mandame un mensaje para que te presupuesto un trabajo.
Te garantizamos:
- EXCELENCIA EN EL TRABAJO
- PRESUPUESTO SIN CARGO
- PRECIOS ACCESIBLES
- FACTURA
- PUNTUALIDAD
');

insert into serviceProviders VALUES (9,'Mi nombre es Marco y soy ingeniero en electronica del instituto tecnico de Buenos Aires. Tengo 30 años y hace 10 años que me dedico a realizas instalaciones electricas.
Realizo:
Cableados
Colocación de luces
Cortocircuito
Disyuntores
Instalación de luminarias
Llaves térmicas
Porteros eléctricos
Recableado
Tableros eléctricos
Tomacorrientes

Incluye
Personal asegurado
Garantía
');

insert into serviceProviders VALUES (8,'PLOMERIA, GAS Y DESTAPACIONES

PLOMERO-GASISTA MATRICULADO

CONSULTAS ÚNICAMENTE DE 9 A 18 HS.
URGENCIAS DESDE LAS 18HS EN ADELANTE

Atendemos capital federal y zona norte.
Trabajos garantidos. Realizamos factura consumidor final.' ||
 '
5434 - 8435
15 - 5320 - 3485
15 - 3641 - 3641
Llame sin compromiso, su consulta no molesta. Muchas gracias!');

insert into serviceProviders VALUES (10,'Soy Mario y tengo 62 años. Trabajo de herrero hace 20 años. Su consulta no es molestía, por favor pregunte!!!');

insert into serviceTypes VALUES (1,'Carpintero');
insert into serviceTypes VALUES (2,'Pintor');
insert into serviceTypes VALUES (3,'Electricista');
insert into serviceTypes VALUES (4,'Plomero');
insert into serviceTypes VALUES (5,'Gasista');
insert into serviceTypes VALUES (6,'Herrero');

insert into aptitudes VALUES (1,1,1,'Realizamos todo tipo de Muebles en Melamina, Madera Maciza, Hierro. Todo a medida. Consultame por alguna Madera en especial!
•Bajo mesada
•Alacenas
•Escritorios
•Placares
•Interiores de placard
•Banquetas
•Barras
•Mesas ratonas
•Rack de Tv
•Mesas de Luz
TODO LO QUE NECESITES ME CONTACTAS Y ARMAMOS JUNTOS EL DISEÑO QUE QUIERAS!!

Todo lo que necesitas para tu empresa o tu hogar lo vas a encontrar.
Lo que estés buscando, nosotros lo Pensamos junto a vos, Diseñamos, Armamos y hacemos la colocacion. No dejamos nada al Azar.
Envianos tu consulta por mail a MADERADEAUTOR@GMAIL.COM o al 1162814845 por WhatsApp.
Enviame un mensaje sin compromiso y te presupuestamos Online SIN CARGO.
Consultanos por un presupuesto a Domicilio!

No incluye
-No incluye presupuesto a Domicilio.
-No incluye Viático');

insert into aptitudes VALUES (2,6,2,'Incluye

PINTURA COLORPLUS

Hacemos trabajos en:
· Interiores/Exteriores
· Cerramientos en general (madera/metal)
· Rejas/muros/portones/etc
· Impermeabilización de terrazas y balcones

PINTAMOS SOBRE TODO TIPO DE MATERIALES:

· Madera
· Paredes
· Cielo rasos
· Rejas
· Pisos
· Cerámicas

TRABAJAMOS CON DIVERSAS PINTURAS

· Latex satinado
· Latex mate
· Epoxi
· Esmaltes sintéticos
· Tarquini
· PermaWhite

REALIZAMOS TRABAJOS EN:

· Casas
· Deptos
· Oficinas
· Industrias

TAMBIEN EFECTUAMOS TRABAJOS DE:

· Retiro de empapelado
· Enduido completo
· Pulido y plastificado de pisos
');

insert into aptitudes VALUES (3,9,3,'
Se realizan instalaciones electricas de todo tipo.
DCI pedido de medidor.
Cableados y recableados en viviendas y oficinas por cañeria interna o externa.
Se arreglan cortocircuitos y fugas.
Colocacion de Artefactos electricos. Lamparas LEDS, cocinas, Aire acondicionados, etc.
Renovacion de tomas, teclas, tapas, iluminacion, etc.
Armado de tableros electricos.
Renovacion de Tablero con tapones a Termica y disyuntor reglamentarios.
Se colocan Jabalinas ( puesta a tierra).
Colocacion de sensores de movimiento y foto control.
Se realiza la adecuacion completa, para el pedido de medidor.
y mas..
');

insert into aptitudes VALUES (4,8,4,'No rompa su casa!!! Limpiamos y desobstruimos cañerías de agua caliente y fría con productos desoxidantes importados y presión regulada e inducida. Instalaciones de agua y desagües. Instalaciones y reparaciones en Termofusion, polipropileno, galvanizado, hidrobronz, plomo, etc. Trabajos en altura con silleta, armado y amurado de colectores y caños maestros exteriores.
Todo tipo de arreglos de plomeria... Fugas de agua o desagües, mochilas, depósitos, flexibles, monocomandos, canillas, limpieza de tanques, bombas de todo tipo y marca, válvulas de inodoro, service de cualquier tipo de artefacto, etc, etc.');

insert into aptitudes VALUES (5,8,5,'Matriculado de 1ra categoría - Matriculado en combustión - Detecciones de fugas con instrumentos de presicion.
Trabajamos en termofusion, epoxi y galvanizado. Trabajos en altura con silleta, tendido y amurado de caños en vacío, salidas de gases a los 4 vientos. Instalaciones, planos habilitaciones, pruebas de hermeticidad.
Arreglos e instalaciones de todo tipo de artefactos a gas... calefones, termotanques, cocinas, calderas, estufas, anafes, artefactos industriales y hogareños.
Destapamos todo tipo de cañerías... cloacales, pluviales, piletas de patio, balcones, todo tipo de rejillas. Lustrado de cañerías. desagotes de sótanos y cámaras.');

insert into aptitudes VALUES (6,10,6,'
Realizo:
Aleros
Balcones
Barandas
Cerramientos
Escaleras
Frentes
Muebles
Parillas
Portones
Puertas
Rejas
Techos
Ventanas
A&R

Incluye
Garantía


Trabajos de Herrería en general.

•Frentes de rejas.
•Portones / Puertas / Ventanas.
•Reformas y automatización de portones corredizos.
•Protecciones de balcón / Piletas.
•Barandas.
•Parrillas, Asadores y cerramientos de parrilla.
•Cestos de residuos.
•Muebles metálicos para locales comerciales y hogares.
•Techos de policarbonato/chapa para entradas de garage.
•Estructura de cocheras.
•Estructuras metálicas en general.
•Todo tipo de trabajos de soldadura
•Automatización de portones corredizos.
•Soldadura a domicilio de cualquier tipo.
•Protecciones de balcon.
•Reparaciones en gral.
•Herreria en Obra.
•Pintura epoxi.');


insert into reviews VALUES (2,1,DEFAULT,5,5,5,5,5,'Me presupuesto por una biblioteca y una mesa de hierro y madera. Fue muy rapido y claro. En 10 dias habiles recibi el mueble en mi domicilio ya que habia pedido envio. Todo excelente y exactamente como lo pedi. Vovlere a comprar.');
insert into reviews VALUES (3,1,DEFAULT,4,5,3,4,4,'Le encargue a matias una tabla de trabajo de 0. 68 x 2. 00 x 0. 77 x 0. 03 mts, con 2 caballetes y una cajonera (3 cajones), en madera de eucalipto y barnizado. Excelente trabajo. Gracias!!.');

insert into reviews VALUES (3,2,DEFAULT,3,4,4,5,2,'La verdad que estoy muy conforme con el trabajo que realizaron. Cumplieron el precio estipulado en el presupuesto. Y algunos detalles que no les correspondia los hicieron de onda. Y dejaron todo limpio y ordenado.');
insert into reviews VALUES (4,2,DEFAULT,5,5,5,5,4,'Fue una experiencia estupenda. Los contrate sin estar en el departamento, un semipiso de casi 200 mts2. Me han hecho pintura de todos los ambientes, aberturas, reparon puertas y paredes y quedo todo mas que estupendo. Muy honestos. Excelente staff de gente. Recomendado absolutamente, gracias');
insert into reviews VALUES (5,2,DEFAULT,4,4,4,5,3,'Agradezco por el trabajo realizado de colocación de revestimiento en el frente de mi casa, excelente predisposición y responsable con su trabajo. Muchas gracias!.');

insert into reviews VALUES (1,3,DEFAULT,5,5,5,4,3,'Realizo la ronovacion del cableado de toda la casa, colocando el tablero nuevo con varios circuitos. Estamos muy conformes con el trabajo y con la predisposición ante todos los pedidos. Saludos y muchas gracias.');
insert into reviews VALUES (2,3,DEFAULT,2,1,3,4,5,'Lo llamé para que venga a revisar unas cosas, el valor de la visita me pareció un exceso. $700. En parte el error fue mío por no preguntar de antemano el costo de la visita, pero nunca me imaginé que saldría ese valor. Aclaro que en la visita estuvo unos 40 min pero para diagnosticar en ningún momento se reparó nada y no se trataba de una emergencia ni nada. Estaba con luz y fue una visita programada. A su favor es amable en el trato y llegó puntual, pero desistí de hacer el trabajo con él porque ese costo de visita me hizo dudar si lo que me presupuestó también estaba inflado. Una lástima porque por el resto de las opiniones parece confiable, pero por lo menos tengan presente consultar cuánto cobra la vista.');
insert into reviews VALUES (3,3,DEFAULT,4,5,5,4,3,'Lo contacté el mismo día que necesitaba revise un problema en un departamento que debíamos entregar al día siguiente. Fue tarde a revisar, determino rápidamente el problema y nos dijo que debíamos comprar para solucionarlo. Volvió al día siguiente, acoplándose al horario nuestro y realizó el trabajo prolija y rápidamente. El pago de la mano de obra fue muy razonable. Excelente servicio, recomendable! muchas gracias!!.');
insert into reviews VALUES (4,3,DEFAULT,1,2,3,4,2,'No lo recomiendo. A favor de el, que llego a horario. Muy desprolijo, dejo todo sucio, además me dejo sin luz en el dormitorio y se fue. Me hizo comprar material eléctrico que no uso - debía volver para terminar, pero la experiencia me llevo a contratar a otro para que terminara el trabajo.');
insert into reviews VALUES (5,3,DEFAULT,4,4,4,4,3,'Lo citamos por un problema con las luces del fondo y la luz del lavadero. Solucionó todo en el momento y además hizo un par de arreglos en el interior de la casa, el precio fue justo y lo tenemos agendado como un profesional de confianza para llamar cada vez que sea necesario.');
insert into reviews VALUES (6,3,DEFAULT,5,5,5,5,5,'Todo perfecto! tenia un problema urgente de cableado. Lo llame y paso en el dia a verlo. Al dia siguiente ya estaba trabajando en el tema. Termino el trabajo en el dia, dandome una rapida solucion. Super recomendable! se pudo acomodar a mis horarios. ');
insert into reviews VALUES (7,3,DEFAULT,1,1,1,1,1,'Quede el dia anterior para que pase y presupueste un trabajo,me dijo que podia venir. Al dia siguiente lo espere y no solo no vino sino que tampoco aviso. Falte al laburo en vano. No lo recomiendo. Poco profesional y poco etico.');

insert into reviews VALUES (1,4,DEFAULT,5,5,5,4,3,'Todo 10 puntos. Muy amables y educados. Limpiaron el tanque de agua y repararon el calefon. Precio acorde y accesible. Muy agradecido. Serán recomendados.');
insert into reviews VALUES (2,4,DEFAULT,2,1,3,4,5,'Me vinieron a destapar un inodoro, estuvo 15 minutos y me cobro $3. 240. - una locura total. Su trabajo es totalmente incomprobable. Me pareció una estafa. Tengo la factura por si alguien la quiere.');
insert into reviews VALUES (3,4,DEFAULT,4,5,5,4,3,'Despues de 3 supuestos "plomeros" vinieran a ver el trabajo, llame a javier para ver que problema tenia la bomba presurizadora, no me vino con ningun cuento como los otros, me explico paso a paso el funcionamiento y que habia que hacer para repararla. Acepte el presupuesto y me la reparo en el dia, tenia los repuestos en el vehiculo, quede re conforme porque me lo soluciono rapido y barato, desarmo toda la bomba y me salio mucho mas barato que cambiarla y comprar otra. Eternamente agradecido. Saludos. Jose de caballito. Saludos!.');

insert into reviews VALUES (4,5,DEFAULT,5,5,5,4,3,'Cumplio con el horario y con el precio acordado, y lo mas importante confirmo el problema q tenia, el cual era negado por el "gasista"que realizo el trabajo. Tambien me dio tips para entender lo que se habia hecho mal y como solucionarlo,super recomendable.');
insert into reviews VALUES (5,5,DEFAULT,4,5,5,4,3,'La verdad muy conforme, se toma el tiempo para explicar cada detalle del trabajo que va a realizar. Cambio de lugar la llave de paso porque metrogas me lo requeria. Hizo el tramite y en 2 dias tenia gas de nuevo. Muy cumplidor y respetuoso. Seran tenidos en cuenta para futuros trabajos. Gracias!.');
insert into reviews VALUES (6,5,DEFAULT,5,5,5,4,3,'Hace unos meses los contrate para detectar una fuga de gas que tenia, ya habiendo probado con otros gasistas sin mucha suerte, javier lo pudo detectar con un aparato sin necesidad de andar rompiendo todo, por suerte! solucionado ese gran problema que tenia me realizaron una extencion de la red de gas para colocar una estufa la cual se coloco recientemente. Excelente trabajo, mas que conforme y agradecido a javier por lo profesional y macanudo a la hora de explicar y aconsejar sobre la solucion de dichos problemas.');
insert into reviews VALUES (7,5,DEFAULT,5,5,5,4,3,'Me resolvio una urgencia fuera de horario, con gran calidad de trabajo y mucho don de gente. Se que seguramente lo vuelva a necesitar para unas reformas mas adelante. No dudare en llamarlo con todo gusto. Recomiendo 100%');

insert into reviews VALUES (8,6,DEFAULT,5,5,5,4,3,'Cambiamos la entrada de un local comercial, me aconsejo re bien, el trabajo quedo excelente! coordinamos muy rápido (y en ningún momento me pateó o plantó (antes de contactarlo llame a otros dos supuestamente recomendados que me plantaron) muy detallista y perfeccionista, sin ninguna duda lo volvería a llamar si necesito hacer alguna otra cosa. Super recomendable!!!.');
insert into reviews VALUES (9,6,DEFAULT,5,5,5,5,5,'Reparó una puerta de una cocina que da al exterior. Super profesional, la dejo como nueva. Recomiendo contratar su servicio.');
insert into reviews VALUES (1,6,DEFAULT,5,5,5,5,5,'Trabajo bien y rápido. Arreglo una parrilla de un quincho. Muchas gracias!!!!.');

insert into messages VALUES (2,6,'Hola Julio como estas te queria hacer una consulta por el tema de carpinteria',DEFAULT );
insert into messages VALUES (6,2,'Hola Florencia si que necesitas?',DEFAULT );
insert into messages VALUES (2,6,'Necesito hacer un aramrio para zapatillas',DEFAULT );

insert into messages VALUES (2,9,'Este tambien es un chat',DEFAULT );
insert into messages VALUES (9,2,'AAA mira que bueno',DEFAULT );
insert into messages VALUES (2,9,'Jajaja',DEFAULT );