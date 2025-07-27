-- =========================
-- Tabla: ROL (ID, NOMBRE)
-- =========================

INSERT INTO ROL (ID, NOMBRE) VALUES (1, 'ADMINISTRADOR');
INSERT INTO ROL (ID, NOMBRE) VALUES (2, 'RECEPCIONISTA');
INSERT INTO ROL (ID, NOMBRE) VALUES (3, 'EMPLEADO');
INSERT INTO ROL (ID, NOMBRE) VALUES (4, 'VETERINARIO');

-- ===========================================
-- Tabla: USUARIO (ID, USERNAME, NOMBRECOMPLETO, CORREO, CLAVE, PRIMERINGRESO, rol_id)
-- ===========================================

INSERT INTO USUARIO (ID, USERNAME, NOMBRECOMPLETO, CORREO, CLAVE, PRIMERINGRESO, rol_id)
VALUES (1, 'migueladm', 'Miguel Armas', 'admin@patitas.com', 'uVQoLxtZvlhBuamIlWRLGQ==', 1, 1);

INSERT INTO USUARIO (ID, USERNAME, NOMBRECOMPLETO, CORREO, CLAVE, PRIMERINGRESO, rol_id)
VALUES (2, 'domenica', 'Domenica Rojas', 'dome_a@patitas.com', 'uVQoLxtZvlhBuamIlWRLGQ==', 1, 2);

INSERT INTO USUARIO (ID, USERNAME, NOMBRECOMPLETO, CORREO, CLAVE, PRIMERINGRESO, rol_id)
VALUES (3, 'victor', 'Victor Roa', 'victor_r@patitas.com', 'uVQoLxtZvlhBuamIlWRLGQ==', 1, 3);

-- ==============================================
-- Tabla: HABITACION (ID, CAPACIDAD, ESTADO, TIPO, mascota_actual_id)
-- ==============================================

INSERT INTO HABITACION (ID, CAPACIDAD, ESTADO, TIPO, mascota_actual_id)
VALUES (1, 1, 'DISPONIBLE', 'INDIVIDUAL', NULL);

INSERT INTO HABITACION (ID, CAPACIDAD, ESTADO, TIPO, mascota_actual_id)
VALUES (2, 1, 'OCUPADA', 'INDIVIDUAL', NULL);
