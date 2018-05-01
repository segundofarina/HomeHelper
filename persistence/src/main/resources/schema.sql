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
  userId INTEGER REFERENCES users(userid) PRIMARY KEY
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
