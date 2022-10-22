BEGIN TRANSACTION;

drop table if exists night;
drop table if exists murder;
drop table if exists game_card;
drop table if exists card_type;
drop table if exists vision_player;
drop table if exists vision_card;
drop table if exists player;
drop table if exists users;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

INSERT INTO users (username,password_hash,role) VALUES ('ghost','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('psychic1','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('psychic2','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('psychic3','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');

INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');



create table night(
	game serial, 
	night int not null,
	phase int not null,
	number_of_psychics int not null,
	
	constraint pk_game primary key(game)
);

insert into night (night, phase, number_of_psychics) values (0,0,3);

create table player(
	player_id serial,
	role_id int not null,
	psychic_level int not null,
	guesses_left int not null,
	investigation_phase int,
	
	constraint pk_player_id primary key(player_id),
	constraint fk_player_id FOREIGN key(player_id) REFERENCES users(user_id)
);

create table card_type(
	card_type_id serial, 
	description varchar(16),
	
	constraint pk_card_type_id primary key(card_type_id)
);

create table game_card(
	game_card_id serial, 
	card_type_id int,
	img_url varchar(64),
	
	constraint pk_game_card_id primary key (game_card_id),
	constraint fk_card_type_id foreign key(card_type_id) references card_type(card_type_id)
);


create table murder(
	murder_id serial,
	player_id int, 
	person_card_id int, 
	location_card_id int,
	weapon_card_id int,
	
	constraint pk_murder_id primary key(murder_id),
	constraint fk_player_id foreign key(player_id) references player(player_id),
	constraint fk_person_card_id foreign key(person_card_id) references game_card(game_card_id),
	constraint fk_location_card_id foreign key(location_card_id) references game_card(game_card_id),
	constraint fk_weapon_card_id foreign key(weapon_card_id) references game_card(game_card_id)
);

create table vision_card(
	vision_card_id serial, 
	zone varchar(16),
	img_url varchar(64),
	
	constraint pk_vision_card_id primary key (vision_card_id),
	constraint chk_zone check (zone in ('deck', 'hand', 'discard', 'player1', 'player2', 'player3', 'player4'))
);

insert into vision_card (zone, img_url)
values ('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),
('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),
('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),
('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),
('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),
('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),
('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),
('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),
('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),
('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),
('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url');

create table vision_player(
	player_id int, 
	vision_card_id int,
	investigation_phase int,
	
	constraint pk_player_id_vision_id primary key(player_id, vision_card_id), 
	constraint fk_player_id foreign key(player_id) references player(player_id),
	constraint fk_vision_card_id foreign key(vision_card_id) references vision_card(vision_card_id)
);

COMMIT TRANSACTION;
