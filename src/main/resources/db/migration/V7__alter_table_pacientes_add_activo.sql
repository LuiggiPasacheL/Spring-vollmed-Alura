ALTER TABLE pacientes add activo tinyint not null;
UPDATE pacientes SET activo = 1;
