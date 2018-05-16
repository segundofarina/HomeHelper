drop table if EXISTS messages CASCADE ;
drop table if exists postAreas CASCADE;
drop table if exists posts CASCADE;
drop table if exists serviceProviders CASCADE;
drop table if exists serviceTypes CASCADE;
drop table if exists users CASCADE;
drop table if exists reviews CASCADE;
drop table if exists aptitudes CASCADE;
drop table if exists appointments CASCADE;
drop table if exists neighborhoods CASCADE;
drop table if exists workingzone CASCADE;

CREATE TABLE IF NOT EXISTS users (
  userid INTEGER IDENTITY PRIMARY KEY,
  username varchar(100) UNIQUE NOT NULL ,
  password varchar(100) NOT NULL,
  firstname varchar(100),
  lastname varchar(100),
  email varchar(100),
  phone varchar(100),
  image blob,
  address varchar(100),
  verified boolean
);

CREATE TABLE IF NOT EXISTS neighborhoods(
  ngid INTEGER IDENTITY PRIMARY KEY,
  ngname VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS serviceTypes (
  serviceTypeId INTEGER IDENTITY PRIMARY KEY,
  serviceName varchar(256)
);

CREATE TABLE IF NOT EXISTS serviceProviders(
  userId INTEGER REFERENCES users(userid) PRIMARY KEY,
  description VARCHAR(1000)

);

CREATE TABLE IF NOT EXISTS aptitudes(
  aptitudeId INTEGER IDENTITY PRIMARY KEY,
  userId INTEGER REFERENCES serviceProviders(userId),
  serviceTypeId INTEGER REFERENCES serviceTypes(serviceTypeId),
  description VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS reviews(
  userId INTEGER REFERENCES users(userId),
  aptitudeId INTEGER REFERENCES aptitudes(aptitudeId),
  reviewdate TIMESTAMP default CURRENT_DATE,
  quality INTEGER,
  cleanness INTEGER ,
  price INTEGER,
  punctuality INTEGER,
  treatment INTEGER,
  comment VARCHAR(1000),
  clientReview boolean DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS messages(
  userFrom INTEGER REFERENCES users(userId),
  userTo  INTEGER REFERENCES users(userId),
  message VARCHAR(10000),
  messageDate TIMESTAMP  default CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS appointments(
  appointmentId INTEGER IDENTITY PRIMARY KEY,
  userId INTEGER REFERENCES users(userId),
  providerId INTEGER REFERENCES serviceProviders(userId),
  serviceTypeId INTEGER REFERENCES serviceTypes(serviceTypeId),
  appointmentDate TIMESTAMP default CURRENT_DATE,
  address VARCHAR(10000),
  status VARCHAR(20),
  jobDescription VARCHAR(10000)
);

CREATE TABLE IF NOT EXISTS workingzones(
  ngId INTEGER REFERENCES neighborhoods(ngId),
  userId INTEGER REFERENCES serviceProviders(userId)
);


insert into users VALUES(1,'segundofarina','dulcedeleche','Segundo','Farina','segundofarina@me.com','1134373920','1010101010','cuba 2546',FALSE );
insert into users VALUES(2,'florcavallin','dulcedeleche','Florencia','Cavallin','fcavallin@itba.edu.ar','1140910035',null,'cuba 2546',FALSE);
insert into users VALUES(3,'tinchovictory','dulcedeleche','Martin','Victory','martin@victory.com.ar','1159540388',null,'cuba 2546',FALSE);
insert into users VALUES(4,'carlosrodriguez','dulcedeleche','Carlos','Rodriguez','carlosrod@gmail.com','1156984231',null,'cuba 2546',FALSE);
insert into users VALUES(5,'juliovelez','dulcedeleche','Julio','Velez','julitogallina@hotmail.com','1148526584',null,'cuba 2546',FALSE);
insert into users VALUES(6,'elnue','dulcedeleche','nuevo','usuario','julitogallina@hotmail.com','2323232323',null,'cuba 2546',FALSE);

insert into neighborhoods VALUES (1,'Belgrano');
insert into neighborhoods VALUES (2,'Palermo');
insert into neighborhoods VALUES (3,'Nuñez');
insert into neighborhoods VALUES (4,'Olivos');
insert into neighborhoods VALUES (5,'Puerto Madero');
insert into neighborhoods VALUES (6,'Parque Patricios');

insert into serviceProviders VALUES(3,'Soy Tincho Victory y no me tomo recreos');
insert into serviceProviders VALUES(4,'Soy Carlitos, trabajo todos los dias hasta las 11 de la mañana');
insert into serviceProviders VALUES(5,'Estudie en el balseiro y no me gusto');

insert into serviceTypes VALUES (1,'Carpintero');
insert into serviceTypes VALUES (2,'Pintor');
insert into serviceTypes VALUES (3,'Obrero');

insert into aptitudes VALUES (1,3,1,'Martin el carpintero');
insert into aptitudes VALUES (2,3,2,'Martinsulis tambien es Pintor');
insert into aptitudes VALUES (3,4,1,'Carlos el carpintero');
insert into aptitudes VALUES (4,4,3,'Carlos obrero');

insert into reviews VALUES (1,1,default,4,5,3,4,4,'Soy Segundo me encanto tu trabajo de carpinteria Martin',true);
insert into reviews VALUES (1,1,default,4,5,3,4,44,'Soy Segundo me encanto tu segundo trabajo de carpinteria Martin',true);
insert into reviews VALUES (2,2,default,4,5,3,4,4,'Soy Florencia me encanto el empapelado Martin',true);

insert into messages VALUES (2,5,'Hola Julio como estas te queria hacer una consulta por el tema de carpinteria',DEFAULT );
insert into messages VALUES (5,2,'Hola Florencia si que necesitas?',DEFAULT );
insert into messages VALUES (2,5,'Necesito hacer un aramrio para zapatillas',DEFAULT );

insert into messages VALUES (2,4,'Este tambien es un chat',DEFAULT );
insert into messages VALUES (4,2,'AAA mira que bueno',DEFAULT );
insert into messages VALUES (2,4,'Jajaja',DEFAULT );


insert into appointments VALUES (1,1,3,1,DEFAULT ,'cuba 2546 6p','Pending','soy flor cavallin, tincho haceme un mueble nuevo');

insert into workingzones VALUES (1,3);
insert into workingzones VALUES (2,3);
insert into workingzones VALUES (3,3);




