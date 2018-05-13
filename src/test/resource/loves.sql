/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.7.17-log 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `loves` (
	`id` bigint (20) NOT NULL AUTO_INCREMENT,
	`createdAt` datetime ,
	`updatedAt` datetime ,
	`title` varchar (255),
	`content` varchar (6000),
	`figureImg` varchar (255),
	`figureMsg` varchar (2000),
	`figcaption` varchar (500),
	PRIMARY KEY (`id`)
); 
insert into `loves` (`id`, `createdAt`, `updatedAt`, `title`, `content`, `figureImg`, `figureMsg`, `figcaption`) values('1','2018-05-10 18:12:32','2018-05-10 18:12:32','My Test','先写奥斯卡等级啊是','http://s1.wailian.download/2017/12/18/2017.md.jpg','这是图片','PS 了一晚上才诞生的 logo');
insert into `loves` (`id`, `createdAt`, `updatedAt`, `title`, `content`, `figureImg`, `figureMsg`, `figcaption`) values('2','2018-05-10 18:13:39','2018-05-10 18:13:39','My Test','先写奥斯卡等级啊是','http://s1.wailian.download/2017/12/18/2017.md.jpg','这是图片','PS 了一晚上才诞生的 logo');
insert into `loves` (`id`, `createdAt`, `updatedAt`, `title`, `content`, `figureImg`, `figureMsg`, `figcaption`) values('3','2018-05-10 18:13:59','2018-05-10 18:13:59','My Test','先写奥斯卡等级啊是','http://s1.wailian.download/2017/12/18/2017.md.jpg','这是图片','PS 了一晚上才诞生的 logo');
