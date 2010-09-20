INSERT INTO Categoria VALUES (default, 'Fútbol');
INSERT INTO Categoria VALUES (default, 'Baloncesto');
INSERT INTO Categoria VALUES (default, 'Balonmano');
INSERT INTO Categoria VALUES (default, 'Tenis');
INSERT INTO Categoria VALUES (default, 'Fórmula 1');
INSERT INTO Categoria VALUES (default, 'Categoria sin eventos');

INSERT INTO Cuenta VALUES (default, 0, 0);
INSERT INTO Cuenta VALUES (default, 50.00, 0);

INSERT INTO Usuario VALUES (default, 'admin', 'WCP4a.zz3uLco', 'Admin', 'Admin', 'admin@biwin.es', 1, 0);
INSERT INTO Usuario VALUES (default, 'sys', 'OW01w1OzWLfm6', 'Diego', 'Sobradelo', 'diego@biwin.es', 2, 0);

INSERT INTO Evento VALUES (Default, 'Valencia - Mallorca', '2011-02-10 22:00:00', 1, 0);
INSERT INTO Evento VALUES (Default, 'Deportivo - Espanyol', '2011-02-10 22:00:00', 1, 0);
INSERT INTO Evento VALUES (Default, 'Real Madrid - Sevilla', '2011-02-10 22:00:00', 1, 0);
INSERT INTO Evento VALUES (Default, 'Barcelona - Real Madrid', '2011-02-10 22:00:00', 1, 0);
INSERT INTO Evento VALUES (Default, 'Manchester - Chelsea', '2009-02-14 20:45:00', 1, 0);
INSERT INTO Evento VALUES (Default, 'Barcelona - Real Madrid', '2011-04-15 20:00:00', 2, 0);
INSERT INTO Evento VALUES (Default, 'TAU - Pamesa', '2009-05-05 20:00:00', 2, 0);
INSERT INTO Evento VALUES (Default, 'Panatinaikos - CSKA Moscu', '2011-04-25 22:00:00', 2, 0);
INSERT INTO Evento VALUES (Default, 'Ciudad Real - Barcelona', '2011-06-01 16:00:00', 3, 0);
INSERT INTO Evento VALUES (Default, 'Wawrinka - Tommy Robredo', '2011-10-24 17:00:00', 4, 0);
INSERT INTO Evento VALUES (Default, 'Nadal - Federer', '2009-01-27 16:00:00', 4, 0);
INSERT INTO Evento VALUES (Default, 'Gran Premio de San Marino', '2006-04-25 14:00:00', 5, 0);
INSERT INTO Evento VALUES (Default, 'GP Monaco', '2011-05-19 14:00:00', 5, 0);

INSERT INTO TipoApuesta VALUES (Default, '1x2', 0, 0, 1, 0);
INSERT INTO TipoApuesta VALUES (Default, 'Quien marcara gol?', 0, 1, 1, 0);
INSERT INTO TipoApuesta VALUES (Default, '1x2', 0, 0, 5, 0);
INSERT INTO TipoApuesta VALUES (Default, 'Quien marcara gol?', 0, 1, 5, 0);
INSERT INTO TipoApuesta VALUES (Default, 'Quien ganara?', 0, 0, 6, 0);
INSERT INTO TipoApuesta VALUES (Default, 'Habra prorroga?', 0, 0, 6, 0);
INSERT INTO TipoApuesta VALUES (Default, 'Quien ganara?', 1, 0, 7, 0);
INSERT INTO TipoApuesta VALUES (Default, 'Quien ganara?', 0, 0, 8, 0);
INSERT INTO TipoApuesta VALUES (Default, '1x2', 0, 0, 9, 0);
INSERT INTO TipoApuesta VALUES (Default, 'Quien ganara?', 0, 0, 10, 0);
INSERT INTO TipoApuesta VALUES (Default, 'Quien ganara?', 0, 0, 11, 0);
INSERT INTO TipoApuesta VALUES (Default, 'Duracion del partido', 1, 0, 11, 0);
INSERT INTO TipoApuesta VALUES (Default, 'Quien ganara?', 0, 0, 12, 0);
INSERT INTO TipoApuesta VALUES (Default, 'Quien estara en la Q3?', 0, 1, 13, 0);
INSERT INTO TipoApuesta VALUES (Default, 'Quien ganara?', 0, 0, 13, 0);

INSERT INTO OpcionApuesta VALUES (Default, 'Valencia', 1.35, 0, 1, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Mallorca', 2, 0, 1, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Empate', 1.5, 0, 1, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Villa', 1.10, 0, 2, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Silva', 1.5, 0, 2, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Webo', 2, 0, 2, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Corrales', 5, 0, 2, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Manchester', 1.90, 0, 3, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Chelsea', 1.95, 0, 3, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Empate', 3, 0, 3, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Rooney', 1.45, 0, 4, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Drogba', 1.75, 0, 4, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Terry', 15, 0, 4, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Barcelona', 1.40, 0, 5, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Madrid', 2.30, 0, 5, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Si', 8, 0, 6, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'No', 1.55, 0, 6, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'TAU', 1.40, 0, 7, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Pamesa', 2.30, 1, 7, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Nadal', 1.70, 0, 11, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Federer', 2.10, 0, 11, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Menos de 2 horas', 15, 0, 12, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Entre 2 y 3 horas', 8, 0, 12, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Entre 3 y 4 horas', 2.10, 1, 12, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Mas de 4 horas', 1.30, 0, 12, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Alonso', 1.40, 0, 13, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Schumacher', 1.20, 0, 13, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Montoya', 2.80, 0, 13, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Raikkonen', 4.20, 0, 13, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Alonso', 1.10, 0, 14, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Hamilton', 1.10, 0, 14, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Schumacher', 1.10, 0, 14, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Alguersuari', 20, 0, 14, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'De la Rosa', 20, 0, 14, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Chandhok', 70, 0, 14, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Alonso', 1.40, 0, 15, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Schumacher', 5.20, 0, 15, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Hamilton', 3.10, 0, 15, 0);
INSERT INTO OpcionApuesta VALUES (Default, 'Vettel', 1.10, 0, 15, 0);

INSERT INTO Apuesta VALUES (Default, default, 15, 2, 1);
INSERT INTO Apuesta VALUES (Default, default, 10, 2, 4);
INSERT INTO Apuesta VALUES (Default, default, 10, 2, 6);
INSERT INTO Apuesta VALUES (Default, default, 5, 2, 8);
INSERT INTO Apuesta VALUES (Default, default, 25, 2, 18);
INSERT INTO Apuesta VALUES (Default, default, 5, 2, 20);
INSERT INTO Apuesta VALUES (Default, default, 10, 2, 23);
INSERT INTO Apuesta VALUES (Default, default, 40, 2, 25);
INSERT INTO Apuesta VALUES (Default, default, 20, 2, 30);
INSERT INTO Apuesta VALUES (Default, default, 15, 2, 31);
INSERT INTO Apuesta VALUES (Default, default, 25, 2, 32);
INSERT INTO Apuesta VALUES (Default, default, 50, 2, 35);
INSERT INTO Apuesta VALUES (Default, default, 30, 2, 36);


