CREATE TABLE IF NOT EXISTS PAINTING (
	PAINTING_ID integer not null auto_increment, 
	NAME varchar(255), 
	TYPE integer,
	primary key (PAINTING_ID)) ;

INSERT INTO PAINTING( PAINTING_ID, NAME, TYPE) VALUES
	(1, 'red', 0),
	(2, 'green', 1);