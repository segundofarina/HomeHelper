drop table if exists postAreas;
drop table if exists posts;
drop table if exists serviceProviders;
drop table if exists serviceTypes;
drop table if exists users;

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
  description TEXT

);

CREATE TABLE IF NOT EXISTS posts (
  postId INTEGER IDENTITY PRIMARY KEY,
  title varchar(256),
  description VARCHAR(1000),
  serviceTypeId INTEGER REFERENCES serviceTypes(serviceTypeId),
  userId INTEGER REFERENCES serviceProviders(userid)
);


CREATE TABLE IF NOT EXISTS postAreas(
  postId INTEGER REFERENCES posts(postId),
  pin varchar(100),
  radius INTEGER
);

CREATE TABLE IF NOT EXISTS aptitudes(
  aptitudeId IDENTITY PRIMARY KEY,
  userId INTEGER REFERENCES users(userid),
  serviceTypeId INTEGER REFERENCES serviceTypes(serviceTypeId),
  description TEXT
);

CREATE TABLE IF NOT EXISTS reviews(
  userId INTEGER  REFERENCES users(userId),
  aptitudeId INTEGER REFERENCES aptitude(aptitudeId),
  reviewdate TIMESTAMP default CURRENT_DATE,
  rating INTEGER CHECK(rating > 0 AND rating < 6),
  comment TEXT
);

insert into users VALUES(1,'segundofarina','dulcedeleche','Segundo','Farina','segundofarina@me.com','1134373920');
insert into users VALUES(2,'florcavallin','dulcedeleche','Florencia','Cavallin','fcavallin@itba.edu.ar','1140910035');
insert into users VALUES(3,'tinchovictory','dulcedeleche','Martin','Victory','martin@victory.com.ar','1159540388');
insert into users VALUES(4,'carlosrodriguez','dulcedeleche','Carlos','Rodriguez','carlosrod@gmail.com','1156984231');
insert into users VALUES(5,'juliovelez','dulcedeleche','Julio','Velez','julitogallina@hotmail.com','1148526584');

insert into serviceProviders VALUES(4);
insert into serviceProviders VALUES(5);

insert into serviceTypes VALUES (1,'Carpintero');
insert into serviceTypes VALUES (2,'Pintor');

insert into posts VALUES (1,'Primer post','Alta experiencia en cosas de carpineria(?) WEEee',1,4);
insert into posts VALUES (2,'Segundo post de carlitos','Tambien hago trabajos de pintura loco',2,4);
insert into posts VALUES (3,'Carpinteria en zona norte','Expermientado trabajador educado. No como carlos',1,5);
insert into posts VALUES (4,'Trabajos de pintura en Vicente Lopez','Paredes interiores y exteriores y muebles',2,5);