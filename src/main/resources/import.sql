INSERT INTO `user` (id, user_id, user_pw, user_nickname) VALUES (1, '20230906', '123', '이정한');
INSERT INTO `user` (id, user_id, user_pw, user_nickname) VALUES (2, '20230919', '123', '허서현');
INSERT INTO `user` (id, user_id, user_pw, user_nickname) VALUES (3, '20230857', '123', '이소빈');
INSERT INTO `user` (id, user_id, user_pw, user_nickname) VALUES (4, '0000', '123', '문미경');

INSERT INTO route (id, user_id) VALUES (1, 1);
INSERT INTO route (id, user_id) VALUES (2, 2);
INSERT INTO route (id, user_id) VALUES (3, 4);

INSERT INTO review (id, review_text, route_id, user_id) VALUES (1, '정말 힘들지만 예쁜 광경을 볼 수 있어 좋았다.', 2, 1);
INSERT INTO review (id, review_text, route_id, user_id) VALUES (2, '친절한 길은 아니지만 씹덕은 좋아할듯', 2, 2);
INSERT INTO review (id, review_text, route_id, user_id) VALUES (3, '사람들이 잘 다니는 길이라서 그런지 산 보다 사람을 더 많이 보네요', 1, 3);
INSERT INTO review (id, review_text, route_id, user_id) VALUES (4, '안내해준 길을 따라 가다보니 안저하게 목적지 까지 갈수있었다.', 1, 4);
INSERT INTO review (id, review_text, route_id, user_id) VALUES (5, '이 길은 이제 제가 정복했습니다. 너무 좋네요', 1, 2);
INSERT INTO review (id, review_text, route_id, user_id) VALUES (6, '조용한 산길에 여러 숲속의 소리에 오늘도 마음에 위안을 얻네요', 2, 3);



