drop table if EXISTS messages CASCADE;
drop table if exists serviceProviders CASCADE;
drop table if exists serviceTypes CASCADE;
drop table if exists users CASCADE;
drop table if exists reviews CASCADE;
drop table if exists aptitudes CASCADE;
DROP TABLE IF EXISTS appointments CASCADE;
drop table if EXISTS verifyUsers CASCADE;
drop table if exists neighborhoods CASCADE;
drop table if exists workingzones CASCADE;
DROP TABLE if EXISTS temporaryImages CASCADE ;
drop table if exists coordenates CASCADE ;
drop table if exists userImages CASCADE ;

CREATE TABLE IF NOT EXISTS users (
  userid SERIAL PRIMARY KEY,
  username varchar(100) UNIQUE NOT NULL ,
  password varchar(100) NOT NULL,
  firstname varchar(100) NOT NULL,
  lastname varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  phone varchar(100),
  address varchar(100) NOT NULL,
  verified boolean
);

CREATE TABLE IF NOT EXISTS serviceTypes (
  serviceTypeId SERIAL PRIMARY KEY,
  serviceName varchar(256)
);

CREATE TABLE IF NOT EXISTS serviceProviders(
  userId INTEGER REFERENCES users(userid) PRIMARY KEY,
  description varchar(10000) NOT NULL
);

CREATE TABLE IF NOT EXISTS aptitudes(
  aptitudeId SERIAL PRIMARY KEY,
  userId INTEGER REFERENCES serviceProviders(userId) NOT NULL,
  serviceTypeId INTEGER REFERENCES serviceTypes(serviceTypeId) NOT NULL,
  description varchar(10000) NOT NULL
);


CREATE TABLE IF NOT EXISTS messages(
  msgId SERIAL PRIMARY KEY,
  userFrom INTEGER REFERENCES users(userId) NOT NULL,
  userTo  INTEGER REFERENCES users(userId) NOT NULL,
  userId INTEGER REFERENCES users(userId) NOT NULL,
  providerId INTEGER REFERENCES users(userId) NOT NULL,
  message VARCHAR(10000) NOT NULL,
  read BOOLEAN NOT NULL,
  messageDate TIMESTAMP  default CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS appointments(
  appointmentId SERIAL PRIMARY KEY,
  userId INTEGER REFERENCES users(userId),
  providerId INTEGER REFERENCES serviceProviders(userId),
  serviceTypeId INTEGER REFERENCES serviceTypes(serviceTypeId),
  appointmentDate DATE default CURRENT_DATE ,
  address VARCHAR(10000) NOT NULL,
  status VARCHAR(20),
  jobDescription VARCHAR(10000) NOT NULL,
  clientReview boolean default false
);

CREATE TABLE IF NOT EXISTS reviews(
  reviewId SERIAL PRIMARY KEY,
  userId INTEGER  REFERENCES users(userId) NOT NULL,
  aptitudeId INTEGER REFERENCES aptitudes(aptitudeId) NOT NULL,
  appointmentId INTEGER REFERENCES appointments(appointmentId) NOT NULL,
  reviewdate DATE default CURRENT_DATE NOT NULL,
  quality INTEGER CHECK(quality > 0 AND  quality < 6) NOT NULL,
  cleanness INTEGER CHECK(cleanness > 0 AND cleanness < 6) NOT NULL,
  price INTEGER CHECK(price > 0 AND price < 6) NOT NULL,
  punctuality INTEGER CHECK(punctuality > 0 AND punctuality < 6) NOT NULL,
  treatment INTEGER CHECK(treatment > 0 AND treatment < 6) NOT NULL,
  comment varchar(10000) NOT NULL
);

create TABLE if NOT EXISTS verifyUsers(
  userId INTEGER PRIMARY KEY REFERENCES users(userId),
  keyCode VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS temporaryImages (
  imageid SERIAL PRIMARY KEY,
  image   BYTEA
);
CREATE TABLE IF NOT EXISTS coordenates (
  coordId SERIAL PRIMARY KEY,
  userId INTEGER REFERENCES serviceProviders(userId) ,
  pos INTEGER NOT NULL,
  lat DOUBLE precision NOT NULL,
  lng DOUBLE precision NOT NULL,
  unique (userid,pos)
);

CREATE TABLE IF NOT EXISTS userImages (
  imageId SERIAL PRIMARY KEY,
  userId INTEGER  REFERENCES users(userId) NOT NULL UNIQUE,
  image   BYTEA
);




insert into users VALUES (1,'mlopez','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Marcos','Lopez','mlopez@itba.edu.ar','1541234567','cuba 2546', TRUE);
insert into users VALUES (2,'amartinez','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Alvaro','Martinez','amartinez@itba.edu.ar','1563498751','cuba 2546', TRUE);
insert into users VALUES (3,'lblanco','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Lucía','Blanco','lblanco@itba.edu.ar','1563287519','cuba 2546', TRUE);
insert into users VALUES (4,'mmiozzo','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Marcelo','Miozzo','marcemiozzo@google.com','1532357421','cuba 2546', TRUE);
insert into users VALUES (5,'nsanchez','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Nadia','Sanchez','nadisanchez@fibertel.com.ar','1561182277','cuba 2546', TRUE);
insert into users VALUES (6,'htorres','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Hugo','Torres','huguito_t@pluspetrol.net','1540910023','cuba 2546', TRUE);
insert into users VALUES (7,'bmatus','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Bianca','Matus','bianqmatus@google.com','1542366653','cuba 2546', TRUE);
insert into users VALUES (8,'lparque','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Luis','Parque','lpark@itba.edu.ar','1563325569','cuba 2546', TRUE);
insert into users VALUES (9,'dservito','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Daniel','Servito','servito@itba.edu.ar','1562009879','cuba 2546', TRUE);
insert into users VALUES (10,'mtessino','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Mario','Tessino','maritotessino@outlook.com','1562341209','cuba 2546', TRUE);
insert into users VALUES (11,'jlynch','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Juan Pablo','Lynch','juanpalynch@google.com','1563277639','cuba 2546', TRUE);
insert into users VALUES (12,'elamir','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Esteban','Lamir','elamir@itba.edu.ar','1562874621','cuba 2546', TRUE);
insert into users VALUES (13,'plifer','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Paula','Fernandez','paufernandez@outlook.com','1539098798','cuba 2546', TRUE);
insert into users VALUES (14,'aballardini','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Axel','Ballardini','aballardini@itba.edu.ar','1563880943','cuba 2546', TRUE);
insert into users VALUES (15,'frojas','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Fernando','Rojas','frojas@google.com','1542548790','cuba 2546', TRUE);
insert into users VALUES (16,'nzeta','$2a$10$JVM./2Vs7ZHtuVSGlHXxSe.JH8LeGljjrciAFVAb46qrYrQo3LSRW','Nicolas','Zeta','nico_zeta@outlook.com','1534890542','cuba 2546', TRUE);


insert into serviceProviders VALUES (1,'Mi nombre es Segundo y tengo 22 años, trabajo en el negocio desde 2010. Trabaje en la fabrica de mubles de Noray desde el 2010 al 2015. En el 2015 comence mi propia empresa de mubles a medida. Contactate con nosotros y a la brevedad te contestaremos!!');

insert into serviceProviders VALUES (2,'Mi nombre es Martin, tengo 32 años. Trabajo como pintor hace 10 años junto a un equipo de 5 pintores. Los presupuestos se presentan por escrito sin cargo alguno. (Dentro de la zona de capital federal.)
En cada trabajo nos superamos para ser recomendados. En el mes de septiembre hacemos descuentos en mano de obra.% con materiales .
Somos particulares y la facturación es con boleta Monotributista
 Visite nuestro blog con referencias y trabajos realizados: http://aplicaciondepinturawalteralvarez.blogspot.com.ar/');

insert into serviceProviders VALUES (3,'Electricista matriculada del Colegio de Técnicos de la Provincia de Buenos Aires (CTPBA). Mat. N° 45605.
Realización de DCI para Edesur y Edenor (residenciales, de obra y comerciales) para habilitación de medidores o para cambio de tarifa.
Informe técnico para bomberos (también llamada Memoria Técnica Descriptiva).
Protocolo de medición de puesta a tierra RES SRT 900/15.
Se le ofrece al cliente la confianza, garantía y respaldo que da Mercadolibre para la realización de todos los servicios.
El pago es la mitad al inicio y la otra a la entrega de la documentación aprobada.
Se acepta pago en efectivo o tarjeta mediante Mercadopago.');

insert into serviceProviders VALUES (4,'MANOS A LA OBRA, ES UNA EMPRESA DE SERVICIOS INTEGRAL PENSADA TANTO PARA PARTICULARES, CONSORCIOS,EMPRESAS O LOCALES, VOLCANDO NUESTRA EXPERIENCIA EN PLOMERÍA, ALBAÑILERÍA, PINTURA, GAS, ELECTRICIDAD DE AÑOS AL SERVICIO DEL CLIENTE.
MANOS A LA OBRA CUENTA CON UN EQUIPO DE PROFESIONALES ALTAMENTE CAPACITADOS DONDE LA CONFIANZA, SERIEDAD, BUENOS PRECIOS Y EL CUMPLIMIENTO DE LOS PLAZOS ESTABLECIDOS SON NUESTRA PREMISA.
EN DICHO PROYECTO SUMAMOS LA EXPERIENCIA ADQUIRIDA COMO CONSTRUCTORES DE VIVIENDAS Y LA INCORPORACIÓN DE ESPECIALISTAS DE LOS DIFERENTES GREMIOS, COMO SER ALBAÑILES, PLOMEROS, PINTORES, ELECTRICISTAS, GASISTAS ETC.
NUESTRA ATENCIÓN SIEMPRE VA A SER PERSONALIZADA Y DE ALTÍSIMA CALIDAD.
AL ENCARAR ÉSTE NOVEDOSO SISTEMA INTEGRAL DE ASISTENCIA NOS COMPROMETEMOS A BRINDARLE DENTRO DE LO POSIBLE, LA SOLUCIÓN RÁPIDA Y EFICIENTE A SUS NECESIDADES.
POR TAL MOTIVO QUEREMOS PRESENTARNOS COMO UNA ALTERNATIVA MÁS A LA HORA DE CONTRATAR UNO DE LOS SERVICIOS OFRECIDOS POR NOSOTROS.
TODOS NUESTROS PROFESIONALES CUENTAN CON UNA AMPLÍSIMA EXPERIENCIA CADA UNO EN SU RUBRO Y AQUEL QUE SE REQUIERA, ESTÁ DEBIDAMENTE MATRICULADO.
SOLAMENTE NOS RESTA SALUDARLOS MUY CORDIALMENTE.
LAS MANOS A LA OBRA.');

insert into serviceProviders VALUES (5,'Ofrecemos el service de Gasista Matriculado.');

insert into serviceProviders VALUES (6,'Soy Sergio, tengo 56. Trabaje de ingeniero quimico durante 30 años, ahora me decido a la pinturería, desde 2012. Mandame un mensaje para que te presupuesto un trabajo.
Te garantizamos:
- EXCELENCIA EN EL TRABAJO
- PRESUPUESTO SIN CARGO
- PRECIOS ACCESIBLES
- FACTURA
- PUNTUALIDAD
');

insert into serviceProviders VALUES (7,'Diseñamos y Fabricamos todo tipo de muebles a medida para hogares y proyectos comerciales.
Trabajamos con todo tipo de materiales y adaptamos el presupuesto a cada necesidad.');

insert into serviceProviders VALUES (8,'PLOMERIA, GAS Y DESTAPACIONES

PLOMERO-GASISTA MATRICULADO

CONSULTAS ÚNICAMENTE DE 9 A 18 HS.
URGENCIAS DESDE LAS 18HS EN ADELANTE

Atendemos capital federal y zona norte.
Trabajos garantidos. Realizamos factura consumidor final.
5434 - 8435
15 - 5320 - 3485
15 - 3641 - 3641
Llame sin compromiso, su consulta no molesta. Muchas gracias!');

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

insert into serviceProviders VALUES (10,'Soy Mario y tengo 62 años. Trabajo de herrero hace 20 años. Su consulta no es molestía, por favor pregunte!!!');

insert into serviceProviders VALUES (11,'Mi nombre es marcelo lynch y soy plomero matriculado de colegio tecnico. Tengo 45 años y me dedico a la plomería desde 1990. Por favor no dude en consultar el presupuesto para su problema. Somos un equipo de 3 para arreglar su problema rápidamente');

insert into serviceProviders VALUES (12,'LR Destapaciones es una empresa del grupo LIGHTROAD especialmente creada para atender las necesidades de los pequeños clientes donde sabemos la importancia de brindar respuestas inmediatas, prestando un servicio de alta calidad a un bajo costo. Desde hace 8 años nos dedicamos a trabajos de limpieza, destapaciones, diagnóstico y reparación de cañerías cloacales y pluviales..
Desde nuestros comienzos nos focalizamos en la calidad de prestación de servicios relacionados al diagnóstico de cañerías detectando errores de tendido, pendiente, deformaciones, roturas, rajaduras, obstrucciones y todo tipo de anomalías que presenten los caños.
Video inspección
Utilizada para el diagnóstico de problemas en cañerías
equipos de video inspección LightRoad
Su objetivo es diagnosticar problemas y desperfectos. Su utilización permite ahorrar costos de reparación y aumenta la precisión y el tiempo de ejecución.');

insert into serviceProviders VALUES (13,'ME PODES MANDAR FOTOS POR WHATSAPP AL 1157638937 INDICANDO LA ZONA Y EL TRABAJO A REALIZAR Y TE PASO UN PRESUPUESTO APROXIMADO.
SI NECESITAS UN PRESUPUESTO A TU DOMICILIO, LA VISITA TIENE UN VALOR DE $350.
TRABAJOS CON GARANTÍA, RESPONSABILIDAD Y SEGURIDAD.
PRECIOS ACCESIBLES. CONSULTAS PRESUPUESTOS AL 15-5763-8937 NICOLAS.');

insert into serviceProviders VALUES (14,'Siguiendo la tradición de mi padre, carpintero, hoy está en marcha nuestra empresa de carpintería. Nos dedicamos a la Ebanistería Fina, Muebles de Hogar y Muebles en comercio. Contamos con taller propio que nos permite hacer trabajos de carpintería grandes o a menor escala. Aparte de crear muebles modernos, nuestro trabajo de carpintería va desde diseño de muebles a medida hasta restauraciones de muebles antiguos.
Todos estos años de experiencia, lo volcamos en la madera y le damos formas a los muebles de tu vida.');

insert into serviceProviders VALUES (15,'En AV pintamos su casa, departamento, PH, estacionamientos, etc. Buscamos algo más que simplemente ofrecerle la mejor relación calidad - precio, buscamos su satisfacción asesorándole y ofreciéndole siempre la mejor solución. En AV no queremos que se preocupe de nada, solo de disfrutar del cambio de su nuevo espacio, nuestro servicio a particulares lo realizamos siempre adaptándonos a las necesidades de nuestros clientes.');

insert into serviceProviders VALUES (16,'Electricista matriculado');


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

insert into aptitudes VALUES (2,2,2,'Nuestro servicio de aplicación de pinturas y revestimientos: Especializado en Empapelados- importados - nacionales, guardas, molduras, alfombras, Satinados, Pátinas, Tratamientos de madera, Pátinas sobre muebles e Impermeabilizaciones.
Este servicio, este emprendimiento; fue creada en el año 2007 contamos con un amplio recorrido brindando servicios. Para dar respuesta a una demanda cada vez más exigente de profesionales en el rubro.
Pida un presupuesto con o sin materiales; cuéntenos su problema o inquietud y los asesoraremos para llegar al objetivo deseado: Que reúna seriedad, confianza, y excelente precio.');

insert into aptitudes VALUES (3,3,3,'Planos Electromecánicos
Habilitación de Ascensores
Planos Eléctricos
Factibilidad de suministro
Gestión de Energía
Protocolo de Puesta a Tierra
Seguridad en el ámbito laboral ART. Resolución 900/15');

insert into aptitudes VALUES (4,4,4,'REALIZAMOS TODO TIPO DE TRABAJOS DE PLOMERIA Y GAS
REPARACIÓN DE ARTEFACTOS ( INODOROS, BIDET, LAVATORIO, DUCHAS, CALEFONES DE TODAS LAS MARCAS, TERMOTANQUES, ETC. )
CAMBIO DE CAÑERIAS EN TODO TIPO DE MATERIALES. SOLDADURAS PARA CAÑOS DE PLOMO E HIDRO BRONCE.
TRABAJOS DE ALBAÑILERIA Y COLOCACION DE CERAMICAS Y PORCELANATO.');

insert into aptitudes VALUES (5,5,5,'Nos dedicamos a la instalacion de cocinas, estufas, calderas, calefones, termotanques, plomeria general, etc
Contamos con personal matriculado.
Damos garantia escrita de nuestros trabajos.
Realizamos planos y habilitaciones.
NO SE DAN PRESUPUESTOS EN ESTE MEDIO, COMUNICARSE A EL NUMERO DE NUESTRO PERFIL. MUCHAS GRACIAS.
Solamente realizamos trabajos en CAPITAL FEDERAL.
http://www.rincongas.com.ar/');

insert into aptitudes VALUES (6,6,2,'Incluye

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

insert into aptitudes VALUES (7,7,1,'Realizamos: Interiores y frentes de placards Muebles para LCD y Led Alacenas y Bajo mesadas Vanitorys Muebles para chicos Stands Muebles para oficinas Bibliotecas Mesas ratonas Muebles para Playrooms Respaldos y Mesas de luz Reposeras Pergolas y Decks
Y todo lo que necesites...siempre cumpliendo lo convenido, asesorándote para lograr el mejor aprovechamiento del espacio y entregando en los plazos acordados.
Visita nuestro sitio web: www.tocamaderamuebles.com.ar');

insert into aptitudes VALUES (8,8,4,'No rompa su casa!!! Limpiamos y desobstruimos cañerías de agua caliente y fría con productos desoxidantes importados y presión regulada e inducida. Instalaciones de agua y desagües. Instalaciones y reparaciones en Termofusion, polipropileno, galvanizado, hidrobronz, plomo, etc. Trabajos en altura con silleta, armado y amurado de colectores y caños maestros exteriores.
Todo tipo de arreglos de plomeria... Fugas de agua o desagües, mochilas, depósitos, flexibles, monocomandos, canillas, limpieza de tanques, bombas de todo tipo y marca, válvulas de inodoro, service de cualquier tipo de artefacto, etc, etc.');

insert into aptitudes VALUES (9,8,5,'Matriculado de 1ra categoría - Matriculado en combustión - Detecciones de fugas con instrumentos de presicion.
Trabajamos en termofusion, epoxi y galvanizado. Trabajos en altura con silleta, tendido y amurado de caños en vacío, salidas de gases a los 4 vientos. Instalaciones, planos habilitaciones, pruebas de hermeticidad.
Arreglos e instalaciones de todo tipo de artefactos a gas... calefones, termotanques, cocinas, calderas, estufas, anafes, artefactos industriales y hogareños.
Destapamos todo tipo de cañerías... cloacales, pluviales, piletas de patio, balcones, todo tipo de rejillas. Lustrado de cañerías. desagotes de sótanos y cámaras.');

insert into aptitudes VALUES (10,9,3,'
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

insert into aptitudes VALUES (11,10,6,'
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

insert into aptitudes VALUES (12,11,4,'Ofrecemos servicios de destapación de cañerías pluviales, cloacas, piletas de cocina y baños, destapación de cañerías con máquinas mecánicas y también Hidrocinéticas. Limpieza y cepillado de cañerías, máquinas destapadoras y desobstructoras únicas en el país. Destapaciones de cloacas y cañerías, piletas de cocinas, baños e inodoros, piletas de patio y lavaderos, destapaciones de rejillas, desobtrucción de columnas de desagüe pluvial, columnas de agua fria y caliente.
Realizamos reparación en instalaciones sanitarias agua, desagües (cloacales y pluviales) e instalaciones de Gas natural.
Atendemos destapaciones de urgencias las 24 horas del día, incluso Sábados, Domingos y feriados. Destapaciones en Capital Federal (vamos a todos los barrios) y alrededores de Gran Buenos Aires, zona norte, zona sur, zona oeste.
MEJORAMOS CUALQUIER PRESUPUESTO QUE TENGA EN ZONA SUR!!!');

insert into aptitudes VALUES (13,12,5,'- Limpieza de ramales de cocina,baño, pileta y lavadero.
- Limpieza de columnas pluviales
- Limpieza de columnas de baño
- Limpieza de columnas de lavadero
- Limpieza cámaras cloacales
- Limpieza de colectoras cloacales
-Limpieza de conductos de AIRE ACONDICIONADO');

insert into aptitudes VALUES (14,13,6,'ARREGLOS DE HERRERÍA Y SOLDADURA A DOMICILIO. GRAN EXPERIENCIA EN EL RUBRO.
REALIZO ARREGLOS DE ESCALERAS, BARANDAS, BISAGRAS, PUERTAS Y MARCOS, VENTANAS, PORTONES, PARRILLAS, REJAS, MESAS, SILLAS, ESTANTERÍAS.');

insert into aptitudes VALUES (15,14,1,'Muebles a medida. Cualquier idea de tu mente, la plasmamos en tus muebles.
Restauración. Restauramos tus muebles para que vuelvan a cobrar vida.
Replica de muebles antiguos. La replica consiste desde la forma hasta la forma de confección.
Muebles modernos. Nuestro taller nos permite jugar con la madera para lograr cualquier tipo de forma y combinar materiales para darle un estilo moderno a tus muebles.
Ademas…
Armarios a medida, pisos, mesas, escritorios a medida, muebles para la cocina, techos, revestimientos, mesas, carpintería para exteriores y más.');

insert into aptitudes VALUES (16,15,2,'Realizamos cualquier tipo de aplicación, Pintura Plástica, Esmalte, Semilacado, Lacado, Veneciano, Trapeados, Gotele, Pintura epoxi, y más. Todo lo que necesita para transformar o rehabilitar su casa, departamento, PH, estacionamientos, etc.
');

insert into aptitudes VALUES (17,16,3,'dci, armado de pilar, cableados, colocacion de artefactos, tramites en edenor.
hogar - comercio - obra - industria
requisitos para pedir dci o habilitacion de medidor monofasico:
podes enviar todos los papeles por whasapp.
se cobra 50% al iniciar el tramite y 50% al entregar el documento terminado');

insert into reviews VALUES (1,2,1,DEFAULT,5,5,5,5,5,'Bueno quería hacer una biblioteca horizontal larga abajo de una ventana, es algo que me venia debiendo desde hace meses, por lo que fue algo importante para mi. Ellos tuvieron cuidado y mucho respeto por esto en todo momento, entendieron lo importante que era para mi y me ayudaron a transformar mi vision en lo que queria, hablamos de posibilidades y si bien me trajeron una idea/proyecto. Estuvieron totalmente abiertos a modificarlo y hacer realidad lo que yo quería. . De verdad no tengo mas que palabras de agradecimiento. Muy recomendable. Yo no se si habra sido mucho o poco lo que pague, pero hay un extra cuando alguien te respeta desde el dia 1 y se toma el tiempo de explicarte, que es impagable. . Gracias!. ');
insert into reviews VALUES (2,3,1,DEFAULT,5,5,5,5,5,'Excelente atención, su trabajo es impecable. Rápidos, prolijos y profesionales. Les envié fotos del mueble, necesitaba desarmarlo y trasladarlo de un 7º a un 4º piso. Me presupuestaron el trabajo vía mail, sin vueltas ni "sorpresas" de ultimo momento. Los recomiendo totalmente y los tengo en cuenta para futuros trabajos, gente seria y que sabe lo que hace. Recomendable 100%.');
insert into reviews VALUES (3,4,1,DEFAULT,5,5,5,5,5,'Excelente el trabajo que hacen. La atención es mas que buena, me supieron asesorar que es lo que mas convenía par mi cocina. Quede feliz con el trabajo realizado. Una belleza todo y mi cocina quedo como nueva! gracias fc amoblamientos por su excelente trabajo.');
insert into reviews VALUES (4,5,1,DEFAULT,4,5,4,5,4,'Realmente he quedado mas que conforme con el servicio, me han realizado unos muebles para el cuarto de los niños y quedaron hermosos. Son excelentes desde la atención, el producto y la prolijidad de las terminaciones. Realmente super recomendables!.');
insert into reviews VALUES (5,6,1,DEFAULT,4,4,4,4,4,'Les pedì presupuesto por unos placares y me resolvieron todas la inquietudes. Me salio mucho mas barato y me dieron ideas. Fantasticas. Es una suerte. Encontrarlos.');
insert into reviews VALUES (6,7,1,DEFAULT,4,4,4,4,5,'Quedamos muy conformes con el mueble de dormitorio que nos hicieron. Sin dudas volveremos a llamarlos para hacer un mueble en el baño que tenemos pendiente.');
insert into reviews VALUES (7,8,1,DEFAULT,4,5,4,5,3,'La calidad de los materiales es inmejorable, vengo contratándolos desde hace dos años y siempre todo es excelente. Muy recomendable.');
insert into reviews VALUES (8,9,1,DEFAULT,4,4,4,4,5,'Muy buen servicio, desde la toma de medidas hasta la colocacion. Super atentos y prolijos.');

insert into reviews VALUES (9,3,2,DEFAULT,3,4,4,5,2,'La verdad que estoy muy conforme con el trabajo que realizaron. Cumplieron el precio estipulado en el presupuesto. Y algunos detalles que no les correspondia los hicieron de onda. Y dejaron todo limpio y ordenado.');
insert into reviews VALUES (10,4,2,DEFAULT,5,5,5,5,4,'Fue una experiencia estupenda. Los contrate sin estar en el departamento, un semipiso de casi 200 mts2. Me han hecho pintura de todos los ambientes, aberturas, reparon puertas y paredes y quedo todo mas que estupendo. Muy honestos. Excelente staff de gente. Recomendado absolutamente, gracias');
insert into reviews VALUES (11,5,2,DEFAULT,4,4,4,5,3,'Agradezco por el trabajo realizado de colocación de revestimiento en el frente de mi casa, excelente predisposición y responsable con su trabajo. Muchas gracias!.');

insert into reviews VALUES (12,10,3,DEFAULT,4,3,3,4,5,'Me hicieron una cocina a nueva en mi casa y quedo como se los pedi.');
insert into reviews VALUES (13,11,3,DEFAULT,5,5,5,5,4,'Acudieron rápido cuando los llamé y me pasaron un presupuesto accesible y lógico. Cumplieron con todo lo pactado y terminaron antes de lo que esperaba! un genio rodrigo, gracias leandro!.');
insert into reviews VALUES (14,12,3,DEFAULT,1,1,1,1,1,'Le puso cable de 05 a un aire , me arruino toda la casa.');
insert into reviews VALUES (15,13,3,DEFAULT,5,4,4,4,5,'Pedimos servicio urgente y fue resuelto en tiempo y forma. Muy amable el técnico federico. Muy recomendable.');
insert into reviews VALUES (16,14,3,DEFAULT,5,5,5,5,5,'Los llamé de urgencia un domingo a última hora. Vinieron bastante rápido y solucionaron el problema a un precio bastante razonable para el día y hora. Los recomiendo ampliamente.');
insert into reviews VALUES (17,15,3,DEFAULT,1,1,1,1,1,'Malhumorado, no cumplió con el trabajo pactado, para eso nos cobró $3800 quedó en hacerlo, y como me tenía que ir a trabajar, aprovecho para irse tras de mí dejando inconcluso el trabajo!!!!!. Nada recomendable, un estafador!!!!!.');

insert into reviews VALUES (18,16,4,DEFAULT,5,5,5,5,5,'Usuario 100% recomendable, se ve que conoce su trabajo, realizo la destapacion muy rápido y dejo todo muy prolijo. Tambien reviso el calefon y lo dejo funcionando, gracias.');
insert into reviews VALUES (19,1,4,DEFAULT,4,4,5,5,3,'Instalación de calefón: excelente trabajo, super puntual (llego un poco antes de lo pactado) y es el mas económico de mercadolibre. Da garantía y factura. Para recomendar.');
insert into reviews VALUES (20,2,4,DEFAULT,4,5,5,5,4,'Muy buen servicio y atencion, muy recomendable y seguire contando con sus servicios. Saludos paula.');
insert into reviews VALUES (21,3,4,DEFAULT,1,1,1,1,1,'Quedo con mi vieja en venir y nunca aparecio me indigna esas situaciones');

insert into reviews VALUES (22,4,5,DEFAULT,5,5,5,4,3,'Cumplio con el horario y con el precio acordado, y lo mas importante confirmo el problema q tenia, el cual era negado por el "gasista"que realizo el trabajo. Tambien me dio tips para entender lo que se habia hecho mal y como solucionarlo,super recomendable.');
insert into reviews VALUES (23,5,5,DEFAULT,4,5,5,4,3,'La verdad muy conforme, se toma el tiempo para explicar cada detalle del trabajo que va a realizar. Cambio de lugar la llave de paso porque metrogas me lo requeria. Hizo el tramite y en 2 dias tenia gas de nuevo. Muy cumplidor y respetuoso. Seran tenidos en cuenta para futuros trabajos. Gracias!.');
insert into reviews VALUES (24,6,5,DEFAULT,5,5,5,4,3,'Hace unos meses los contrate para detectar una fuga de gas que tenia, ya habiendo probado con otros gasistas sin mucha suerte, javier lo pudo detectar con un aparato sin necesidad de andar rompiendo todo, por suerte! solucionado ese gran problema que tenia me realizaron una extencion de la red de gas para colocar una estufa la cual se coloco recientemente. Excelente trabajo, mas que conforme y agradecido a javier por lo profesional y macanudo a la hora de explicar y aconsejar sobre la solucion de dichos problemas.');
insert into reviews VALUES (25,7,5,DEFAULT,5,5,5,4,3,'Me resolvio una urgencia fuera de horario, con gran calidad de trabajo y mucho don de gente. Se que seguramente lo vuelva a necesitar para unas reformas mas adelante. No dudare en llamarlo con todo gusto. Recomiendo 100%');

insert into reviews VALUES (26,2,6,DEFAULT,5,5,5,5,5,'Me presupuesto por una biblioteca y una mesa de hierro y madera. Fue muy rapido y claro. En 10 dias habiles recibi el mueble en mi domicilio ya que habia pedido envio. Todo excelente y exactamente como lo pedi. Vovlere a comprar.');
insert into reviews VALUES (27,3,6,DEFAULT,4,5,3,4,4,'Le encargue a matias una tabla de trabajo de 0. 68 x 2. 00 x 0. 77 x 0. 03 mts, con 2 caballetes y una cajonera (3 cajones), en madera de eucalipto y barnizado. Excelente trabajo. Gracias!!.');
insert into reviews VALUES (28,8,6,DEFAULT,5,5,5,4,3,'Cambiamos la entrada de un local comercial, me aconsejo re bien, el trabajo quedo excelente! coordinamos muy rápido (y en ningún momento me pateó o plantó (antes de contactarlo llame a otros dos supuestamente recomendados que me plantaron) muy detallista y perfeccionista, sin ninguna duda lo volvería a llamar si necesito hacer alguna otra cosa. Super recomendable!!!.');
insert into reviews VALUES (29,9,6,DEFAULT,5,5,5,5,5,'Reparó una puerta de una cocina que da al exterior. Super profesional, la dejo como nueva. Recomiendo contratar su servicio.');
insert into reviews VALUES (30,1,6,DEFAULT,5,5,5,5,5,'Trabajo bien y rápido. Arreglo una parrilla de un quincho. Muchas gracias!!!!.');

insert into reviews VALUES (31,4,7,DEFAULT,5,4,4,5,4,'Excelente trabajo en muebles bajomesada en la cocina. Todo en tiempo y forma sin ningún tipo de problema');
insert into reviews VALUES (32,5,7,DEFAULT,5,5,5,5,4,'un genio, super amable y cumplidor con tiempos y calidad del producto. Le pedí un diseño, me lo presupuesto (muy buen precio), me dio una fecha y concretamos. El mueble quedo impecable! 10 puntos! tiene con cajones deslizantes y buena calidad de los materiales. En principio era un trabajo simple pero como había una falsa escuadra en el edificio, no me hizo ningún problema, se quedo sin vacilar hasta terminarlo. Quedo buenisimo! sin lugar a dudas lo voy a recomendar. Gracias');
insert into reviews VALUES (33,6,7,DEFAULT,5,5,5,5,4,'Excelente atencion y trabajo. Estuvo atento a cada detalle. La mesa de livng tiene una terminacion espectacular. Recomiendo sin dudas a marcelo. Muchas gracias!!. ');
insert into reviews VALUES (34,7,7,DEFAULT,4,4,4,4,4,'realizó tal cual le pedí los muebles para mi cocina. Responsable, prolijo, un señor! muy recomendable! muchas gracias');

insert into reviews VALUES (35,1,8,DEFAULT,5,5,5,4,3,'Todo 10 puntos. Muy amables y educados. Limpiaron el tanque de agua y repararon el calefon. Precio acorde y accesible. Muy agradecido. Serán recomendados.');
insert into reviews VALUES (36,2,8,DEFAULT,2,1,3,4,5,'Me vinieron a destapar un inodoro, estuvo 15 minutos y me cobro $3. 240. - una locura total. Su trabajo es totalmente incomprobable. Me pareció una estafa. Tengo la factura por si alguien la quiere.');
insert into reviews VALUES (37,3,8,DEFAULT,4,5,5,4,3,'Despues de 3 supuestos "plomeros" vinieran a ver el trabajo, llame a javier para ver que problema tenia la bomba presurizadora, no me vino con ningun cuento como los otros, me explico paso a paso el funcionamiento y que habia que hacer para repararla. Acepte el presupuesto y me la reparo en el dia, tenia los repuestos en el vehiculo, quede re conforme porque me lo soluciono rapido y barato, desarmo toda la bomba y me salio mucho mas barato que cambiarla y comprar otra. Eternamente agradecido. Saludos. Jose de caballito. Saludos!.');

insert into reviews VALUES (38,1,9,DEFAULT,5,5,5,4,3,'Realizo la ronovacion del cableado de toda la casa, colocando el tablero nuevo con varios circuitos. Estamos muy conformes con el trabajo y con la predisposición ante todos los pedidos. Saludos y muchas gracias.');
insert into reviews VALUES (39,2,9,DEFAULT,2,1,3,4,5,'Lo llamé para que venga a revisar unas cosas, el valor de la visita me pareció un exceso. $700. En parte el error fue mío por no preguntar de antemano el costo de la visita, pero nunca me imaginé que saldría ese valor. Aclaro que en la visita estuvo unos 40 min pero para diagnosticar en ningún momento se reparó nada y no se trataba de una emergencia ni nada. Estaba con luz y fue una visita programada. A su favor es amable en el trato y llegó puntual, pero desistí de hacer el trabajo con él porque ese costo de visita me hizo dudar si lo que me presupuestó también estaba inflado. Una lástima porque por el resto de las opiniones parece confiable, pero por lo menos tengan presente consultar cuánto cobra la vista.');
insert into reviews VALUES (40,3,9,DEFAULT,4,5,5,4,3,'Lo contacté el mismo día que necesitaba revise un problema en un departamento que debíamos entregar al día siguiente. Fue tarde a revisar, determino rápidamente el problema y nos dijo que debíamos comprar para solucionarlo. Volvió al día siguiente, acoplándose al horario nuestro y realizó el trabajo prolija y rápidamente. El pago de la mano de obra fue muy razonable. Excelente servicio, recomendable! muchas gracias!!.');
insert into reviews VALUES (41,4,9,DEFAULT,1,2,3,4,2,'No lo recomiendo. A favor de el, que llego a horario. Muy desprolijo, dejo todo sucio, además me dejo sin luz en el dormitorio y se fue. Me hizo comprar material eléctrico que no uso - debía volver para terminar, pero la experiencia me llevo a contratar a otro para que terminara el trabajo.');
insert into reviews VALUES (42,5,9,DEFAULT,4,4,4,4,3,'Lo citamos por un problema con las luces del fondo y la luz del lavadero. Solucionó todo en el momento y además hizo un par de arreglos en el interior de la casa, el precio fue justo y lo tenemos agendado como un profesional de confianza para llamar cada vez que sea necesario.');
insert into reviews VALUES (43,6,9,DEFAULT,5,5,5,5,5,'Todo perfecto! tenia un problema urgente de cableado. Lo llame y paso en el dia a verlo. Al dia siguiente ya estaba trabajando en el tema. Termino el trabajo en el dia, dandome una rapida solucion. Super recomendable! se pudo acomodar a mis horarios. ');
insert into reviews VALUES (44,7,9,DEFAULT,1,1,1,1,1,'Quede el dia anterior para que pase y presupueste un trabajo,me dijo que podia venir. Al dia siguiente lo espere y no solo no vino sino que tampoco aviso. Falte al laburo en vano. No lo recomiendo. Poco profesional y poco etico.');

insert into reviews VALUES (45,4,15,DEFAULT,4,4,4,5,5,'Excelente trabajo. Muy prolijo. Puntualidad y responsabilidad. Cumplió con los tiempos. Muy recomendable!.');
insert into reviews VALUES (46,5,15,DEFAULT,5,5,5,5,5,'Muy buen trabajo, cumplió con tiempos y calidad. Muy prolijas terminaciones. Muchas gracias. ');
insert into reviews VALUES (47,6,15,DEFAULT,4,4,4,5,5,'Excelente atención y mis muebles quedaron muy bien. Cumplió con los tiempos.');
insert into reviews VALUES (48,7,15,DEFAULT,4,4,4,4,4,'Muy buen trabajo y terminaciones. Puntual y responsable. Súper recomendable.');

insert into messages VALUES (1,2,6,2,6,'Hola Julio como estas te queria hacer una consulta por el tema de carpinteria',FALSE ,DEFAULT );
insert into messages VALUES (2,6,2,2,6,'Hola Florencia si que necesitas?',FALSE ,DEFAULT );
insert into messages VALUES (3,2,6,2,6,'Necesito hacer un aramrio para zapatillas',FALSE ,DEFAULT );

insert into messages VALUES (4,2,9,2,9,'Este tambien es un chat',FALSE ,DEFAULT );
insert into messages VALUES (5,9,2,2,9,'AAA mira que bueno',FALSE ,DEFAULT );
insert into messages VALUES (6,2,9,2,9,'Jajaja',FALSE ,DEFAULT );

INSERT INTO coordenates (pos,userid, lat, lng) SELECT 1,userid, -34.557176,-58.430436 FROM serviceProviders;
INSERT INTO coordenates (pos,userid, lat, lng) SELECT 2,userid, -34.575376,-58.403839 FROM serviceProviders;
INSERT INTO coordenates (pos,userid, lat, lng) SELECT 3,userid, -34.588696,-58.431428 FROM serviceProviders;

SELECT setval('users_userid_seq',               (SELECT MAX(userid)         from users));
SELECT setval('serviceTypes_serviceTypeId_seq', (SELECT MAX(serviceTypeId)  from serviceTypes));
SELECT setval('aptitudes_aptitudeId_seq',       (SELECT MAX(aptitudeId)     from aptitudes));
SELECT setval('appointments_appointmentId_seq', (SELECT MAX(appointmentId)  from appointments));
SELECT setval('temporaryImages_imageid_seq',    (SELECT MAX(imageid)        from temporaryImages));
SELECT setval('reviews_reviewId_seq',           (SELECT MAX(reviewId)        from reviews));
SELECT setval('messages_msgId_seq',             (SELECT MAX(msgId)        from messages));
SELECT setval('coordenates_coordId_seq',             (SELECT MAX(coordId)        from coordenates));
SELECT setval('userImages_imageId_seq',             (SELECT MAX(imageId)        from userImages));