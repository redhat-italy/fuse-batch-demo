use integration;

CREATE TABLE orders
(
id INTEGER AUTO_INCREMENT,
product TEXT,
vendor TEXT,
quantity INT,
orderdate  DATETIME,
PRIMARY KEY (id)
) COMMENT='Orders test tables';

INSERT INTO orders ( product, vendor, quantity, orderdate) VALUES ('Sphere', 'Volume inc', 1, '2014-11-10');
INSERT INTO orders ( product, vendor, quantity, orderdate) VALUES ('Cube', 'Volume inc', 1, '2015-2-18');
INSERT INTO orders ( product, vendor, quantity, orderdate) VALUES ('Cylinder', 'Volume inc', 1, '2015-3-20');
INSERT INTO orders ( product, vendor, quantity, orderdate) VALUES ('Circle', 'Planar ltd', 1, '2011-3-4');
INSERT INTO orders ( product, vendor, quantity, orderdate) VALUES ('Square', 'Planar ltd', 1, '2014-5-5');
INSERT INTO orders ( product, vendor, quantity, orderdate) VALUES ('Rectangle', 'Planar ltd', 1, '2017-1-6');