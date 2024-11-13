INSERT INTO permisos (id, especialistas, fonoAudiologia, historiaClinica, institucionesEducativas, psicologiaClinica, psicologiaEducativa, pacientes, sede)
VALUES
(1, b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1'),
(2, b'0', b'0', b'0', b'1', b'0', b'0', b'1', b'0'),
(3, b'0', b'0', b'1', b'0', b'0', b'1', b'0', b'0'),
(4, b'0', b'0', b'1', b'0', b'1', b'0', b'0', b'0'),
(5, b'0', b'1', b'1', b'0', b'0', b'0', b'0', b'0'),
(6, b'0', b'0', b'1', b'0', b'0', b'0', b'0', b'0'),
(7, b'0', b'0', b'1', b'0', b'0', b'0', b'0', b'0'),
(8, b'0', b'0', b'1', b'0', b'0', b'0', b'0', b'0');
INSERT INTO Sede (id, estado, nombre)
VALUES
(1, 1, 'CUENCA'),
(2, 1, 'AZOGUES');
INSERT INTO especialidad (id, area, id_permiso)
VALUES
(8, 'Recuperación Pedagogica',8);
-- Insertar Especialistas
INSERT INTO especialista (cedula, primerNombre, segundoNombre, primerApellido, segundoApellido, especialistaAsignado, esPasante, contrasena, especialista_estado, sede_id)
VALUES
('0105360887', 'GABRIELA ', 'ANGELITA', 'JARA', 'SALDAÑA', 1, 0, '0105360887', 1, 1),
('0103159067', 'ZOBEIDA ', 'XIMENA', 'PARRA', 'ORDOÑEZ', 2, 0, '0103159067', 1, 1),
('0102698669', 'MARIA DE LOURDES', NULL, 'CEDILLO', 'ARMIJOS', 4, 0, '0102698669', 1, 1),
('1104010960', 'DUNIA ', 'CECILIA', 'COELLO', 'LUNA', 4, 0, '1104010960', 1, 1),
('0302929872', 'CARMEN ', 'NOEMI', 'ESPINOZA', 'PARRA', 3, 0, '0302929872', 1, 1),
('0302230602', 'FERNANDO ', 'ISMAEL', 'LUCERO', 'ESPINOZA', 3, 0, '0302230602', 1, 1),
('0102428620', 'MAGALY ', 'ESPERANZA', 'AGUIRRE', 'CUESTA', 8, 0, '0102428620', 1, 1),
('0105706105', 'DAYANNA ', 'CAROLINA', 'GARATE', 'RUGEL', 5, 0, '0105706105', 1, 1),
('2222222222', 'ADMIN', NULL, 'ADMIN', 'COORDINADOR', 0, 0, 'admin123', 1, 1);

-- Insertar Especialidad


-- Insertar Jornada
INSERT INTO Jornada (id, estado_jornada, nombre_jornada)
VALUES
(1, 1, 'matutina'),
(2, 1, 'despertina');

-- Insertar Permisos


-- Insertar Sede

