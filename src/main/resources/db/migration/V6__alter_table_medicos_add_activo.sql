ALTER TABLE medicos add activo tinyint not null;
UPDATE medicos SET activo = 1;
