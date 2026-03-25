-- MariaDB dump 10.19  Distrib 10.4.32-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: ERP
-- ------------------------------------------------------
-- Server version	10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `deductions`
--

DROP TABLE IF EXISTS `deductions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deductions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deduction_name` varchar(255) NOT NULL,
  `percentage` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deductions`
--

LOCK TABLES `deductions` WRITE;
/*!40000 ALTER TABLE `deductions` DISABLE KEYS */;
INSERT INTO `deductions` VALUES (1,'employee tax',30),(2,'pension',6),(3,'medical insurance',5),(4,'others',5),(5,'housing',14),(6,'transport',14);
/*!40000 ALTER TABLE `deductions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `password` varchar(100) NOT NULL,
  `roles` enum('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_MANAGER') NOT NULL,
  `status` enum('ACTIVE','DISABLED') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfopic1oh5oln2khj8eat6ino0` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (6,'2005-05-29','mu.gloria405@gmail.com','Musimenta','Gloria','1111111111','$2a$10$ZFQ/RlZWZffuRm6xwwQRc.UA5Qk9tIasOZmPZt9SE10KBcelK053C','ROLE_MANAGER','ACTIVE'),(7,'2005-05-29','mugabo@gmail.com','Mugabo','Javis','1111111111','$2a$10$Ke6c6A50zb2kYvrjAzjpiu.1jGT/2xLbyJXEVUR4C0N5W5qI0mvlW','ROLE_ADMIN','ACTIVE'),(8,'2000-05-29','uwamahorobonaventure@gmail.com','Uwamahoro','Bonaventure','1111111111','$2a$10$mFlWScqPUbE6dicrcp3/oOiANN6BAI4LzYI1H2Aseamv7m0J8kx4K','ROLE_EMPLOYEE','ACTIVE');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employment`
--

DROP TABLE IF EXISTS `employment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `base_salary` double NOT NULL,
  `department` varchar(255) NOT NULL,
  `joining_date` date DEFAULT NULL,
  `position` varchar(255) NOT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe4av0khvk9h1ixjrt9i3un3my` (`employee_id`),
  CONSTRAINT `FKe4av0khvk9h1ixjrt9i3un3my` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employment`
--

LOCK TABLES `employment` WRITE;
/*!40000 ALTER TABLE `employment` DISABLE KEYS */;
INSERT INTO `employment` VALUES (1,70000,'tech','2024-05-29','HR','ACTIVE',7),(2,100000,'TECH','2020-05-29','CEO','ACTIVE',8);
/*!40000 ALTER TABLE `employment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message` text DEFAULT NULL,
  `month` int(11) NOT NULL,
  `sent_at` datetime(6) DEFAULT NULL,
  `year` int(11) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnt0c0ugqu26cuj5vyqhmrgrvi` (`employee_id`),
  CONSTRAINT `FKnt0c0ugqu26cuj5vyqhmrgrvi` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'Your payslip for May 2025 has been processed.',5,'2025-05-29 18:16:46.000000',2025,8),(2,'Dear Uwamahoro, Your salary of 5/2025 from ERP Corp 63600.00 has been credited to your 8 account successfully.',5,'2025-05-29 18:26:23.000000',2025,8);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_slip`
--

DROP TABLE IF EXISTS `pay_slip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pay_slip` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `employee_taxed_amount` double NOT NULL,
  `gross_salary` double NOT NULL,
  `house` double NOT NULL,
  `medical_insurance` double NOT NULL,
  `month` int(11) NOT NULL,
  `net_salary` double NOT NULL,
  `other_taxed_amount` double NOT NULL,
  `pension` double NOT NULL,
  `status` enum('PAID','PENDING') NOT NULL,
  `transport` double NOT NULL,
  `year` int(11) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsuofq8xqv9va0ti9vp2m2mwvn` (`employee_id`,`month`,`year`),
  CONSTRAINT `FKfx972wf9gercevxt1shn3b6h9` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_slip`
--

LOCK TABLES `pay_slip` WRITE;
/*!40000 ALTER TABLE `pay_slip` DISABLE KEYS */;
INSERT INTO `pay_slip` VALUES (1,25200,84000,7000,4200,5,44520,4200,5880.000000000001,'PAID',7000,2025,7),(2,36000,120000,10000,6000,5,63600,6000,8400,'PENDING',10000,2025,8);
/*!40000 ALTER TABLE `pay_slip` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-29 18:32:45
