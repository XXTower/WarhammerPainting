CREATE TABLE IF NOT EXISTS USER (
	USER_ID integer not null auto_increment, 
	PASSWORD varchar(255), 
	USERNAME varchar(255), 
	primary key (USER_ID)) ;

INSERT INTO USER( USER_ID, USERNAME, PASSWORD) VALUES
	(1, 'test', 'test'),
	(2, 'test2', 'test2');
    
CREATE TABLE IF NOT EXISTS PAINTING (
	PAINTING_ID integer not null auto_increment, 
	NAME varchar(255), 
	TYPE integer,
	primary key (PAINTING_ID)) ;

INSERT INTO PAINTING( PAINTING_ID, NAME, TYPE) VALUES
	(1, 'red', 0),
	(2, 'green', 1);