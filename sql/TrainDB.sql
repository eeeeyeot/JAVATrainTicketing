CREATE TABLE userdata
(
	user_id		varchar(20) NOT NULL,
	user_pw		varchar(20) NOT NULL,
	uname		varchar(20),
	contact		varchar(15),
	register_date	DATE,
	CONSTRAINT pk_userdata PRIMARY KEY (user_id)
);

CREATE TABLE reservation 
(
	user_id			varchar(20) NOT NULL,
	ticket_id		number(20) NOT NULL,
	CONSTRAINT pk_reservation PRIMARY KEY (ticket_id),
	CONSTRAINT fk_reservation FOREIGN KEY (user_id)
	REFERENCES userdata (user_id)
);

CREATE TABLE Ticket (
	ticket_id		number(10) NOT NULL,
	deppland_place	varchar(20) NOT NULL,
	arrpland_place	varchar(20) NOT NULL,
	train_name		varchar(20),
	car_number		int,
	seat			varchar(30),
	personnel		int,
	deppland_time	DATE NOT NULL,
	arrpland_time	DATE NOT NULL,
	price			int NOT NULL,
	ticketing_day	DATE NOT NULL,
	ticket_type		int NOT NULL,
	term			int,
	effective_date	DATE,
	expiration_date	DATE,
	CONSTRAINT fk_ticket FOREIGN KEY (ticket_id)
	REFERENCES reservation (ticket_id)
);

CREATE TABLE fee
(
	passenger_type		varchar(20) NOT NULL,
	standing_price		int NOT NULL,
	seat_price			int NOT NULL,
	CONSTRAINT pk_fare PRIMARY KEY (passenger_type)
);