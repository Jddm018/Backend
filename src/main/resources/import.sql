
INSERT INTO users (name, last_name, email, password) VALUE ('Camilo','Cabrales', 'camilocabrales2005@gmail.com','camilo12345');
INSERT INTO users (name, last_name, email, password) VALUES ('Ezequiel', 'Palencia','palenciaezequiel09@gmail.com','ezequiel12345'); 
INSERT INTO users (name, last_name, email, password ) VALUES ('Jesus', 'Deulufeutt', 'jesusdeulufeuttmendez@gmail.com','jesus12345'); 

INSERT INTO product(name, price, category, images) VALUES('Plancha Asadora', 1000000,  'Industrial', 'http://localhost:8080/images/i1.png');
INSERT INTO product(name, price, category, images) VALUES ('Meson de Lavaplatos', 300000, 'Industrial', 'http://localhost:8080/images/i2.png'); 
INSERT INTO product (name, price, category, images) VALUES ('Meson de Trabajo pesado', 550000, 'Industrial', 'http://localhost:8080/images/i3.png');
INSERT INTO product(name, price, category, images) VALUES('Freidora Industrial', 1200000,  'Industrial', 'http://localhost:8080/images/i4.png');
INSERT INTO product(name, price, category, images) VALUES ('Freidora Doble', 2400000, 'Industrial', 'http://localhost:8080/images/i5.png'); 
INSERT INTO product (name, price, category, images) VALUES ('Extractor Tipo Hongo', 1500000, 'Industrial', 'http://localhost:8080/images/i6.png');
INSERT INTO product(name, price, category, images) VALUES('Estufa Industrial 4 Puestos', 1950000,  'Industrial', 'http://localhost:8080/images/i7.png');
INSERT INTO product(name, price, category, images) VALUES ('Estufa Industrial 6 Puestos', 3250000, 'Industrial', 'http://localhost:8080/images/i8.png'); 
INSERT INTO product (name, price, category, images) VALUES ('Campana Extractora', 4000000, 'Industrial', 'http://localhost:8080/images/i9.png');
INSERT INTO product (name, price, category, images) VALUES ('Barbacoa Plana', 2300000, 'Industrial', 'http://localhost:8080/images/i10.png');


INSERT INTO buys(date, id_product, id_user, quantity) VALUES ('2006-02-12-13-12-12', 1, 1, 10);
INSERT INTO buys(date, id_product, id_user, quantity) VALUES ('2006-02-14-13-12-12', 2, 3, 7);
INSERT INTO buys(date, id_product, id_user, quantity) VALUES ('2006-02-14-13-12-12', 3, 3, 3);

INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('ADMIN');

INSERT INTO role_user (user_id, role_id) VALUES (1, 2);
INSERT INTO role_user (user_id, role_id) VALUES (2, 1);
INSERT INTO role_user (user_id, role_id) VALUES (3, 1);