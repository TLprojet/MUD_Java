CREATE TABLE Player(
		Id Integer NOT NULL AUTO_INCREMENT,
		Room Integer NOT NULL,
		Hp Integer NOT NULL,
        Name Varchar (50) NOT NULL,
        Sins Varchar (50) NOT NULL,
        Maxhp Integer NOT NULL,
CONSTRAINT Player_PK PRIMARY KEY (Id)
);