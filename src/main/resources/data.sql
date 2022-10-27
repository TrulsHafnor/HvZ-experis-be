-- Game
INSERT INTO game (game_state, game_title, nw_lat, nw_lng, se_lat, se_lng)
VALUES ('REGISTRATION', 'Hungry Zombers', '59.932792', '10.754136', '59.928405', '10.761050'),
       ('IN_PROGRESS', 'World War Z', '59.938979', '10.776390', '59.930698', '10.780897'),
       ('COMPLETE', 'Walking Dread', '59.925777', '10.787467', '59.923863', '10.792707'),
       ('REGISTRATION', 'Java vs Zombies', '59.918669', '10.759249', '59.915625', '10.767274');

-- Player
INSERT INTO player(bite_code, is_human, game_id, is_patient_zero)
    VALUES ('xWq42',true, 1, false),
       ('wlQ2B', true, 1, false),
       ('wSQ2B', true, 1, false),
       ('wVQ2B', true, 1, false),
       ('wlB2B', true, 1, false),
       ('wk2lq', true, 2, false),
       ('eeRTs', false, 2, false),
       ('wk2lq', false, 2, false),
       ('ewRTs', false, 2, false),
       ('wk2xq', true, 2, false),
       ('eeRTs', false, 2, false),
       ('pq124', false, 3, false),
       ('okqw0', false, 3, false),
       ('pqiwh', false, 3, false),
       ('okenz', false, 3, false),
       ('pqiw2', false, 3, false),
       ('ok3n0', false, 3, false);

-- Mission
INSERT INTO mission (end_time, mission_description,
                    mission_lat,mission_lng,
                    mission_name,mission_visibility,
                    start_time,game_id)
values ('27/10/2022','Hjelp de gamle damene for å beskytte godteriet deres', 59.917303,10.764270,'Code Experis','HUMAN','11:15',4),
       ('27/10/2022','Skrem de gamle damene, og ta godteriet deres', 59.916432, 10.761716, 'Code Noroff','ZOMBIE','11:15',4),
       ('27/10/2022','Kos dere på etterfesten!', 59.916679, 10.762854, 'Code Noroff','GLOBAL','17:00',4);

-- Kill
INSERT INTO kill(lat, lng, time_of_death, game_id, player_death_id, kills_id)
VALUES ('59.932723','10.756447', '11:63:32',2,11,4),
       ('59.932723','10.756447', '16:53:26',2,12,11),
       ('59.932723','10.756447', '17:11:12',3,13,6),
       ('59.932723','10.756447', '10:15:57',3,14,13);

INSERT INTO squad(is_human, "name", game_id, player_id)
VALUES (TRUE, 'HumanForce', 1, 1),
       (FALSE, 'ZombieForce', 1, 2),
       (TRUE, 'HumanForce', 2, 3),
       (FALSE, 'ZombieForce', 2, 4);

-- Squadmember
INSERT INTO squad_member(rank, member_id, squad_id)
VALUES ('Leader', 1, 1),
       ('Zombie', 2, 2),
       ('Leader', 3, 3),
       ('Zombie', 4, 4);
