-- MySQL dump 10.13  Distrib 8.0.30, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: test
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `id` bigint NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1t68827l97cwyxo9r1u6t4p7d` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (3,'microsoft soft. comp.','Software'),(15,'High lvl tech','Electronics'),(115,'finance department','monopoly');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` bigint NOT NULL,
  `email` varchar(50) NOT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fopic1oh5oln2khj8eat6ino0` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (13,'js@mail.com','Sparrow','Jack'),(67,'h10@mail','hagi','George'),(69,'admin@mail','Admin','Great'),(71,'hr@mail','none','MrHR'),(114,'md@Mail.com','mr','MrDepot');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_role`
--

DROP TABLE IF EXISTS `employee_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo7rvk7ejtx29vru9cyhf7o68a` (`employee_id`),
  CONSTRAINT `FKo7rvk7ejtx29vru9cyhf7o68a` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_role`
--

LOCK TABLES `employee_role` WRITE;
/*!40000 ALTER TABLE `employee_role` DISABLE KEYS */;
INSERT INTO `employee_role` VALUES (1,'DEPOT_WORKER',NULL),(8,'DEPOT_WORKER',NULL),(9,'ADMIN',NULL),(10,'ADMIN',NULL),(11,'HR',NULL),(14,'ADMIN',NULL),(15,'HR',NULL),(16,'HR',NULL),(17,'ADMIN',NULL),(18,'DEPOT_WORKER',NULL),(19,'ADMIN',NULL),(20,'DEPOT_WORKER',NULL),(21,'HR',NULL),(22,'ADMIN',NULL),(23,'DEPOT_WORKER',NULL),(24,'ADMIN',NULL),(25,'DEPOT_WORKER',NULL),(26,'HR',NULL),(27,'ADMIN',NULL),(28,'DEPOT_WORKER',NULL),(29,'HR',NULL),(30,'NONE',NULL),(32,'ADMIN',NULL),(33,'ADMIN',NULL),(34,'DEPOT_WORKER',NULL),(35,'HR',NULL),(36,'HR',NULL),(37,'DEPOT_WORKER',NULL),(38,'ADMIN',NULL),(39,'DEPOT_WORKER',NULL),(40,'HR',NULL),(41,'DEPOT_WORKER',NULL),(42,'ADMIN',NULL),(43,'DEPOT_WORKER',NULL),(44,'HR',NULL),(45,'ADMIN',NULL),(46,'DEPOT_WORKER',NULL),(47,'HR',NULL),(48,'DEPOT_WORKER',NULL),(49,'HR',NULL),(50,'ADMIN',NULL),(51,'DEPOT_WORKER',NULL),(52,'HR',NULL),(53,'ADMIN',NULL),(54,'DEPOT_WORKER',NULL),(55,'DEPOT_WORKER',NULL),(56,'ADMIN',NULL),(57,'DEPOT_WORKER',NULL),(58,'ADMIN',NULL),(59,'DEPOT_WORKER',NULL),(60,'ADMIN',NULL),(61,'HR',NULL),(62,'HR',13),(63,'DEPOT_WORKER',13),(64,'ADMIN',13),(65,'ADMIN',NULL),(67,'DEPOT_WORKER',67),(68,'HR',NULL),(69,'HR',NULL),(70,'ADMIN',69),(71,'NONE',NULL),(72,'DEPOT_WORKER',114),(73,'ADMIN',NULL),(74,'HR',NULL),(75,'HR',NULL),(76,'HR',NULL),(77,'DEPOT_WORKER',NULL),(78,'HR',NULL),(79,'ADMIN',NULL),(80,'HR',NULL),(81,'HR',71);
/*!40000 ALTER TABLE `employee_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (193);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `id` bigint NOT NULL,
  `amount` int DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `location_name` varchar(255) DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (177,250,'2019-01-12 20:11:00.455317','canakkale',16,'Watermelon'),(178,111,'2023-01-12 20:11:27.335345','amsterdam',16,'Watermelon'),(186,255,'2023-01-12 21:52:25.059047','canakkale',179,'Iphone'),(187,777,'2016-01-12 21:52:27.748226','canakkale',179,'Iphone'),(188,645,'2023-01-12 21:52:31.489039','amsterdam',179,'Iphone'),(189,115,'2023-01-12 21:52:34.413489','amsterdam',179,'Iphone');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` bigint NOT NULL,
  `address` varchar(30) NOT NULL,
  `max_pallet` int DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ltij63d2oq8xj94dgd3rkyuuh` (`address`),
  UNIQUE KEY `UK_sahixf1v7f7xns19cbg12d946` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (4,'south of the city',998,'canakkale'),(41,'amsterdam Centrall',888,'amsterdam');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pallet`
--

DROP TABLE IF EXISTS `pallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pallet` (
  `id` bigint NOT NULL,
  `amount` int DEFAULT NULL,
  `location_id` bigint NOT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfcvl91rqdkllo61qiij0tadvw` (`location_id`),
  KEY `FK18s161e81dlou4ieo6v1gfo65` (`product_id`),
  CONSTRAINT `FK18s161e81dlou4ieo6v1gfo65` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKfcvl91rqdkllo61qiij0tadvw` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pallet`
--

LOCK TABLES `pallet` WRITE;
/*!40000 ALTER TABLE `pallet` DISABLE KEYS */;
INSERT INTO `pallet` VALUES (14,211,4,9),(100,55,41,9),(108,222,41,16);
/*!40000 ALTER TABLE `pallet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `height` double DEFAULT NULL,
  `length` double DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `width` double DEFAULT NULL,
  `department_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8vqjj32x97p0xtnbe504aku1c` (`department_id`),
  CONSTRAINT `FK8vqjj32x97p0xtnbe504aku1c` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (9,'vitamin abc',5,4,'mandalina',20,3,15),(16,'fruit',4,3,'Watermelon',5,2,15),(179,'Pro max',15,75,'Iphone',100,75,15),(180,'s22',20,70,'Samsung Galaxy',100,70,15),(181,'Good cam',30,50,'Google Pixel',88,50,15);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(30) NOT NULL,
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lqjrcobrh9jc8wpcar64q1bfh` (`user_name`),
  KEY `FK211dk0pe7l3aibwce8yy61ota` (`employee_id`),
  CONSTRAINT `FK211dk0pe7l3aibwce8yy61ota` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (12,'$2a$10$GCo5DPk4CoTACrTe9qcWg.E2cHVFB.72qvwz9i.xJqLG5ZjIVXs1m','Captain',13),(66,'$2a$10$DpOgwXKI8RnoTrtcMD51gOEFsMuvK75rRqCMk56xPB/cBXnDBWqXG','H10',67),(68,'$2a$10$GjuoGNqsBXbyOqdfdaXuH.AHORyKArwNE7IQsMj8BSMEbmYDe33cm','GA',69),(70,'$2a$10$RPtTwlIlaM0PEeQHNVfVu.b.aOcMCZIciyNIJxy7MOjShESa14uTm','Hr',71),(113,'$2a$10$ZAugqyyJTBwA/kKYl66NX.A8bX4DxxHMAn0fWTTw9SZ2PML5s4wTe','depot',114);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-13 19:07:31
