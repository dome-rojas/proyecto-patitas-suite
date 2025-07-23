-- ========================================
-- Tabla: PERMISO (ID, RECURSO, ACCION)
-- ========================================

INSERT INTO PERMISO (ID, recurso, accion) VALUES (1, '/admin/usuarioList.xhtml', 'ALL');
INSERT INTO PERMISO (ID, recurso, accion) VALUES (2, '/admin/usuarioList.xhtml', 'READ');
INSERT INTO PERMISO (ID, recurso, accion) VALUES (3, '/admin/usuarioList.xhtml', 'WRITE');
INSERT INTO PERMISO (ID, recurso, accion) VALUES (4, '/admin/usuarioList.xhtml', 'DELETE');

INSERT INTO PERMISO (ID, recurso, accion) VALUES (5, '/recepcionista/mascota.xhtml', 'ALL');
INSERT INTO PERMISO (ID, recurso, accion) VALUES (6, '/recepcionista/mascota.xhtml', 'READ');
INSERT INTO PERMISO (ID, recurso, accion) VALUES (7, '/recepcionista/mascota.xhtml', 'WRITE');

INSERT INTO PERMISO (ID, recurso, accion) VALUES (8, '/empleado/tareas.xhtml', 'READ');
INSERT INTO PERMISO (ID, recurso, accion) VALUES (9, '/empleado/tareas.xhtml', 'WRITE');

INSERT INTO PERMISO (ID, recurso, accion) VALUES (10, '/veterinario/fichaMedica.xhtml', 'READ');
INSERT INTO PERMISO (ID, recurso, accion) VALUES (11, '/veterinario/fichaMedica.xhtml', 'WRITE');
-- =========================
-- Tabla: ROL (ID, NOMBRE)
-- =========================

INSERT INTO ROL (ID, nombre) VALUES (1, 'ADMIN');
INSERT INTO ROL (ID, nombre) VALUES (2, 'RECEPCIONISTA');
INSERT INTO ROL (ID, nombre) VALUES (3, 'EMPLEADO');
INSERT INTO ROL (ID, nombre) VALUES (4, 'VETERINARIO');

-- ============================
-- Tabla: USUARIO (ID, NOMBRE, APELLIDO, CORREO, CLAVE, PRIMER_INGRESO, rol_id)
-- ============================

-- Contraseña cifrada (ejemplo): "admin123" en Base64 AES -> 'uVQoLxtZvlhBuamIlWRLGQ=='
INSERT INTO USUARIO (ID, nombre, apellido, correo, clave, primerIngreso, rol_id)
VALUES (1, 'admin', 'principal', 'admin@patitas.com', 'uVQoLxtZvlhBuamIlWRLGQ==', true, 1);
INSERT INTO USUARIO (ID, nombre, apellido, correo, clave, primerIngreso, rol_id)
VALUES (2, 'Sonia', 'Armijos', 'soniaA@patitas.com', 'uVQoLxtZvlhBuamIlWRLGQ==', true, 2);
INSERT INTO USUARIO (ID, nombre, apellido, correo, clave, primerIngreso, rol_id)
VALUES (3, 'Raul', 'Gonzales', 'raulL@patitas.com', 'uVQoLxtZvlhBuamIlWRLGQ==', true, 3);


-- ================================
-- Tabla intermedia: user_Permision (rol_id, permission_id)
-- ================================

-- ADMIN con todo acceso
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 1);
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 2);
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 3);
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 4);
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 5);
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 6);
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 7);
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 8);
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 9);
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 10);
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 11);
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 12);
INSERT INTO user_Permision (role_id, permission_id) VALUES (1, 13);

-- RECEPCIONISTA: gestiona mascotas y clientes
INSERT INTO user_Permision (role_id, permission_id) VALUES (2, 5);
INSERT INTO user_Permision (role_id, permission_id) VALUES (2, 6);
INSERT INTO user_Permision (role_id, permission_id) VALUES (2, 7);
INSERT INTO user_Permision (role_id, permission_id) VALUES (2, 12);
INSERT INTO user_Permision (role_id, permission_id) VALUES (2, 13);

-- EMPLEADO: ve y marca tareas
INSERT INTO user_Permision (role_id, permission_id) VALUES (3, 8);
INSERT INTO user_Permision (role_id, permission_id) VALUES (3, 9);

-- VETERINARIO: acceso a ficha médica
INSERT INTO user_Permision (role_id, permission_id) VALUES (4, 10);
INSERT INTO user_Permision (role_id, permission_id) VALUES (4, 11);

COMMIT;
