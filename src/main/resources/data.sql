-- Insert into member table if not exists
INSERT IGNORE INTO member (id, created_at, modified_at, email, nickname, password, gender, role)
VALUES (1, '2024-05-30 00:00:00', '2024-06-01 00:00:00', 'gang@example.com', 'admin_Gang', 'password', 'M', 'USER');

INSERT IGNORE INTO member (id, created_at, modified_at, email, nickname, password, gender, role)
VALUES (2, '2024-01-05 00:00:00', '2024-01-10 00:00:00', 'amy@example.com', 'admin_Amy', 'password', 'F', 'USER');

INSERT IGNORE INTO member (id, created_at, modified_at, email, nickname, password, gender, role)
VALUES (3, '2024-08-20 00:00:00', '2024-08-25 00:00:00', 'wendy@example.com', 'admin_Wendy', 'password', 'F', 'USER');

-- -- Insert into post table if not exists (Dummy post)
-- INSERT IGNORE INTO post (id, created_at, modified_at, trip_star_date, trip_end_date, view_count, address, continent, country, title, content, member_id)
-- VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2023-01-01 00:00:00', '2023-01-10 00:00:00', 0, '더미 주소', '아시아', '대한민국', '더미 포스트 제목', '이것은 더미 포스트 내용입니다.', 1);

-- Insert into post table if not exists
INSERT IGNORE INTO post (id, created_at, modified_at, trip_star_date, trip_end_date, view_count, address, continent, country, title, content, member_id)
VALUES (1, '2024-05-30 00:00:00', '2024-06-01 00:00:00', '2024-05-15 00:00:00', '2024-05-25 00:00:00', 0, '', '아시아', '일본', '도쿄에서의 애니메이션 탐방', '', 1);

-- Insert into memo table (short posts) if not exists
INSERT IGNORE INTO memo (id, post_id, address, content)
VALUES (1, 1, '아키하바라', '<p>아키하바라의 애니메이션 굿즈 상점을 둘러봤습니다. 맛있었어요!</p>');

INSERT IGNORE INTO memo (id, post_id, address, content)
VALUES (2, 1, '미타카시의 미술관', '<p>지브리 미술관에서 지브리의 세계에 빠졌어요.</p>');

INSERT IGNORE INTO memo (id, post_id, address, content)
VALUES (3, 1, '도쿄 애니메이션 센터', '<p>도쿄 애니메이션 센터에서 다양한 전시를 감상했습니다.</p>');

INSERT IGNORE INTO memo (id, post_id, address, content)
VALUES (4, 1, '사이타마', '<p>사이타마의 도쿄 원더랜드에서 하루를 보냈어요.</p>');

-- Insert into post_theme table (themes) if not exists
INSERT IGNORE INTO post_theme (id, post_id, theme)
VALUES (1, 1, 'HOT_PLACE');

INSERT IGNORE INTO post_theme (id, post_id, theme)
VALUES (2, 1, 'ACTIVITY');

INSERT IGNORE INTO post_theme (id, post_id, theme)
VALUES (3, 1, 'SIGHTSEEING');

-- Insert into post table if not exists (Switzerland post)
INSERT IGNORE INTO post (id, created_at, modified_at, trip_star_date, trip_end_date, view_count, address, continent, country, title, content, member_id)
VALUES (2, '2024-01-05 00:00:00', '2024-01-10 00:00:00', '2023-12-20 00:00:00', '2023-12-30 00:00:00', 0, '', '유럽', '스위스', '스위스의 겨울 왕국', '', 2);

-- Insert into memo table (short posts) if not exists (Switzerland short posts)
INSERT IGNORE INTO memo (id, post_id, address, content)
VALUES (5, 2, '취리히', '<p>취리히의 마법 같은 크리스마스 마켓을 방문했습니다.</p>');

INSERT IGNORE INTO memo (id, post_id, address, content)
VALUES (6, 2, '알프스', '<p>스위스 알프스에서의 스키는 숨막히는 경험이었습니다.</p>');

INSERT IGNORE INTO memo (id, post_id, address, content)
VALUES (7, 2, '그뤼에르', '<p>유명한 스위스 퐁듀를 시도했는데 정말 맛있었어요!</p>');

-- Insert into post_theme table (themes) if not exists (Switzerland themes)
INSERT IGNORE INTO post_theme (id, post_id, theme)
VALUES (4, 2, 'ACTIVITY');

INSERT IGNORE INTO post_theme (id, post_id, theme)
VALUES (5, 2, 'LUXURY');

INSERT IGNORE INTO post_theme (id, post_id, theme)
VALUES (6, 2, 'SIGHTSEEING');

-- Insert into post table if not exists (UK post)
INSERT IGNORE INTO post (id, created_at, modified_at, trip_star_date, trip_end_date, view_count, address, continent, country, title, content, member_id)
VALUES (3, '2024-08-20 00:00:00', '2024-08-25 00:00:00', '2024-08-01 00:00:00', '2024-08-15 00:00:00', 0, '런던', '유럽', '영국', '영국의 문화와 역사를 탐방하다', '<p>이번 영국 여행은 문화와 역사에 흠뻑 빠질 수 있는 멋진 시간이었습니다. 런던에서는 버킹엄 궁전, 타워 브리지, 그리고 대영박물관을 방문했는데, 각 장소마다 깊은 역사와 아름다움을 느낄 수 있었습니다. 특히, 대영박물관에서는 다양한 유물들을 직접 볼 수 있어 인상적이었습니다. 그린위치 천문대에서는 시간의 역사를 배우며 경이로움을 느꼈습니다.</p><p>런던 외에도 옥스포드와 케임브리지 대학을 둘러보며 영국의 교육과 학문에 대한 존경심을 느꼈고, 코츠월드의 아름다운 시골 풍경을 즐길 수 있었습니다. 또한, 스톤헨지의 신비로운 분위기와 바스의 로마 시대 목욕탕은 여행의 하이라이트 중 하나였습니다.</p>', 3);

-- Insert into post_theme table (themes) if not exists (UK themes)
INSERT IGNORE INTO post_theme (id, post_id, theme)
VALUES (7, 3, 'SIGHTSEEING');

INSERT IGNORE INTO post_theme (id, post_id, theme)
VALUES (8, 3, 'HISTORY');

INSERT IGNORE INTO post_theme (id, post_id, theme)
VALUES (9, 3, 'CULTURE');
