CREATE TABLE IF NOT EXISTS users (
  userid SERIAL PRIMARY KEY,
  username varchar(100) UNIQUE NOT NULL ,
  password varchar(100) NOT NULL,
  firstname varchar(100) NOT NULL,
  lastname varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  phone varchar(100),
  image bytea,
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
  userId INTEGER REFERENCES serviceProviders(userId),
  serviceTypeId INTEGER REFERENCES serviceTypes(serviceTypeId),
  description varchar(10000) NOT NULL
);

CREATE TABLE IF NOT EXISTS reviews(
  reviewId SERIAL PRIMARY KEY,
  userId INTEGER  REFERENCES users(userId),
  aptitudeId INTEGER REFERENCES aptitudes(aptitudeId),
  reviewdate DATE default CURRENT_DATE,
  quality INTEGER CHECK(quality > 0 AND  quality < 6),
  cleanness INTEGER CHECK(cleanness > 0 AND cleanness < 6),
  price INTEGER CHECK(price > 0 AND price < 6),
  punctuality INTEGER CHECK(punctuality > 0 AND punctuality < 6),
  treatment INTEGER CHECK(treatment > 0 AND treatment < 6),
  comment varchar(10000) NOT NULL
);
CREATE TABLE IF NOT EXISTS messages(
  msgId SERIAL PRIMARY KEY,
  userFrom INTEGER REFERENCES users(userId),
  userTo  INTEGER REFERENCES users(userId),
  client INTEGER REFERENCES users(userId),
  provider INTEGER REFERENCES users(userId),
  message VARCHAR(10000),
  read BOOLEAN,
  messageDate TIMESTAMP  default CURRENT_TIMESTAMP
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


create TABLE if NOT EXISTS verifyUsers(
  userId INTEGER PRIMARY KEY REFERENCES users(userId),
  keyCode VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS neighborhoods(
  ngId SERIAL PRIMARY KEY,
  ngname VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS workingzones(
  workingZoneId SERIAL PRIMARY KEY,
  ngId INTEGER REFERENCES neighborhoods(ngId),
  userId INTEGER REFERENCES serviceProviders(userId)
);

CREATE TABLE IF NOT EXISTS temporaryImages (
  imageid SERIAL PRIMARY KEY,
  image   BYTEA
);

CREATE TABLE IF NOT EXISTS coordenates (
  coordId SERIAL PRIMARY KEY,
  userId INTEGER REFERENCES serviceProviders(userId) ,
  position INTEGER NOT NULL,
  lat DOUBLE precision NOT NULL,
  lng DOUBLE precision NOT NULL
);

