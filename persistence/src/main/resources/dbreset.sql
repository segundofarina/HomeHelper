drop table if EXISTS messages;
drop table if exists postAreas CASCADE;
drop table if exists posts CASCADE;
drop table if exists serviceProviders CASCADE;
drop table if exists serviceTypes CASCADE;
drop table if exists users CASCADE;
drop table if exists reviews CASCADE;
drop table if exists aptitudes CASCADE;

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
  description TEXT
);


CREATE TABLE IF NOT EXISTS posts (
  postId SERIAL PRIMARY KEY,
  title varchar(256),
  description TEXT,
  serviceTypeId INTEGER REFERENCES serviceTypes(serviceTypeId),
  userId INTEGER REFERENCES serviceProviders(userid)
);


CREATE TABLE IF NOT EXISTS postAreas(
  postId INTEGER REFERENCES posts(postId),
  pin varchar(100),
  radius INTEGER
);

CREATE TABLE IF NOT EXISTS aptitudes(
  aptitudeId SERIAL PRIMARY KEY,
  userId INTEGER REFERENCES serviceProviders(userId),
  serviceTypeId INTEGER REFERENCES serviceTypes(serviceTypeId),
  description TEXT
);

CREATE TABLE IF NOT EXISTS reviews(
  userId INTEGER  REFERENCES users(userId),
  aptitudeId INTEGER REFERENCES aptitudes(aptitudeId),
  reviewdate TIMESTAMP default CURRENT_DATE,
  rating INTEGER CHECK(rating > 0 AND rating < 6),
  comment TEXT
);
CREATE TABLE IF NOT EXISTS messages(
  userFrom INTEGER REFERENCES users(userId),
  userTo  INTEGER REFERENCES users(userId),
  message VARCHAR(10000),
  messageDate TIMESTAMP  default CURRENT_DATE
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


insert into aptitudes VALUES (1,4,2,'Muy buen pintor segundito, te felicito <3');


insert into messages VALUES (2,5,'Hola Julio como estas te queria hacer una consulta por el tema de carpinteria',DEFAULT );
insert into messages VALUES (5,2,'Hola Florencia si que necesitas?',DEFAULT );
insert into messages VALUES (2,5,'Necesito hacer un aramrio para zapatillas',DEFAULT );

insert into messages VALUES (2,4,'Este tambien es un chat',DEFAULT );
insert into messages VALUES (4,2,'AAA mira que bueno',DEFAULT );
insert into messages VALUES (2,4,'Jajaja',DEFAULT );