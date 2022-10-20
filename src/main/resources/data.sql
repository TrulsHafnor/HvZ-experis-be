-- Game
INSERT INTO game (game_state, game_title, nw_lat, nw_lng, se_lat, se_lng)
VALUES ('REGISTRATION', 'Game one', '59.932792', '10.754136', '59.928405', '10.761050'),
       ('IN_PROGRESS', 'Game two', '59.938979', '10.776390', '59.930698', '10.780897'),
       ('COMPLETE', 'Game three', '59.925777', '10.787467', '59.923863', '10.792707');

-- Player
INSERT INTO player(bite_code, is_human, game_id, is_patient_zero)
    VALUES ('Hei1',true, 1, false),
       ('Hei2', false, 1, false),
       ('Hei3', true, 2, false),
       ('Hei4', false, 2, false),
       ('Hei5',true, 3, false),
       ('Hei6',false, 3, false),
       ('Hei7',true, 1, false),
       ('Hei8',false, 1, false),
       ('Hei9',false, 1, false),
       ('Hei10',false, 1, false),
       ('Hei11',false, 2, false),
       ('Hei12',false, 2, false),
       ('Hei13',false, 3, false),
       ('Hei14',false, 3, false),
       ('Sjekk1',true, 2, false),
       ('Sjekk2',true, 2, false),
       ('Sjekk3',true, 2, false),
       ('Sjekk4',true, 2, false),
       ('Sjekk5',true, 2, false),
       ('Sjekk6',true, 2, false),
       ('Sjekk7',true, 2, false),
       ('Sjekk8',true, 2, false);
-- Mission
INSERT INTO mission (end_time, mission_description,
                    mission_lat,mission_lng,
                    mission_name,mission_visibility,
                    start_time,game_id)
values ('02/04/1996','It is time to embrace the Eat', 59.931146,10.756830,'Code Jabo','HUMAN','12:00',1),
       ('02/04/1999','It is time to embrace the Eat', 59.931146,10.756830,'Code Babor','HUMAN','12:00',2),
       ('14/12/1995','Eat the beans', 59.933545,10.763886,'Code Truls','ZOMBIE','12:00',1),
       ('14/12/1992','Eat the brains', 59.933545,10.763886,'Code Trults','ZOMBIE','12:00',2),
       ('24/06/2020','Delve into the drakness', 59.936418,10.783266,'Code Carl','GLOBAL','12:00',1),
       ('24/06/2020','Delve into the drakness', 59.936418,10.783266,'Code Zarl','GLOBAL','12:00',2),
       ('04/02/2022','Very cool misison', 59.938316,10.800220,'Code SKULLS','HUMAN','12:00',3),
       ('04/02/2022','Very cool misison', 59.938316,10.800220,'Code PULS','GLOBAL','12:00',3),
       ('04/02/2022','Very cool misison', 59.938316,10.800220,'Code SNULS','ZOMBIE','12:00',3);

-- Kill
INSERT INTO kill(lat, lng, time_of_death, game_id, player_death_id, kills_id)
VALUES ('59.932723','10.756447', '12:13:00',1,8,2),
       ('59.926519','10.752038', '12:13:00',1,9,2),
       ('59.925939','10.750561', '12:13:00',1,10,9),
       ('59.932723','10.756447', '12:13:00',2,11,4),
       ('59.932723','10.756447', '12:13:00',2,12,11),
       ('59.932723','10.756447', '12:13:00',3,13,6),
       ('59.932723','10.756447', '12:13:00',3,14,13);



