CREATE TABLE IF NOT EXISTS USER_FIGURINE (
	USER_FIGURINE_ID integer not null auto_increment,
	DESCRIPTION varchar(255),
    TITLE varchar(255),
    VISBILITY bit,
    FIGURINE_ID integer,
    USER_ID integer,
    primary key (USER_FIGURINE_ID)
);

CREATE TABLE IF NOT EXISTS USER_FIGURINE_listPainting (
	UserFigurine_USER_FIGURINE_ID integer not null,
    listPainting_PAINTING_ID integer not null
);

alter table USER_FIGURINE add constraint FKgkgs6s1083m0rpgy02608xxmr foreign key (FIGURINE_ID) references FIGURINE (FIGURINE_ID);
alter table USER_FIGURINE add constraint FKr370nflarf9ia29echot82o6e foreign key (USER_ID) references USER (USER_ID);
alter table USER_FIGURINE_listPainting add constraint FKi0nyfvji2vpttoiykwh6wu2ea foreign key (listPainting_PAINTING_ID) references PAINTING (PAINTING_ID);
alter table USER_FIGURINE_listPainting add constraint FK7wfd8072pp4kjsejfb69grkep foreign key (UserFigurine_USER_FIGURINE_ID) references USER_FIGURINE (USER_FIGURINE_ID);

INSERT INTO USER_FIGURINE( USER_FIGURINE_ID, TITLE, DESCRIPTION, VISBILITY, FIGURINE_ID, USER_ID) VALUES
	(1, 'title', 'Descripsion',1,1,1),
	(2, 'title', 'Descripsion',1,2,1),
    (3, 'title', 'Descripsion',1,1,2);
    
    INSERT INTO USER_FIGURINE_listPainting( UserFigurine_USER_FIGURINE_ID, listPainting_PAINTING_ID) VALUES
	(1, 1),
	(2, 1),
    (3, 1);