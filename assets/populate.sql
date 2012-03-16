CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, password TEXT NOT NULL);"
CREATE TABLE projects (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, description TEXT NOT NULL, user_id INTEGER);"
CREATE TABLE tasks (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, description TEXT NOT NULL, time INTEGER, date DATE, project_id INTEGER, activity_id INTEGER);
CREATE TABLE activities (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL);
INSERT INTO `activities` (`name`) VALUES ('Self development');
INSERT INTO `activities` (`name`) VALUES ('Working time');
INSERT INTO `activities` (`name`) VALUES ('Extra time');
INSERT INTO `activities` (`name`) VALUES ('Team time');
INSERT INTO `users` (`name`,`password`) VALUES ('1','1');
INSERT INTO `projects` (`name`,`description`,`user_id`) VALUES ('1 project','description','1');
INSERT INTO `projects` (`name`,`description`,`user_id`) VALUES ('2 project','description','1');
INSERT INTO `projects` (`name`,`description`,`user_id`) VALUES ('3 project','description','1');
INSERT INTO `projects` (`name`,`description`,`user_id`) VALUES ('4 project','description','1');
INSERT INTO `projects` (`name`,`description`,`user_id`) VALUES ('5 project','description','1');
INSERT INTO `projects` (`name`,`description`,`user_id`) VALUES ('6 project','description','1');
INSERT INTO `projects` (`name`,`description`,`user_id`) VALUES ('N/A','description','1');
INSERT INTO `tasks` (`name`,`description`,`time`,`date`,`project_id`,`activity_id`) VALUES ('Drink Cola','description','124','2012-03-14', '1', '1');
INSERT INTO `tasks` (`name`,`description`,`time`,`date`,`project_id`,`activity_id`) VALUES ('saving the world','description','524','2012-03-14', '1', '1');
INSERT INTO `tasks` (`name`,`description`,`time`,`date`,`project_id`,`activity_id`) VALUES ('Fixing bug','description','224','2012-03-15', '1', '1');
INSERT INTO `tasks` (`name`,`description`,`time`,`date`,`project_id`,`activity_id`) VALUES ('Creating app','description','324','2012-03-16', '1', '2');
INSERT INTO `tasks` (`name`,`description`,`time`,`date`,`project_id`,`activity_id`) VALUES ('Back to the future','description','444','2012-03-15', '1', '2');
INSERT INTO `tasks` (`name`,`description`,`time`,`date`,`project_id`,`activity_id`) VALUES ('Trip to the moon','description','600','2012-03-15', '1', '3');
INSERT INTO `tasks` (`name`,`description`,`time`,`date`,`project_id`,`activity_id`) VALUES ('Exploring black hole','description','90','2012-03-15', '1', '3');