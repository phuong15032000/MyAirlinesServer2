drop database if exists MyAirlines;
create database MyAirlines;
use MyAirlines;
create table AirBrand(
airBrandId int not null auto_increment,
nameAirBrand varchar(45),
primary key (airBrandId)
);
create table AirCraft(
airCraftId int not null auto_increment,
airBrandId int,
airCraftName varchar(45),
model varchar(45),
seatNumber int,
primary key (airCraftId),
foreign key (airBrandId) references AirBrand(airBrandId)
);
create table Airport(
	airportId int not null auto_increment,
    airportName varchar(45),
    cityName varchar(45),
    primary key (airportId)
);
create table FlightRoute(
	routeId int not null auto_increment,
    departurePlaceId int ,
	arrivalPlaceId int ,
    standardPrice double,
    primary key (routeId),
	foreign key (departurePlaceId) references Airport(airportId),
	foreign key (arrivalPlaceId) references Airport(airportId)
);
create table Flight(
	flightId int not null auto_increment,
    departureTime DATETIME,
    arrivalTime DATETIME,
    totalSeat int ,
    availableSeat int ,
    orderedSeat int ,
    duration double,
    routeId int,
    airCraftId int ,
    primary key (flightId),
    foreign key (routeId) references FlightRoute(routeId),
    foreign key (airCraftId) references AirCraft(airCraftId)
);
create table admin(
adminId int not null auto_increment,
name varchar(45),
phoneNumber varchar(45),
identifyNumber varchar(45),
email varchar(45),
username varchar(45) not null,
password varchar(45) not null,
address varchar(45),
enabled TINYINT NOT NULL DEFAULT 1 ,
primary key (adminId)
);
insert into admin (name, phoneNumber, identifyNumber, email, username, password, address) values ('Tran Diep Phuong','0354892727','201835650','dphuong15032000@gmail.com','phuong15032000','15032000','Da Nang');

INSERT INTO `myairlines`.`airbrand` (`nameAirBrand`) VALUES ('Vietnamairlines');
INSERT INTO `myairlines`.`airbrand` (`nameAirBrand`) VALUES ('Vietjet');
INSERT INTO `myairlines`.`airbrand` (`nameAirBrand`) VALUES ('BambooAirway');

INSERT INTO `myairlines`.`aircraft` (`airBrandId`, `airCraftName`, `model`, `seatNumber`) VALUES ('1', 'VN001', 'B52', '200');
INSERT INTO `myairlines`.`aircraft` (`airBrandId`, `airCraftName`, `model`, `seatNumber`) VALUES ('1', 'VN002', 'B52', '200');
INSERT INTO `myairlines`.`aircraft` (`airBrandId`, `airCraftName`, `model`, `seatNumber`) VALUES ('1', 'VN003', 'B52', '200');
INSERT INTO `myairlines`.`aircraft` (`airBrandId`, `airCraftName`, `model`, `seatNumber`) VALUES ('2', 'VJ001', 'B52', '200');
INSERT INTO `myairlines`.`aircraft` (`airBrandId`, `airCraftName`, `model`, `seatNumber`) VALUES ('2', 'VJ002', 'B52', '200');
INSERT INTO `myairlines`.`aircraft` (`airBrandId`, `airCraftName`, `model`, `seatNumber`) VALUES ('2', 'VJ003', 'B52', '200');
INSERT INTO `myairlines`.`aircraft` (`airBrandId`, `airCraftName`, `model`, `seatNumber`) VALUES ('3', 'QH001', 'B52', '200');
INSERT INTO `myairlines`.`aircraft` (`airBrandId`, `airCraftName`, `model`, `seatNumber`) VALUES ('3', 'QH002', 'B52', '200');
INSERT INTO `myairlines`.`aircraft` (`airBrandId`, `airCraftName`, `model`, `seatNumber`) VALUES ('3', 'QH003', 'B52', '200');

INSERT INTO `myairlines`.`airport` (`airportName`, `cityName`) VALUES ('Tan Son Nhat', 'Thanh pho Ho Chi Minh');
INSERT INTO `myairlines`.`airport` (`airportName`, `cityName`) VALUES ('Noi Bai', 'Thu do Ha Noi');
INSERT INTO `myairlines`.`airport` (`airportName`, `cityName`) VALUES ('Da Nang', 'Thanh pho Da Nang');

INSERT INTO `myairlines`.`flightroute` (`departurePlaceId`, `arrivalPlaceId`, `standardPrice`) VALUES ('1', '2', '2000000');
INSERT INTO `myairlines`.`flightroute` (`departurePlaceId`, `arrivalPlaceId`, `standardPrice`) VALUES ('1', '3', '1500000');
INSERT INTO `myairlines`.`flightroute` (`departurePlaceId`, `arrivalPlaceId`, `standardPrice`) VALUES ('2', '1', '200000');
INSERT INTO `myairlines`.`flightroute` (`departurePlaceId`, `arrivalPlaceId`, `standardPrice`) VALUES ('2', '3', '1600000');
INSERT INTO `myairlines`.`flightroute` (`departurePlaceId`, `arrivalPlaceId`, `standardPrice`) VALUES ('3', '1', '1500000');
INSERT INTO `myairlines`.`flightroute` (`departurePlaceId`, `arrivalPlaceId`, `standardPrice`) VALUES ('3', '2', '1600000');

INSERT INTO `myairlines`.`flight` (`departureTime`, `arrivalTime`, `totalSeat`, `availableSeat`, `orderedSeat`, `duration`, `routeId`, `airCraftId`) VALUES ('2020-09-29 19:07:00', '2020-09-30 07:07:00', '200', '200', '0', '120', '1', '1');
INSERT INTO `myairlines`.`flight` (`departureTime`, `arrivalTime`, `totalSeat`, `availableSeat`, `orderedSeat`, `duration`, `routeId`, `airCraftId`) VALUES ('2020-09-30 20:00:00', '2020-09-30 21:30:00', '200', '200', '0', '120', '1', '1');
INSERT INTO `myairlines`.`flight` (`departureTime`, `arrivalTime`, `totalSeat`, `availableSeat`, `orderedSeat`, `duration`, `routeId`, `airCraftId`) VALUES ('2020-10-01 10:00:00', '2020-10-01 21:30:00', '200', '200', '0', '120', '2', '1');
INSERT INTO `myairlines`.`flight` (`departureTime`, `arrivalTime`, `totalSeat`, `availableSeat`, `orderedSeat`, `duration`, `routeId`, `airCraftId`) VALUES ('2020-09-29 05:00:00', '2020-09-30 5:30:00', '200', '200', '0', '120', '2', '1');
INSERT INTO `myairlines`.`flight` (`departureTime`, `arrivalTime`, `totalSeat`, `availableSeat`, `orderedSeat`, `duration`, `routeId`, `airCraftId`) VALUES ('2020-09-29 10:00:00', '2020-09-30 10:30:00', '200', '200', '0', '120', '2', '1');
INSERT INTO `myairlines`.`flight` (`departureTime`, `arrivalTime`, `totalSeat`, `availableSeat`, `orderedSeat`, `duration`, `routeId`, `airCraftId`) VALUES ('2020-09-29 21:00:00', '2020-09-30 21:30:00', '200', '200', '0', '120', '2', '1');

