-- Game
INSERT INTO game (game_state, game_title, nw_lat, nw_lng, se_lat, se_lng)
VALUES ('REGISTRATION', 'Game one', '59.932792', '10.754136', '59.928405', '10.761050'),
       ('IN_PROGRESS', 'Game two', '59.938979', '10.776390', '59.930698', '10.780897'),
       ('COMPLETE', 'Game three', '59.925777', '10.787467', '59.923863', '10.792707');

-- Player
INSERT INTO player(bite_code, is_human, game_id)
VALUES ('Hei1',true, 1),
       ('Hei2', false, 1),
       ('Hei3', true, 2),
       ('Hei4', false, 2),
       ('Hei5',true, 3),
       ('Hei6',false, 3),
       ('Hei7',true, 1);
-- Mission
--INSERT INTO movie (movie_name,genre,movie_director,movie_picture,movie_trailer, release_year,franchise_id)

