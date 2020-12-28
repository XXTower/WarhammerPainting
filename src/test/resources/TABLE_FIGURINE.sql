CREATE TABLE IF NOT EXISTS FIGURINE (
	FIGURINE_ID integer not null auto_increment,
	FACTION integer, 
	NAME varchar(255), 
	SUBFACTION integer, 
	primary key (FIGURINE_ID));
ALTER TABLE FIGURINE ADD CONSTRAINT UK_cwnjl1wjnc3rqqbvoimv1e8or unique (NAME);
INSERT INTO FIGURINE( FIGURINE_ID, NAME, FACTION, SUBFACTION) VALUES
	(1, 'test', 0, 0),
	(2, 'test2', 1, 2);