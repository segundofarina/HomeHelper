CREATE TABLE IF NOT EXISTS users (
  userid INTEGER IDENTITY PRIMARY KEY,
  username varchar(100),
  password varchar(100),
  firstname varchar(100),
  lastname varchar(100),
  email varchar(100),
  phone varchar(100)
);

CREATE TABLE IF NOT EXISTS serviceTypes (
  serviceTypeId INTEGER IDENTITY PRIMARY KEY,
  serviceName varchar(256)
);

CREATE TABLE IF NOT EXISTS posts (
  postId INTEGER IDENTITY PRIMARY KEY,
  title varchar(256),
  description VARCHAR(1000),
  serviceTypeId INTEGER REFERENCES serviceTypes(serviceTypeId)
);


CREATE TABLE IF NOT EXISTS serviceProviders(
  userId INTEGER REFERENCES users(userid),
  postId INTEGER REFERENCES posts(postId)

);

CREATE TABLE IF NOT EXISTS postAreas(
  postId INTEGER REFERENCES posts(postId),
  pin varchar(100),
  radius INTEGER
);