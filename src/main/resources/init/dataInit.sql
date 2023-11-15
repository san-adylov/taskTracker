insert into users(id, first_name, last_name, email, password, image, role)
values (1, 'Abdumalik', 'Turatbek uulu', 'asanbekovmalik2@gmail.com',
        '$2a$12$6RFdM5bwOFsWmbrD8Z4u5u/VcEyRTebNBbFcHbMEcOe1MupujUrPS',
        'https://ichef.bbci.co.uk/news/976/cpsprodpb/3EE0/production/_121269061_getty.jpg', 'ADMIN'),
       (2, 'Matmusa', 'Abduvohob uulu', 'admin@gmail.com',
        '$2a$12$7LhVCZDPWuzl9jOAjm2plu.BYusL3s3QTlIf.HaYVRKITNtC3zqvW',
        'https://kartinkof.club/uploads/posts/2022-05/1653229464_15-kartinkof-club-p-kartinki-privet-dzhamshut-15.png',
        'ADMIN'),
       (3, 'Lira', 'Kanatova', 'lira@gmail.com', '$2a$12$N8/R3Q04xhEAhZ1vWcaQquLn3nDE5V8xwPApuBndhKNUUCr.mljf.',
        'https://o-tendencii.com/uploads/posts/2022-02/1644707103_35-o-tendencii-com-p-obraz-natsionalnaya-odezhda-kirgizii-zhens-38.jpg',
        'ADMIN'),
       (4, 'Adilet', 'Islambek uulu', 'adilet@gmail.com',
        '$2a$12$xYXRx6kjHtxqb0sV1jLrh.WrcfmoVazuwqLY1YmVjHlGjcHroICVO',
        'https://biogr.net/wp-content/uploads/2022/02/63463463.jpg', 'ADMIN'),
       (5, 'Manas', 'Abdugani uulu', 'manas@gmail.com', '$2a$12$egcK6uZ5RlHPgEzEEczyM.VVX33yCsgyH2Kdw4m.lJk7Bl2pnE39.',
        'https://kai.kg/public/images/2021/12/1638521287.png', 'ADMIN'),
       (6, 'Ruslan', 'Manas uulu', 'rusi.studio.kgz@gmail.com',
        '$2a$12$gXZ4PqsbtIRLo3jzhYbEuu4buoTi6QuNDziF9Wbv5IOL0s8k2BnbO',
        'https://fikiwiki.com/uploads/posts/2022-02/1644971529_1-fikiwiki-com-p-smeshnie-kartinki-muzhchin-1.jpg',
        'ADMIN'),
       (7, 'Gulira', 'Abdukerim', 'gulira@gmail.com', '$2a$12$2iwD0wgV3wXR6s5NUo5UN./cL.IT.5WIJBJFL14Y.NQvPw20UEejm',
        'https://uhd.name/uploads/posts/2022-09/1662489129_2-uhd-name-p-odezhda-iz-kirgizii-devushka-pinterest-2.jpg',
        'ADMIN'),
       (8, 'Aizat', 'Duiheeva', 'aizat@gmail.com', '$2a$12$UARP9TLJMXtcEMGcPqhdQu3cmKgbLWVujV5EBqXzvVT8j119gBh9a',
        'https://almode.ru/uploads/posts/2021-03/thumbs/1615893140_3-p-natsionalnaya-odezhda-v-kirgizii-7.jpg',
        'ADMIN'),
       (9, 'Sanjar', 'Abdimomunov', 'sanjar@gmail.com', '$2a$12$cHRllZJmiH1BRFS9YRBDHO7TkiKaN4yysFA2tXmGqb/ugiGRBQIPO',
        'https://weblinks.ru/wp-content/uploads/2022/11/1-4.jpeg', 'ADMIN'),
       (10, 'Davran', 'Joldoshbaev', 'davran@gmail.com', '$2a$12$p8SDxF12xsfCFPnjDJFlQebSSGetlVCCUFxGFeuJ2gzm1uO9Tvk3O',
        'https://png.pngtree.com/png-clipart/20210502/original/pngtree-fashion-male-wearing-a-charming-model-png-image_6257513.jpg',
        'ADMIN'),
       (11, 'Baytik', 'Taalaybekov', 'baytik@gmail.com', '$2a$12$1gAJqZKNNmVwRVPAP8460.MUpIR93N9mtBEzXRMM1BUc1dW287K82',
        'https://galerey-room.ru/images/0_93fb1_e0e3eb0e_L.jpeg', 'ADMIN'),
       (12, 'Aigerim', 'Bektenova', 'aigerim@gmail.com', '$2a$12$C0L/gxMnQ.xy.kW.GHsPZ.idzgmcAmS1qnJ1FQvO9u02KEFZ8Ftie',
        'https://i.pinimg.com/236x/70/77/42/7077420010cddb55f03e972727e14e42.jpg', 'ADMIN'),
       (13, 'Kanikey', 'Akjoltoy ', 'kanikey@gmail.com', '$2a$12$.RVGGU9E.9zo/aYNobfxVe6JS6EjKdL6kQrQuXEUk18D/RQDX1KxO',
        'https://i.pinimg.com/originals/92/8b/6b/928b6bc590245ef02410224f5cb657a1.jpg', 'ADMIN'),
       (14, 'Salimbek', 'Hadjakeldiev', 'salimbek@gmail.com',
        '$2a$12$lFrKvKznE4AWCLpn/77VQe8babr9vPimOa6iQu4qnV1hNjTd1R.wq',
        'https://pibig.info/uploads/posts/2022-11/1669419936_1-pibig-info-p-siluet-muzhchini-krasivo-1.jpg', 'ADMIN'),
       (15, 'Akkayn', 'Ruslan uulu', 'akkayn@gmail.com', '$2a$12$I1WJ3pWJDoBCHsr2cCLOvevKmTTWzJDUXC.IWcORU5KZymezW034C',
        'https://w7.pngwing.com/pngs/698/726/png-transparent-graphy-male-taxi-driver-miscellaneous-photography-transport-thumbnail.png',
        'ADMIN');

insert into work_spaces(id,name,admin_id,created_date)
values (1,'Taigan',1,'2023-09-07T23:00:00'),
       (2,'LMS',2,'2023-09-07T12:00:00'),
       (3,'Gadgetarium',3,'2023-09-09T00:00:00'),
       (4,'AirBnb',4,'2023-09-10T00:00:00'),
       (5,'Legend',5,'2023-09-11T00:00:00'),
       (6,'NASA',6,'2023-09-12T00:00:00'),
       (7,'Tesla',7,'2023-09-13T00:00:00'),
       (8,'ᏤᎪᏒᎶᎪᏚ',8,'2023-09-14T00:00:00'),
       (9,'ᴏtσͷɢ乂',9,'2023-09-15T00:00:00'),
       (10,'ʜᴇᴀᴅsʜᴏᴛ',10,'2023-09-16T00:00:00'),
       (11,'•P£R$£B∆Y∆•',11,'2023-09-16T00:00:00'),
       (12,'AldyyExtece',2,'2023-09-18T00:00:00'),
       (13,'ωαнуυsu',1,'2023-09-19T00:00:00'),
       (14,'Bama.Boy',2,'2023-09-20T00:00:00'),
       (15,'maratha',2,'2023-09-21T00:00:00'),
       (16,'VEŇØM',2,'2023-09-22T00:00:00'),
       (17,'Divâgar',3,'2023-09-23T00:00:00'),
       (18,'Baby°Killer™',3,'2023-09-24T00:00:00'),
       (19,'ᴺᴸsᴇᴠᴇɴ°ᴵᴰ',3,'2023-09-25T00:00:00'),
       (20,'PrettyFreefire',3,'2023-09-26T00:00:00'),
       (21,'B.K.Mästër',3,'2023-09-27T00:00:00'),
       (22,'Shroud',3,'2023-09-28T00:00:00'),
       (23,'Sunny',4,'2023-09-29T00:00:00'),
       (24,'Headhunter',4,'2023-09-30T00:00:00'),
       (25,'Zimmer',4,'2023-10-01T00:00:00'),
       (26,'Doter',4,'2023-10-02T00:00:00'),
       (27,'~Real Man~',5,'2023-10-03T00:00:00'),
       (28,'FlaxPlayer',5,'2023-10-04T00:00:00'),
       (29,'ApostaL',5,'2023-10-05T00:00:00'),
       (30,'MedCheck',5,'2023-10-06T00:00:00');

insert into boards(id, title, back_ground, work_space_id)
values (1, 'word', 'img', 1),
       (2, 'title', 'color', 2),
       (3, 'word', 'img', 3),
       (4, 'Cute Escape', 'color', 4),
       (5, 'Diamond', 'img', 5),
       (6, 'Urban', 'color', 6),
       (7, 'Recharge', 'img', 7),
       (8, 'Price Grabber', 'color', 8),
       (9, 'Spring Ready', 'img', 9),
       (10, 'Cloud Nine', 'img', 10),
       (11, 'Second Freedom', 'color', 11),
       (12, 'Greenvalley', 'img', 2),
       (13, 'Plot Twist', 'img', 7),
       (14, 'All In One', 'img', 7),
       (15, 'First Choice', 'color', 8),
       (16, 'Save Happiness', 'img', 8),
       (17, 'All-Star', 'color', 9),
       (18, 'Preferred Pins', 'img', 9),
       (19, 'Fabulous Touch', 'color', 10),
       (20, 'Chiff Chaff', 'img', 11),
       (21, 'Better World', 'img', 2),
       (22, 'Dream Team', 'color', 2),
       (23, 'Wolf Mode', 'img', 13),
       (24, 'Limitless Flow', 'img', 14),
       (25, 'Aesthetic Wallpapers', 'color', 15),
       (26, 'Cyber Case', 'img', 16),
       (27, 'BlueLight', 'color', 6),
       (28, 'Food Lover', 'img', 16),
       (29, 'Army', 'img', 17),
       (30, 'Fine Dine', 'color', 20);

insert into favorites(id, member_id, board_id, work_space_id)
values (1, 5, 1, 5),
       (2, 5, 5, 1),
       (3, 4, 3, 4),
       (4, 4, 4, 4),
       (5, 3, 6, 3),
       (6, 3, 3, 6),
       (7, 1, 6, 1),
       (8, 1, 1, 3),
       (9, 2, 6, 1),
       (10, 2, 7, 2),
       (11, 2, 4, 3),
       (12, 2, 5, 4),
       (13, 2, 2, 5),
       (14, 2, 1, 7),
       (15, 2, 2, 10),
       (16, 2, 3, 11),
       (17, 2, 4, 12),
       (18, 2, 5, 13);

insert into user_work_space_roles(id, role, member_id, work_space_id)
values (1, 'ADMIN', 5, 5),
       (2, 'MEMBER', 4, 4),
       (3, 'ADMIN', 3, 3),
       (4, 'MEMBER', 2, 2),
       (5, 'ADMIN', 1, 1),
       (6, 'ADMIN', 6, 6),
       (7, 'MEMBER', 7, 7),
       (8, 'ADMIN', 8, 8),
       (9, 'MEMBER', 9, 9),
       (10, 'ADMIN', 10, 10),
       (11, 'MEMBER', 11, 11);

insert into columns(id, title, is_archive, board_id)
values (1, 'Done', false, 1),
       (2, 'Attention', false, 2),
       (3, 'Done', false, 3),
       (4, 'Attention', false, 4),
       (5, 'Progress', false, 5),
       (6, 'Code rewiue', false, 6),
       (7, 'KICK BACK', false, 7),
       (8, 'Done', false, 8),
       (9, 'Attention', false, 9),
       (10, 'TODO', false, 10),
       (11, 'Attention', false, 2),
       (12, 'Progress', false, 12),
       (13, 'Attention', false, 21),
       (14, 'Final', false, 5),
       (15, 'Attention', false, 22),
       (16, 'Done', false, 2);


insert into cards(id, title, description, is_archive, created_date, column_id, creator_id)
values (1, 'title', 'Чарльз Бэббидж считается пионером вычислительной техники. Бэббидж имел чёткое представление о механических вычислениях чисел и таблиц.', false, '2023-09-07', 1, 1),
       (2, 'word', ' К 1830 году Бэббидж придумал план, как разработать машину, которая могла использовать перфокарты для выполнения арифметических операций.', false, '2023-09-07', 2, 1),
       (3, 'title', 'Предполагалось, что машина должна хранить числа в блоках памяти и содержать форму последовательного управления. Это означает, что операции', false, '2023-09-09', 3, 1),
       (4, 'word', 'Чарльз Бэббидж считается пионером вычислительной техники. Бэббидж имел чёткое представление о механических вычислениях чисел и таблиц. ', false, '2023-09-10', 4, 4),
       (5, 'title', 'К 1830 году Бэббидж придумал план, как разработать машину, которая могла использовать перфокарты для выполнения арифметических операций.', false, '2023-09-11', 4, 2),
       (6, 'word', ' Предполагалось, что машина должна хранить числа в блоках памяти и содержать форму последовательного управления. Это означает, что операции', false, '2023-09-12', 7, 4),
       (7, 'title', 'Чарльз Бэббидж считается пионером вычислительной техники. Бэббидж имел чёткое представление о механических вычислениях чисел и таблиц. ', false, '2023-09-13', 9, 5),
       (8, 'word', 'К 1830 году Бэббидж придумал план, как разработать машину, которая могла использовать перфокарты для выполнения арифметических операций.', false, '2023-09-14', 1, 2),
       (9, 'title', 'Предполагалось, что машина должна хранить числа в блоках памяти и содержать форму последовательного управления. Это означает, что операции ', false, '2023-09-15', 6, 4),
       (10, 'word', 'Чарльз Бэббидж считается пионером вычислительной техники. Бэббидж имел чёткое представление о механических вычислениях чисел и таблиц. ', false, '2023-09-16', 11, 5),
       (11, 'title', 'К 1830 году Бэббидж придумал план, как разработать машину, которая могла использовать перфокарты для выполнения арифметических операций.', false, '2023-09-18', 2, 3),
       (12, 'word', 'Предполагалось, что машина должна хранить числа в блоках памяти и содержать форму последовательного управления. Это означает, что операции', false, '2023-09-19', 12, 3),
       (13, 'title', 'Чарльз Бэббидж считается пионером вычислительной техники. Бэббидж имел чёткое представление о механических вычислениях чисел и таблиц.', false, '2023-09-20', 13, 5),
       (14, 'word', 'К 1830 году Бэббидж придумал план, как разработать машину, которая могла использовать перфокарты для выполнения арифметических операций.', false, '2023-09-21', 16, 2),
       (15, 'title', 'Предполагалось, что машина должна хранить числа в блоках памяти и содержать форму последовательного управления. Это означает, что операции ', false, '2023-09-22', 3, 4),
       (16, 'word', 'Чарльз Бэббидж считается пионером вычислительной техники. Бэббидж имел чёткое представление о механических вычислениях чисел и таблиц. ', false, '2023-09-23', 2, 2),
       (17, 'title', 'К 1830 году Бэббидж придумал план, как разработать машину, которая могла использовать перфокарты для выполнения арифметических операций.', false, '2023-09-24', 2, 2),
       (18, 'word', 'Предполагалось, что машина должна хранить числа в блоках памяти и содержать форму последовательного управления. Это означает, что операции', false, '2023-09-29', 13, 5);

insert into notifications(id, text, image, type, is_read, created_date, card_id,column_id,board_id,from_user_id)
values (1, 'batyraak bol', 'img', 'MOVE', false, '2023-09-11T00:00:00', 2,2,2,2),
       (2, 'vnimanie', 'img', 'MOVE', false, '2023-09-11T00:00:00', 1,1,1,1),
       (3, 'vas dobavili', 'img', 'ASSIGN', false, '2023-09-11T00:00:00', 3,3,3,3),
       (4, 'pravilno', 'img', 'REMINDER', false, '2023-09-11T00:00:00', 4,4,4,4),
       (5, 'normalno', 'img', 'REMINDER', false, '2023-09-11T00:00:00', 5,5,5,5);

insert into labels(id, color, label_name)
values (1, 'Green', 'Done'),
       (2, 'Blue', 'Code rewiev'),
       (3, 'Red', 'Kick back'),
       (4, 'Yellow', 'Final rewiev');


insert into check_lists(id, percent, title, card_id)
values (1, 10, 'writing', 1),
       (2, 20, 'character', 2),
       (3, 30, 'reading', 3),
       (4, 40, 'writing', 4),
       (5, 70, 'task', 5);

insert into comments(id, comment, created_date, card_id, member_id)
values (1, 'Kachan butot', '2023-09-07T12:00:00', 1, 1),
       (2, 'Tuura emesko brat', '2023-09-07T12:00:00', 2, 2),
       (3, 'tutorial please', '2023-09-07T12:00:00', 3, 3),
       (4, 'Harosh', '2023-09-07T12:00:00', 4, 4),
       (5, 'Good job', '2023-09-07T12:00:00', 5, 5);

insert into estimations(id,reminder_type,start_date,due_date,start_time,finish_time,notification_time,card_id)
values (1,'NONE','2023-07-10T10:30:00+03:00','2023-07-11T10:30:00+03:00','2023-07-11T10:30:00+03:00','2023-07-11T10:30:00+03:00','2023-07-11T10:30:00+03:00',1),
       (2,'FIVE_MINUTE','2023-07-09T10:30:00+03:00','2023-07-10T10:30:00+03:00','2023-07-10T10:30:00+03:00','2023-07-11T10:30:00+03:00','2023-07-11T10:30:00+03:00',2),
       (3,'TEN_MINUTE','2023-07-08T10:30:00+03:00','2023-07-09T10:30:00+03:00','2023-07-09T10:30:00+03:00','2023-07-11T10:30:00+03:00','2023-07-11T10:30:00+03:00',3),
       (4,'FIFTEEN_MINUTE','2023-07-07T10:30:00+03:00','2023-07-08T10:30:00+03:00','2023-07-08T10:30:00+03:00','2023-07-11T10:30:00+03:00','2023-07-11T10:30:00+03:00',4),
       (5,'THIRD_MINUTE','2023-07-06T10:30:00+03:00','2023-07-07T10:30:00+03:00','2023-07-07T10:30:00+03:00','2023-07-11T10:30:00+03:00','2023-07-11T10:30:00+03:00',5);

insert into items(id, is_done, title, check_list_id)
values (1, false, 'word', 1),
       (2, false, 'read', 2),
       (3, false, 'word', 3),
       (4, false, 'read', 4),
       (5, false, 'word', 5);

insert into attachments(id, created_at, document_link, card_id)
values (1, '2023-09-07T12:00:00', 'link', 1),
       (2, '2023-09-07T12:00:00', 'link', 2),
       (3, '2023-09-07T23:00:00', 'link', 3),
       (4, '2023-09-07T23:00:00', 'link', 4),
       (5, '2023-09-07T23:00:00', 'link', 5);

insert into users_work_spaces(members_id, work_spaces_id)
values (1, 1),
       (1, 13),
       (8, 8),
       (9, 9),
       (10, 10),
       (11, 11),
       (2, 2),
       (2, 12),
       (2, 14),
       (2, 15),
       (2, 16),
       (3, 3),
       (3, 17),
       (3, 18),
       (3, 19),
       (3, 20),
       (3, 21),
       (3, 22),
       (4, 4),
       (5, 5),
       (4, 23),
       (4, 24),
       (4, 25),
       (4, 26),
       (6, 6),
       (5, 27),
       (5, 28),
       (5, 29),
       (5, 30);

insert into notifications_members(notifications_id, members_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 5),
       (2, 6),
       (2, 7),
       (2, 8),
       (3, 9),
       (3, 10),
       (3, 11),
       (3, 12),
       (4, 13),
       (5, 14),
       (5, 15);

insert into labels_cards(cards_id, labels_id)
values (1,1),
       (1,2),
       (1,3),
       (1,4),
       (2,1),
       (2,2),
       (2,3),
       (2,4),
       (3,1),
       (3,2),
       (3,3),
       (3,4),
       (4,1),
       (4,2),
       (4,3),
       (4,4),
       (5,1),
       (5,2),
       (5,3),
       (5,4),
       (6,1),
       (6,2),
       (6,3),
       (6,4),
       (7,1),
       (7,2),
       (7,3),
       (7,4),
       (8,1),
       (8,2),
       (8,3),
       (8,4),
       (9,1),
       (9,2),
       (9,3),
       (9,4),
       (10,1),
       (10,2),
       (10,3),
       (10,4),
       (11,1),
       (11,2),
       (11,3),
       (11,4),
       (12,1),
       (12,2),
       (12,3),
       (12,4),
       (13,1),
       (13,2),
       (13,3),
       (13,4),
       (14,1),
       (14,2),
       (14,3),
       (14,4),
       (15,1),
       (15,2),
       (15,3),
       (15,4),
       (16,1),
       (16,2),
       (16,3),
       (16,4),
       (17,1),
       (17,2),
       (17,3),
       (17,4),
       (18,1),
       (18,2),
       (18,3),
       (18,4);


insert into columns_members(columns_id, members_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 7),
       (3, 8),
       (3, 9),
       (4, 10),
       (4, 11),
       (4, 12),
       (5, 13),
       (5, 14),
       (5, 15);

insert into cards_members(cards_id, members_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (2, 8),
       (2, 9),
       (3, 7),
       (3, 8),
       (3, 9),
       (4, 10),
       (4, 11),
       (4, 12),
       (5, 13),
       (5, 14),
       (5, 15),
       (6, 1),
       (6, 2),
       (6, 3),
       (7, 4),
       (7, 5),
       (7, 6),
       (8, 7),
       (8, 8),
       (8, 9),
       (9, 10),
       (9, 11),
       (9, 12),
       (10, 13),
       (11, 1),
       (12, 12),
       (13, 13),
       (13, 3),
       (16, 11),
       (17, 10),
       (18, 9),
       (11, 7);

insert into boards_members(boards_id, members_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 7),
       (3, 8),
       (3, 9),
       (4, 10),
       (4, 11),
       (4, 12),
       (5, 13),
       (5, 14),
       (5, 15),
       (6, 1),
       (6, 2),
       (6, 3),
       (7, 4),
       (7, 5),
       (7, 6),
       (8, 7),
       (8, 8),
       (8, 9),
       (9, 10),
       (9, 11),
       (9, 12),
       (10, 13),
       (10, 14),
       (10, 15);