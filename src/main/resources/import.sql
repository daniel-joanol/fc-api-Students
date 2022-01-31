INSERT INTO roles (name) VALUES ('ADMIN');

INSERT INTO users (username, password, name) VALUES ('admin@ob.com', '$2a$10$IvKJD.poRVCWu3GqJiNMNOz8RE7ob2WDU/r4DYWpuEXcGfgdzIXJu', 'Admin');
INSERT INTO users (username, password, name) VALUES ('joanoldaniel@gmail.com', '$2a$10$IvKJD.poRVCWu3GqJiNMNOz8RE7ob2WDU/r4DYWpuEXcGfgdzIXJu', 'Daniel');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 1);

INSERT INTO tags (name) VALUES ('Java');
INSERT INTO tags (name) VALUES ('Spring');
INSERT INTO tags (name) VALUES ('J-Unit');
INSERT INTO tags (name) VALUES ('Hibernate');
INSERT INTO tags (name) VALUES ('Git');
INSERT INTO tags (name) VALUES ('CSS-HTML');
INSERT INTO tags (name) VALUES ('JavaScript');
INSERT INTO tags (name) VALUES ('React');
INSERT INTO tags (name) VALUES ('Angular');

INSERT INTO countries(name) VALUES ('España');
INSERT INTO countries(name) VALUES ('Brasil');

INSERT INTO cities(name) VALUES ('Ávila');
INSERT INTO cities(name) VALUES ('Barcelona');
INSERT INTO cities(name) VALUES ('Madrid');
INSERT INTO cities(name) VALUES ('Sevilla');

INSERT INTO cities(name) VALUES ('Florianópolis');
INSERT INTO cities(name) VALUES ('Porto Alegre');
INSERT INTO cities(name) VALUES ('Rio de Janeiro');
INSERT INTO cities(name) VALUES ('São Paulo');

INSERT INTO countries_cities(country_id, city_id) VALUES ('1', '1');
INSERT INTO countries_cities(country_id, city_id) VALUES ('1', '2');
INSERT INTO countries_cities(country_id, city_id) VALUES ('1', '3');
INSERT INTO countries_cities(country_id, city_id) VALUES ('1', '4');
INSERT INTO countries_cities(country_id, city_id) VALUES ('2', '5');
INSERT INTO countries_cities(country_id, city_id) VALUES ('2', '6');
INSERT INTO countries_cities(country_id, city_id) VALUES ('2', '7');
INSERT INTO countries_cities(country_id, city_id) VALUES ('2', '8');