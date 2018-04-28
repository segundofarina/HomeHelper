 CREATE TABLE IF NOT EXISTS users (
  userid INTEGER IDENTITY PRIMARY KEY,
   username varchar(100),
   password varchar(100),
   firstname varchar(100),
   lastname varchar(100),
   email varchar(100),
   phone varchar(100)
);