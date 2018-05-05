drop table if exists postAreas CASCADE ;
drop table if exists posts CASCADE;
drop table if exists serviceProviders CASCADE;
drop table if exists serviceTypes CASCADE;
drop table if exists users CASCADE;
drop table if exists aptitudes CASCADE;
drop table if exists reviews CASCADE;

CREATE TABLE IF NOT EXISTS users (
  userid INTEGER IDENTITY PRIMARY KEY,
  username varchar(100) UNIQUE NOT NULL ,
  password varchar(100) NOT NULL,
  firstname varchar(100),
  lastname varchar(100),
  email varchar(100),
  phone varchar(100)
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
  rating INTEGER,
  comment VARCHAR(1000)
);

insert into users VALUES(1,'segundofarina','dulcedeleche','Segundo','Farina','segundofarina@me.com','1134373920');
insert into users VALUES(2,'florcavallin','dulcedeleche','Florencia','Cavallin','fcavallin@itba.edu.ar','1140910035');
insert into users VALUES(3,'tinchovictory','dulcedeleche','Martin','Victory','martin@victory.com.ar','1159540388');
insert into users VALUES(4,'carlosrodriguez','dulcedeleche','Carlos','Rodriguez','carlosrod@gmail.com','1156984231');
insert into users VALUES(5,'juliovelez','dulcedeleche','Julio','Velez','julitogallina@hotmail.com','1148526584');

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

insert into reviews VALUES (1,1,default,4,'Soy Segundo me encanto tu trabajo de carpinteria Martin');
insert into reviews VALUES (1,1,default,4,'Soy Segundo me encanto tu segundo trabajo de carpinteria Martin');
insert into reviews VALUES (2,2,default,5,'Soy Florencia me encanto el empapelado Martin');
