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

CREATE TABLE RESERVATION_SEASON 
(
	user_id			varchar(20) NOT NULL,
	season_id		number(20) NOT NULL,
	CONSTRAINT pk_reservation_season PRIMARY KEY (season_id),
	CONSTRAINT fk_reservation_season FOREIGN KEY (user_id) 
	REFERENCES userdata (user_id)
)

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
	CONSTRAINT fk_ticket FOREIGN KEY (ticket_id)
	REFERENCES reservation (ticket_id)
);

CREATE TABLE SEASON_Ticket (
	season_id		number(10) NOT NULL,
	deppland_place	varchar(20) NOT NULL,
	arrpland_place	varchar(20) NOT NULL,
	term			int NOT NULL,
	effective_date	DATE NOT NULL,
	expiration_date	DATE NOT NULL,
	CONSTRAINT fk_season FOREIGN KEY (season_id)
	REFERENCES reservation_season (season_id)
)

SELECT * FROM tab

CREATE TABLE fare
(
	passenger_type		varchar(20) NOT NULL,
	standing_price		int NOT NULL,
	seat_price			int NOT NULL,
	CONSTRAINT pk_fare PRIMARY KEY (passenger_type)
);

CREATE SEQUENCE ticketIncrement START WITH 1 INCREMENT BY 1 MAXVALUE 1000 CYCLE NOCACHE

DROP TABLE Season

DROP SEQUENCE ticketIncrement

DROP TABLE TICKET

SELECT * FROM reservation_season

SELECT * FROM RESERVATION

SELECT * FROM SEASON_TICKET

SELECT * FROM TICKET

SELECT * FROM tab

DELETE FROM RESERVATION  WHERE USER_ID = 'test'

DELETE FROM ticket WHERE TICKET_ID = 1

SELECT MIN(ticket_id) FROM TICKET

SELECT t.TICKET_ID 표번호, t.DEPPLAND_PLACE 출발역, t.ARRPLAND_PLACE 도착역, t.TRAIN_NAME 열차명, t.CAR_NUMBER 열차번호, t.SEAT 좌석, 
t.DEPPLAND_TIME 출발시간, t.ARRPLAND_TIME 도착시간, t.PRICE 가격, t.TICKETING_DAY 예매날짜, t.TICKET_TYPE 예매종류, t.TERM 기간, t.EFFECTIVE_DATE, t.EXPIRATION_DATE 
FROM TICKET t, RESERVATION r 
WHERE r.USER_ID = 'test' AND r.TICKET_ID = t.TICKET_ID

SELECT Seat FROM ticket t WHERE t.TRAIN_NAME = 'KTX' AND t.CAR_NUMBER = 101
AND DEPPLAND_TIME BETWEEN to_date('202011300515', 'yyyyMMddHH24MI') AND to_date('202011300610', 'yyyyMMddHH24MI')

SELECT Seat FROM ticket t WHERE t.TRAIN_NAME = 'KTX' AND t.CAR_NUMBER = 101
AND DEPPLAND_TIME BETWEEN to_date('202012010515', 'yyyyMMddHH24MI') AND to_date('202012010610', 'yyyyMMddHH24MI')

SELECT seat FROM TICKET WHERE train_name = 'KTX' and car_number = 101

SELECT t.TICKET_ID, t.DEPPLAND_PLACE, t.ARRPLAND_PLACE, t.TRAIN_NAME, t.CAR_NUMBER, t.SEAT, t.PERSONNEL, t.DEPPLAND_TIME, t.ARRPLAND_TIME, t.PRICE, t.TICKETING_DAY FROM TICKET t, RESERVATION r WHERE r.USER_ID = 'test' AND r.TICKET_ID = t.TICKET_ID

SELECT s.SEASON_ID, s.DEPPLAND_PLACE, s.ARRPLAND_PLACE, s.TERM, s.EFFECTIVE_DATE, s.EXPIRATION_DATE FROM SEASON s, RESERVATION_SEASON r WHERE s.SEASON_ID = r.SEASON_ID AND r.USER_ID = 'test'

INSERT INTO season_ticket VALUES (1, '서울', '대전', 10, to_date('202012071624', 'yyyyMMddHH24MI'), to_date('202012171624', 'yyyyMMddHH24MI'))