INSERT INTO CALENDAR (ID) VALUES (1000);
INSERT INTO CALENDAR (ID) VALUES (1001);
INSERT INTO CALENDAR (ID) VALUES (1002);
INSERT INTO CALENDAR (ID) VALUES (1003);
INSERT INTO CALENDAR (ID) VALUES (1004);
INSERT INTO CALENDAR (ID) VALUES (1005);
INSERT INTO CALENDAR (ID) VALUES (1006);


INSERT INTO CALENDAR()
VALUES
    (), (), (), (), (), (), (), (), (), ();



INSERT INTO USER_TABLE (UID, BIRTH, CALENDAR_ID, EMAIL, GENDER, IMAGE_PATH, JOIN_DATE, NICKNAME, ROLE)
VALUES ('Tqth82Y2EFMR42egUYoVEyiGxr32', '1990-05-15', 1000, 'test4@naver.com', 'Male', '/profile.jpg', '2023-01-01', 'Nick001', 'USER'),
       ('QJBriy9zfPMAD8nk15KrWFKnLq73', '1988-03-22', 1001, 'test5@naver.com', 'Female', '/profile.jpg', '2023-02-15', 'Nick002', 'USER'),
       ('udaWuzdnRHObAy4Pk8WcPVfMQ8w1', '1995-08-10', 1002, 'test6@naver.com', 'Non-Binary', '/profile.jpg', '2023-03-05', 'Nick003', 'USER'),
       ('FmSaRC08JgbwSYMFYu5wbXWKjxY2', '2000-12-01', 1003, 'test7@naver.com', 'Male', '/profile.jpg', '2023-04-20', 'Nick004', 'USER'),
       ('ERzkjqvUAwUX9h9uAw8kPWdHgsC2', '1992-07-18', 1004, 'test8@naver.com', 'Female', '/profile.jpg', '2023-05-30', 'Nick005', 'USER'),
       ('95IcydqLUESf3ZTUcI3oNRarS0Y2', '1992-07-18', 1005, 'ksh@naver.com', 'Male', '/profile.jpg', '2023-05-30', 'Nick005', 'USER'),
       ('6knmKJyUQBV5GbGnHvMbk8zm5eB3', '1994-07-18', 1006, 'x@naver.com', 'Male', '/profile.jpg', '2023-05-30', 'x', 'ADMIN');



INSERT INTO group_table (maker_uid, calendar_id, group_name)
VALUES
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1, 'g1'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 2, 'g2'),
    ('Tqth82Y2EFMR42egUYoVEyiGxr32', 3, 'g3');

INSERT INTO group_and_user (uid, gid, allowed_security_level)
VALUES
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1, 'HIGH'),
    ('Tqth82Y2EFMR42egUYoVEyiGxr32', 1, 'HIGH'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 2, 'HIGH'),
    ('Tqth82Y2EFMR42egUYoVEyiGxr32', 2, 'HIGH'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 3, 'HIGH'),
    ('Tqth82Y2EFMR42egUYoVEyiGxr32', 3, 'HIGH'),
    ('QJBriy9zfPMAD8nk15KrWFKnLq73', 3, 'HIGH');



INSERT INTO FRIENDS_RELATION (ID, ALIAS, SOURCE_EMAIL, TARGET_EMAIL)
VALUES (1000, 'TEST4', 'ksh@naver.com', 'test4@naver.com');

INSERT INTO FRIENDS_RELATION (ID, ALIAS, SOURCE_EMAIL, TARGET_EMAIL)
VALUES (1001, 'TEST5', 'ksh@naver.com', 'test5@naver.com');

INSERT INTO FRIENDS_RELATION (ID, ALIAS, SOURCE_EMAIL, TARGET_EMAIL)
VALUES (1002, 'TEST6', 'ksh@naver.com', 'test6@naver.com');

INSERT INTO FRIENDS_RELATION (ID, ALIAS, SOURCE_EMAIL, TARGET_EMAIL)
VALUES (1003, 'TEST7', 'ksh@naver.com', 'test7@naver.com');

INSERT INTO FRIENDS_RELATION (ID, ALIAS, SOURCE_EMAIL, TARGET_EMAIL)
VALUES (1004, 'TEST8', 'ksh@naver.com', 'test8@naver.com');

--PERSONAL SCHEDULE 1 ~ 24
INSERT INTO schedule_source (maker_uid, source_calendar_id, color_value, description, title, guest_edit_permission, start_date, end_date, start_time, end_time)
VALUES
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FFFF5733', 'Weekly team meeting', 'Team Meeting', true, '2025-02-01', '2025-02-01', '10:00:00', '11:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF33FF57', 'Project deadline for Q1', 'Project Deadline', false, '2025-02-05', '2025-02-05', '14:00:00', '15:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF5733FF', 'Birthday celebration for John', 'John’s Birthday', true, '2025-02-10', '2025-02-10', '18:00:00', '22:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FFFF33A1', 'Annual company retreat', 'Company Retreat', false, '2025-03-01', '2025-03-03', '09:00:00', '17:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF33A1FF', 'Family vacation in Jeju', 'Jeju Vacation', true, '2025-04-15', '2025-04-20', '07:00:00', '21:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FFFFD700', 'Gym workout session', 'Workout', false, '2025-02-07', '2025-02-07', '06:00:00', '07:30:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF8A2BE2', 'Dentist appointment', 'Dentist Visit', false, '2025-02-12', '2025-02-12', '15:00:00', '16:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FFDC143C', 'Weekend hiking trip', 'Hiking Trip', true, '2025-02-17', '2025-02-17', '08:00:00', '13:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF32CD32', 'Coding hackathon', 'Hackathon Event', true, '2025-03-10', '2025-03-11', '09:00:00', '21:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FFFF4500', 'Weekend road trip', 'Road Trip', false, '2025-03-20', '2025-03-22', '06:00:00', '22:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF1E90FF', 'Online conference on AI', 'AI Conference', false, '2025-04-05', '2025-04-05', '10:00:00', '17:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FFFF1493', 'Friends’ reunion party', 'Reunion Party', true, '2025-04-10', '2025-04-10', '19:00:00', '23:30:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF008B8B', 'Study session for exams', 'Study Group', false, '2025-04-15', '2025-04-15', '14:00:00', '18:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FFB22222', 'Wedding ceremony', 'Wedding Event', true, '2025-05-01', '2025-05-01', '11:00:00', '16:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF006400', 'Volunteer work at shelter', 'Volunteer Work', false, '2025-05-10', '2025-05-10', '09:00:00', '13:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF8B008B', 'Business trip to Seoul', 'Business Trip', false, '2025-05-15', '2025-05-17', '08:00:00', '21:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF00008B', 'Parents’ anniversary dinner', 'Anniversary Dinner', true, '2025-06-01', '2025-06-01', '19:00:00', '22:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FFA52A2A', 'Photography workshop', 'Photo Workshop', false, '2025-06-10', '2025-06-10', '10:00:00', '16:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FFFF6347', 'Cooking class for beginners', 'Cooking Class', true, '2025-06-20', '2025-06-20', '15:00:00', '18:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF4682B4', 'Monthly book club meeting', 'Book Club', true, '2025-07-01', '2025-07-01', '18:30:00', '20:30:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF32CD32', 'Coding hackathon', 'Hackathon Event', true, '2025-03-10', '2025-03-11', '09:00:00', '21:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF32CD32', 'Coding hackathon', 'Hackathon Event', true, '2025-03-10', '2025-03-11', '09:00:00', '21:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF32CD32', 'Coding hackathon', 'Hackathon Event', true, '2025-03-10', '2025-03-11', '09:00:00', '21:00:00'),
('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1005, 'FF32CD32', 'Coding hackathon', 'Hackathon Event', true, '2025-03-10', '2025-03-11', '09:00:00', '21:00:00');



INSERT INTO schedule_security (schedule_id, uid, security_level)
VALUES
    (1, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'HIGH'),
    (2, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'MEDIUM'),
    (3, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (4, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'HIGH'),
    (5, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (6, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'MEDIUM'),
    (7, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'HIGH'),
    (8, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (9, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'MEDIUM'),
    (10, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'HIGH'),
    (11, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (12, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'HIGH'),
    (13, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'MEDIUM'),
    (14, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (15, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'HIGH'),
    (16, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'MEDIUM'),
    (17, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (18, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'HIGH'),
    (19, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'MEDIUM'),
    (20, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (21, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (22, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (23, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (24, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW');


--GROUP SCHEDULE 25 ~ 33
INSERT INTO schedule_source (maker_uid, source_calendar_id, color_value, description, title, guest_edit_permission, start_date, end_date, start_time, end_time)
VALUES
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1, 'FFFF5733', 'Weekly team meeting', 'Team Meeting', true, '2025-02-02', '2025-02-05', '10:00:00', '11:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1, 'FFFF5733', 'Weekly team meeting', 'Team Meeting', true, '2025-03-02', '2025-03-05', '10:00:00', '11:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1, 'FFFF5733', 'Kesi team meeting', 'Team Meeting', true, '2025-03-05', '2025-03-05', '10:00:00', '11:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 2, 'FF33A1FF', 'Project deadline review', 'Deadline Review', false, '2025-03-10', '2025-03-10', '14:00:00', '15:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 3, 'FF5733FF', 'Client presentation', 'Client Meeting', true, '2025-03-15', '2025-03-15', '09:30:00', '11:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 2, 'FF33FF57', 'Sprint planning session', 'Sprint Planning', false, '2025-03-20', '2025-03-20', '13:00:00', '15:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 2, 'FFFFA533', 'Quarterly report discussion', 'Quarterly Meeting', true, '2025-03-25', '2025-03-25', '10:00:00', '12:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1, 'FF3380FF', 'Team building activity', 'Team Building', true, '2025-04-01', '2025-04-01', '15:00:00', '17:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 3, 'FF22A1D3', 'Technical workshop on AI', 'AI Workshop', false, '2025-04-05', '2025-04-05', '09:00:00', '12:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 3, 'FFAACC22', 'HR policy update meeting', 'HR Meeting', false, '2025-04-10', '2025-04-10', '10:30:00', '11:30:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1, 'FF229955', 'Product demo for investors', 'Product Demo', true, '2025-04-15', '2025-04-15', '14:00:00', '16:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 2, 'FF8833DD', 'Backend system upgrade discussion', 'Tech Meeting', false, '2025-04-20', '2025-04-20', '15:00:00', '16:30:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 3, 'FFDDAA44', 'Security awareness training', 'Security Training', true, '2025-04-25', '2025-04-25', '09:00:00', '11:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1, 'FF1155AA', 'Mobile app UI/UX review', 'Design Review', false, '2025-05-01', '2025-05-01', '13:30:00', '15:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 2, 'FF77CC55', 'All-hands company meeting', 'Company Meeting', true, '2025-05-05', '2025-05-05', '10:00:00', '11:30:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 3, 'FF5544CC', 'New feature brainstorming session', 'Feature Planning', false, '2025-05-10', '2025-05-10', '14:00:00', '16:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 1, 'FF996633', 'Marketing campaign kickoff', 'Marketing Kickoff', true, '2025-05-15', '2025-05-15', '09:30:00', '11:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 2, 'FFCC4488', 'Cloud infrastructure scaling discussion', 'Cloud Strategy', false, '2025-05-20', '2025-05-20', '15:30:00', '17:00:00'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 3, 'FF3322DD', 'End-of-quarter performance review', 'Performance Review', true, '2025-05-25', '2025-05-25', '10:00:00', '12:00:00');

INSERT INTO schedule_security (schedule_id, uid, security_level)
VALUES
    (25, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'HIGH'),
    (25, 'Tqth82Y2EFMR42egUYoVEyiGxr32', 'MEDIUM'),
    (26, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (26, 'Tqth82Y2EFMR42egUYoVEyiGxr32', 'HIGH'),
    (27, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (27, 'Tqth82Y2EFMR42egUYoVEyiGxr32', 'MEDIUM'),
    (28, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'MEDIUM'),
    (28, 'Tqth82Y2EFMR42egUYoVEyiGxr32', 'HIGH'),
    (29, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'HIGH'),
    (29, 'Tqth82Y2EFMR42egUYoVEyiGxr32', 'LOW'),
    (30, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'MEDIUM'),
    (30, 'Tqth82Y2EFMR42egUYoVEyiGxr32', 'LOW'),
    (31, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'LOW'),
    (31, 'Tqth82Y2EFMR42egUYoVEyiGxr32', 'HIGH'),
    (32, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'HIGH'),
    (32, 'Tqth82Y2EFMR42egUYoVEyiGxr32', 'MEDIUM'),
    (33, '95IcydqLUESf3ZTUcI3oNRarS0Y2', 'MEDIUM'),
    (33, 'Tqth82Y2EFMR42egUYoVEyiGxr32', 'LOW');




INSERT INTO alarm (uid, title, content, alarm_type, create_time, alarm_state)
VALUES
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Meeting Reminder', 'You have a team meeting at 10 AM.', 'SCHEDULE', '2025-02-01 09:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Project Deadline', 'Final project report is due today.', 'SCHEDULE', '2025-01-30 08:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Birthday Alert', 'John’s birthday is today. Don’t forget to wish him!', 'BASIC', '2025-01-30 07:30:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Company Retreat Info', 'Reminder: The company retreat starts tomorrow.', 'GROUP', '2025-01-28 18:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Vacation Flight Reminder', 'Your flight to Jeju is scheduled for 7 AM.', 'SCHEDULE', '2025-01-14 20:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Workout Session', 'Time to hit the gym! Your session starts at 6 AM.', 'BASIC', '2025-01-07 05:30:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Dentist Appointment', 'Reminder: You have a dentist visit today at 3 PM.', 'SCHEDULE', '2025-01-12 09:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Hiking Trip Alert', 'Prepare for your hiking trip tomorrow morning.', 'BASIC', '2025-01-16 19:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Hackathon Notification', 'Hackathon event starts at 9 AM. Get ready!', 'GROUP', '2025-01-09 21:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Road Trip Prep', 'Pack your bags! Your road trip starts tomorrow.', 'SCHEDULE', '2025-01-19 22:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'AI Conference Notice', 'The AI Conference starts at 10 AM.', 'GROUP', '2025-01-05 08:30:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Reunion Party Reminder', 'The friends’ reunion party is at 7 PM.', 'BASIC', '2025-01-10 12:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Exam Study Session', 'Study group session starts at 2 PM.', 'SCHEDULE', '2025-01-15 09:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Wedding Alert', 'Don’t forget the wedding at 11 AM.', 'SCHEDULE', '2025-01-01 10:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Volunteer Work Reminder', 'Your volunteer work at the shelter starts at 9 AM.', 'BASIC', '2025-01-10 08:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Business Trip Departure', 'Your trip to Seoul begins at 8 AM.', 'SCHEDULE', '2025-01-14 21:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Anniversary Dinner Alert', 'Your parents’ anniversary dinner is at 7 PM.', 'BASIC', '2025-01-01 13:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Photography Workshop', 'Your photo workshop starts at 10 AM.', 'SCHEDULE', '2025-01-09 20:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Cooking Class Reminder', 'Your cooking class starts at 3 PM.', 'GROUP', '2025-01-19 14:00:00', 'PROCESSED'),
    ('95IcydqLUESf3ZTUcI3oNRarS0Y2', 'Book Club Meeting', 'Book club meeting starts at 6:30 PM.', 'GROUP', '2025-01-01 12:00:00', 'PROCESSED');

INSERT INTO alarm_group (alarm_id, gid)
VALUES
    (4, 101),  -- Hackathon 알람 (GROUP) → 그룹 101
    (9, 101),  -- Hackathon 알람 (GROUP) → 그룹 101
    (11, 102), -- AI Conference 알람 (GROUP) → 그룹 102
    (19, 103), -- Cooking Class 알람 (GROUP) → 그룹 103
    (20, 104); -- Book Club 알람 (GROUP) → 그룹 104


INSERT INTO alarm_schedule (alarm_id, gid, schedule_id)
VALUES
    (1, 201, 1),  -- Meeting Reminder (SCHEDULE) → 그룹 201, 일정 ID 1
    (2, 202, 2),  -- Project Deadline (SCHEDULE) → 그룹 202, 일정 ID 2
    (5, 203, 5),  -- Dentist Appointment (SCHEDULE) → 그룹 203, 일정 ID 7
    (7, 203, 7),  -- Dentist Appointment (SCHEDULE) → 그룹 203, 일정 ID 7
    (10, 204, 10), -- Road Trip Prep (SCHEDULE) → 그룹 204, 일정 ID 10
    (13, 205, 13), -- Exam Study Session (SCHEDULE) → 그룹 205, 일정 ID 13
    (14, 206, 14), -- Wedding Alert (SCHEDULE) → 그룹 206, 일정 ID 14
    (16, 207, 16), -- Business Trip Departure (SCHEDULE) → 그룹 207, 일정 ID 16
    (18, 208, 18); -- Photography Workshop (SCHEDULE) → 그룹 208, 일정 ID 18


INSERT INTO alarm_basic (alarm_id)
VALUES
    (3),
    (6),
    (8),
    (12),
    (15),
    (17);




