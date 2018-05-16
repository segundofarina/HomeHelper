CREATE TABLE IF NOT EXISTS users (
  userid SERIAL PRIMARY KEY,
  username varchar(100) UNIQUE NOT NULL ,
  password varchar(100) NOT NULL,
  firstname varchar(100),
  lastname varchar(100),
  email varchar(100),
  phone varchar(100),
  image bytea,
  address varchar(100)
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
  description varchar(10000)
);

CREATE TABLE IF NOT EXISTS reviews(
  reviewId INTEGER IDENTITY PRIMARY KEY,
  reviewdate TIMESTAMP default CURRENT_DATE,
  quality INTEGER,
  cleanness INTEGER ,
  price INTEGER,
  punctuality INTEGER,
  treatment INTEGER,
  comment VARCHAR(1000)
);
CREATE TABLE IF NOT EXISTS messages(
  userFrom INTEGER REFERENCES users(userId),
  userTo  INTEGER REFERENCES users(userId),
  message VARCHAR(10000),
  messageDate TIMESTAMP  default CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS appointments(
  appointmentId INTEGER SERIAL PRIMARY KEY,
  userId INTEGER REFERENCES users(userId),
  aptitudeId INTEGER REFERENCES aptitudes(aptitudeId),
  reviewId INTEGER REFERENCES reviews(reviewId) DEFAULT -1,
  appointmentDate TIMESTAMP default CURRENT_DATE,
  address VARCHAR(10000),
  status VARCHAR(20),
  jobDescription VARCHAR(10000)
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
  ngId INTEGER REFERENCES neighborhoods(ngId),
  userId INTEGER REFERENCES serviceProviders(userId)
);
