-- MySQL dump 10.13  Distrib 5.6.20, for osx10.8 (x86_64)
--
-- Host: localhost    Database: eos
-- ------------------------------------------------------
-- Server version	5.6.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review0` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review1` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review2` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review3` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review4` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review5` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review6`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review6` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review7`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review7` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review8`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review8` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review9`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review9` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review10`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review10` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review11`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review11` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review12`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review12` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review13`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review13` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review14`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review14` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review31`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review31` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review15`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review15` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review16`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review16` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review17`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review17` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review18`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review18` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review19`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review19` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review20`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review20` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review21`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review21` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review22`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review22` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review23`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review23` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review24`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review24` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review25`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review25` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review26`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review26` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review27`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review27` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review28`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review28` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review29`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review29` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review30`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review30` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expId` bigint(20) DEFAULT NULL,
  `isChildReview` bit(1) DEFAULT NULL,
  `content` longtext,
  `createTs` bigint(20) DEFAULT NULL,
  `floor` int(11) NOT NULL,
  `reviewed` varchar(255) DEFAULT NULL,
  `reviewer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;










