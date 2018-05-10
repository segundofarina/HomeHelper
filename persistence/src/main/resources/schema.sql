CREATE TABLE IF NOT EXISTS users (
  userid SERIAL PRIMARY KEY,
  username varchar(100) UNIQUE NOT NULL ,
  password varchar(100) NOT NULL,
  firstname varchar(100),
  lastname varchar(100),
  email varchar(100),
  phone varchar(100),
  image BLOB
);

CREATE TABLE IF NOT EXISTS serviceTypes (
  serviceTypeId SERIAL PRIMARY KEY,
  serviceName varchar(256)
);

CREATE TABLE IF NOT EXISTS serviceProviders(
  userId INTEGER REFERENCES users(userid) PRIMARY KEY,
  description TEXT
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
  quality INTEGER CHECK(quality > 0 AND  quality < 6),
  cleanness INTEGER CHECK(cleanness > 0 AND cleanness < 6),
  price INTEGER CHECK(price > 0 AND price < 6),
  punctuality INTEGER CHECK(punctuality > 0 AND punctuality < 6),
  treatment INTEGER CHECK(treatment > 0 AND treatment < 6),
  comment TEXT
);
CREATE TABLE IF NOT EXISTS messages(
  userFrom INTEGER REFERENCES users(userId),
  userTo  INTEGER REFERENCES users(userId),
  message VARCHAR(10000),
  messageDate TIMESTAMP  default CURRENT_DATE
);
