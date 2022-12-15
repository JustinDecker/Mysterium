BEGIN TRANSACTION;

drop table if exists player_prediction;
drop table if exists murders;
drop table if exists game_card;
drop table if exists card_type;
drop table if exists vision_player;
drop table if exists visions;
drop table if exists players;
drop table if exists games;
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

--test data, remove after
INSERT INTO users (username,password_hash,role) VALUES ('ghost','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('psychic1','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('psychic2','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('psychic3','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');

INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');


create table games(
	game_id serial, 
	night int not null,
	phase int not null,
	number_of_psychics int not null,
	
	constraint pk_game_id primary key(game_id)
);

insert into games (night, phase, number_of_psychics) values (0,0,3);

create table players(
	player_id int unique,
	game_id int not null,
	role varchar not null,
	psychic_level int not null,
	remaining_guesses int not null,
	investigation_phase int,
	current_guess int,
	
	constraint pk_player_id primary key(player_id),
	constraint fk_game_id foreign key(game_id) references games(game_id),
	constraint fk_player_id FOREIGN key(player_id) REFERENCES users(user_id),
	constraint chk_role check (role in ('ghost', 'psychic'))
);

--test data, remove after
insert into players (player_id, role, game_id, psychic_level, remaining_guesses, investigation_phase, current_guess)
values (1, 'ghost', 1, 0, 0, 0, -1), (2, 'psychic', 1, 0, 4, 0, -1), (3, 'psychic', 1, 0, 4, 0, -1), (4, 'psychic', 1, 0, 4, 0, -1);

create table card_type(
	card_type_id serial, 
	description varchar(16),
	
	constraint pk_card_type_id primary key(card_type_id)
);
insert into card_type (description) values ('person'),('location'),('weapon');

create table game_card(
	game_card_id serial, 
	card_type_id int,
	img_url varchar(64),
	zone varchar(16),
	
	constraint pk_game_card_id primary key (game_card_id),
	constraint fk_card_type_id foreign key(card_type_id) references card_type(card_type_id),
	constraint chk_zone check (zone in ('deck', 'play'))
);
insert into game_card (card_type_id, img_url, zone) values 
(1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'), (1,'placeholder', 'deck'),
(2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'), (2,'placeholder', 'deck'),
(3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck'), (3,'placeholder', 'deck');

create table murders(
	murder_id serial,
	player_id int, 
	person_card_id int, 
	location_card_id int,
	weapon_card_id int,
	
	constraint pk_murder_id primary key(murder_id),
	constraint fk_player_id foreign key(player_id) references players(player_id),
	constraint fk_person_card_id foreign key(person_card_id) references game_card(game_card_id),
	constraint fk_location_card_id foreign key(location_card_id) references game_card(game_card_id),
	constraint fk_weapon_card_id foreign key(weapon_card_id) references game_card(game_card_id),
	constraint chk_person_card check (person_card_id >= 1 and person_card_id <= 18),
	constraint chk_location_card check (location_card_id >= 19 and location_card_id <= 36),
	constraint chk_weapon_card check (weapon_card_id >= 37 and weapon_card_id <= 54)
);

create table visions(
	vision_id serial, 
	zone varchar(16),
	img_url varchar(64),
	
	constraint pk_vision_id primary key (vision_id),
	constraint chk_zone check (zone in ('deck', 'hand', 'discard', 'player'))
);

--test data, update img_url and zone of first three
insert into visions (zone, img_url)
values ('player', 'placeholder_url'),('player', 'placeholder_url'),('player', 'placeholder_url'),('player', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),('deck', 'placeholder_url'),
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
	vision_id int unique,
	
	constraint pk_player_id_vision_id primary key(player_id, vision_id), 
	constraint fk_player_id foreign key(player_id) references players(player_id),
	constraint fk_vision_id foreign key(vision_id) references visions(vision_id)
);

--test data, delete
insert into vision_player values (2,1),(3,2),(4,3),(4,4);

create table player_prediction(
	player_id int,
	foreign_player_id int,
	prediction boolean,
	
	constraint pk_player_id_foreign_player_id primary key (player_id, foreign_player_id),
	constraint fk_player_id foreign key(player_id) references players(player_id),
	constraint fk_foreign_player_id foreign key(foreign_player_id) references players(player_id),
	constraint chk_player_id_cant_equal_foreign_player_id check (player_id != foreign_player_id)
);

insert into player_prediction values (2,3,true);

COMMIT TRANSACTION;
