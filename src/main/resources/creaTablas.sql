
drop schema if exists unfold_cr;
drop user if exists admin; 
drop user if exists usuarioreportes;

CREATE SCHEMA unfold_cr;


create user 'admin'@'%' identified by 'admin'; 
create user 'usuarioreportes'@'%' identified by 'admin'; 

grant all privileges on unfold_cr.* to 'admin'@'%';
grant select on unfold_cr.* to 'usuarioreportes'@'%';
flush privileges;

use unfold_cr;


create table categoria (
  id_categoria INT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(100) NOT NULL, 
  ruta_imagen varchar(1024),
  activo bool,
  PRIMARY KEY (id_categoria))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

create table producto (
  id_producto INT NOT NULL AUTO_INCREMENT,
  id_categoria INT NOT NULL,
  descripcion VARCHAR(255) NOT NULL, 
  detalle VARCHAR(1600) NOT NULL,
  precio double,
  existencias int,
  ruta_imagen varchar(1024),
  activo bool,
  PRIMARY KEY (id_producto),
  foreign key fk_producto_caregoria (id_categoria) references categoria(id_categoria)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE usuario (
  id_usuario INT NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL UNIQUE, 
  password varchar(512) NOT NULL,
  nombre VARCHAR(50) NOT NULL, 
  apellidos VARCHAR(50) NOT NULL, 
  correo VARCHAR(100) NULL, 
  telefono VARCHAR(20) NULL, 
  ruta_imagen varchar(1024),
  activo boolean,
  PRIMARY KEY (`id_usuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

create table factura (
  id_factura INT NOT NULL AUTO_INCREMENT,
  id_usuario INT NOT NULL,
  fecha datetime,
  total double,
  estado VARCHAR(50), -- Estado como string (ej: 'Activa', 'Pagada', 'Anulada')
  PRIMARY KEY (id_factura),
  foreign key fk_factura_usuario (id_usuario) references usuario(id_usuario)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

create table venta (
  id_venta INT NOT NULL AUTO_INCREMENT,
  id_factura INT NOT NULL,
  id_producto INT NOT NULL,
  precio double,
  cantidad int,
  talla VARCHAR(10) NULL,
  color VARCHAR(50) NULL,
  PRIMARY KEY (id_venta),
  foreign key fk_ventas_factura (id_factura) references factura(id_factura),
  foreign key fk_ventas_producto (id_producto) references producto(id_producto)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


INSERT INTO usuario (id_usuario, username,password,nombre, apellidos, correo, telefono,ruta_imagen,activo) VALUES
(1,'carlosmora','$2a$10$P1.w58XvnaYQUQgZUCk4aO/RTRl8EValluCqB3S2VMLTbRt.tlre.','Carlos', 'Mora Ramirez', 'carlos.mora@unfoldcr.com', '8888-1111', 'https://example.com/carlos_perfil.jpg',true),
(2,'anasoto','$2a$10$GkEj.ZzmQa/aEfDmtLIh3udIH5fMphx/35d0EYeqZL5uzgCJ0lQRi','Ana', 'Soto Vargas', 'ana.soto@unfoldcr.com', '7777-2222','https://example.com/ana_perfil.jpg',true),
(3,'luisfernandez','$2a$10$koGR7eS22Pv5KdaVJKDcge04ZB53iMiw76.UjHPY.XyVYlYqXnPbO','Luis', 'Fernández Solís', 'luis.fernandez@unfoldcr.com', '6666-3333','https://example.com/luis_perfil.jpg',true);

USE unfold_cr;
UPDATE usuario SET username = 'admin' WHERE id_usuario = 1;

INSERT INTO categoria (id_categoria,descripcion,ruta_imagen,activo) VALUES
(1,'Camisetas', 'https://unsplash.com/photos/a-stack-of-white-t-shirts-on-a-white-surface-59yX3y43_0Y', true),
(2,'Pantalones', 'https://unsplash.com/photos/a-pair-of-brown-pants-are-folded-on-a-wooden-surface-Xw_hT8aMDUw', true),
(3,'Vestidos', 'https://unsplash.com/photos/a-person-is-holding-a-hangar-with-a-dress-on-it-2lYhXyN7_hI', true),
(4,'Accesorios', 'https://unsplash.com/photos/a-close-up-of-a-person-wearing-a-silver-watch-S8P_qG_pW8Y', false);


INSERT INTO producto (id_producto,id_categoria,descripcion,detalle,precio,existencias,ruta_imagen,activo) VALUES
(1,1,'Camiseta Básica Hombre','Camiseta 100% algodón, cuello redondo, corte regular. Ideal para el día a día. Varias tallas y colores.',15500,50,'https://unsplash.com/photos/a-man-wearing-a-white-t-shirt-and-blue-jeans-is-standing-in-front-of-a-white-wall-4299b_YJmO8',true),
(2,1,'Camiseta Estampada Mujer','Camiseta de algodón suave con estampado floral. Diseño moderno y cómodo. Tallas S, M, L.',18000,35,'https://unsplash.com/photos/a-woman-in-a-floral-shirt-is-looking-at-the-camera-P_E4eUj2j7M',true),
(3,2,'Pantalón Jean Slim Fit','Jean de mezclilla elástica, corte ajustado. Cierre con cremallera y botón. Varias tallas disponibles.',32000,40,'https://unsplash.com/photos/a-person-s-legs-in-blue-jeans-and-black-sneakers-rS1R7eM8w5s',true),
(4,2,'Pantalón Chino Hombre','Pantalón chino de algodón ligero, corte recto. Perfecto para un look casual o semi-formal. Colores beige y azul marino.',28000,25,'https://unsplash.com/photos/a-person-s-legs-are-crossed-in-a-pair-of-pants-and-shoes-N2B7xYyX-oU',true),
(5,3,'Vestido Midi Verano','Vestido midi ligero con estampado de lunares. Tirantes ajustables y escote en V. Ideal para climas cálidos.',45000,15,'https://unsplash.com/photos/a-woman-in-a-polka-dot-dress-is-standing-in-front-of-a-window-b-x_yIe20XU',true),
(6,3,'Vestido Noche Elegante','Vestido largo de satén con abertura en la pierna. Diseño sofisticado para eventos especiales. Tallas S, M.',57000,10,'https://unsplash.com/photos/a-woman-in-a-black-dress-is-standing-in-front-of-a-mirror-p_eS-lTf62k',true),
(7,4,'Cinturón Cuero Genuino','Cinturón de cuero 100% genuino con hebilla metálica. Duradero y versátil para cualquier atuendo.',25000,60,'https://unsplash.com/photos/a-person-is-holding-a-brown-leather-belt-q3G5Tq_XWd8',true),
(8,4,'Gorra Deportiva UnFold','Gorra ajustable con el logo bordado de UnFold Costa Rica. Ideal para actividades al aire libre.',8500,75,'https://unsplash.com/photos/a-person-in-a-blue-baseball-cap-and-a-black-jacket-jV6e-Jt_Pzo',true);


INSERT INTO factura (id_factura,id_usuario,fecha,total,estado) VALUES
(1,1,'2025-06-05 10:30:00',48500,'Pagado'),
(2,2,'2025-06-07 14:15:00',80000,'Enviado'),
(3,3,'2025-07-07 09:00:00',102000,'Pendiente');

INSERT INTO venta (id_venta,id_factura,id_producto,precio,cantidad,talla,color) values
(1,1,1,15500,2,'M','Blanco'),
(2,1,8,8500,2,'Única','Negra'),
(3,2,3,32000,1,'32','Azul Oscuro'),
(4,2,5,45000,1,'M','Lunares'),
(5,3,6,57000,1,'S','Negro'),
(6,3,7,25000,1,'Única','Marrón'),
(7,3,1,15500,1,'L','Negro');


create table role (
  rol varchar(20),
  PRIMARY KEY (rol)
);

insert into role (rol) values ('ADMIN'), ('VENDEDOR'), ('USER');

create table rol (
  id_rol INT NOT NULL AUTO_INCREMENT,
  nombre varchar(20),
  id_usuario int,
  PRIMARY KEY (id_rol)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


insert into rol (id_rol, nombre, id_usuario) values
  (1,'ADMIN',1), (2,'VENDEDOR',1), (3,'USER',1),
  (4,'VENDEDOR',2), (5,'USER',2),
  (6,'USER',3);


CREATE TABLE ruta (
    id_ruta INT AUTO_INCREMENT NOT NULL,
    patron VARCHAR(255) NOT NULL,
    rol_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_ruta))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

INSERT INTO ruta (patron, rol_name) VALUES
('/producto/nuevo', 'ADMIN'),
('/producto/guardar', 'ADMIN'),
('/producto/modificar/**', 'ADMIN'),
('/producto/eliminar/**', 'ADMIN'),
('/categoria/nuevo', 'ADMIN'),
('/categoria/guardar', 'ADMIN'),
('/categoria/modificar/**', 'ADMIN'),
('/categoria/eliminar/**', 'ADMIN'),
('/usuario/**', 'ADMIN'),
('/constante/**', 'ADMIN'),
('/role/**', 'ADMIN'),
('/usuario_role/**', 'ADMIN'),
('/ruta/**', 'ADMIN'),
('/producto/listado', 'VENDEDOR'),
('/categoria/listado', 'VENDEDOR'),
('/reportes/**', 'VENDEDOR'),
('/facturar/carrito', 'USER'),
('/payment/**', 'USER');

CREATE TABLE ruta_permit (
    id_ruta INT AUTO_INCREMENT NOT NULL,
    patron VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_ruta))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

INSERT INTO ruta_permit (patron) VALUES
('/'),
('/index'),
('/errores/**'),
('/carrito/**'),
('/registro/**'),
('/fav/**'),
('/js/**'),
('/webjars/**'),
('/contacto/**'),
('/faqs/**'),
('/tiendas/**');

CREATE TABLE constante (
    id_constante INT AUTO_INCREMENT NOT NULL,
    atributo VARCHAR(50) NOT NULL, 
    valor VARCHAR(255) NOT NULL, 
    PRIMARY KEY (id_constante))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

INSERT INTO constante (atributo,valor) VALUES
('dominio_app','localhost:8080'),
('ruta_certificados','c:/unfold_certs'), 
('tipo_cambio_dolar','520.75'), 
('paypal.client-id','AUjOjw5Q1I0QLTYjbvRS0j4Amd8xrUU2yL9UYyb3TOTcrazzd3G3lYRc6o7g9rOyZkfWEj2wxxDi0aRz'),
('paypal.client-secret','EMdb08VRlo8Vusd_f4aAHRdTE14ujnV9mCYPovSmXCquLjzWd_EbTrRrNdYrF1-C4D4o-57wvua3YD2u'),
('paypal.mode','sandbox'),
('url_paypal_cancel','http://localhost:8080/payment/cancel'), 
('url_paypal_success','http://localhost:8080/payment/success'); 