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

CREATE TABLE fare
(
	passenger_type		varchar(20) NOT NULL,
	standing_price		int NOT NULL,
	seat_price			int NOT NULL,
	CONSTRAINT pk_fare PRIMARY KEY (passenger_type)
);

CREATE SEQUENCE ticketIncrement START WITH 1 INCREMENT BY 1 MAXVALUE 1000 CYCLE NOCACHE

DROP SEQUENCE ticketIncrement

DROP TABLE TICKET 

SELECT * FROM RESERVATION

SELECT * FROM TICKET

DELETE FROM RESERVATION  WHERE USER_ID = 'test'

DELETE FROM ticket WHERE TICKET_ID = 1

SELECT MIN(ticket_id) FROM TICKET

SELECT t.TICKET_ID 표번호, t.DEPPLAND_PLACE 출발역, t.ARRPLAND_PLACE 도착역, t.TRAIN_NAME 열차명, t.CAR_NUMBER 열차번호, t.SEAT 좌석, 
t.DEPPLAND_TIME 출발시간, t.ARRPLAND_TIME 도착시간, t.PRICE 가격, t.TICKETING_DAY 예매날짜, t.TICKET_TYPE 예매종류, t.TERM 기간, t.EFFECTIVE_DATE, t.EXPIRATION_DATE 
FROM TICKET t, RESERVATION r 
WHERE r.USER_ID = 'test' AND r.TICKET_ID = t.TICKET_ID




