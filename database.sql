-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: ration_supply
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `card_table`
--

DROP TABLE IF EXISTS `card_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `card_table` (
  `card_no` int NOT NULL,
  `holder_name` varchar(20) NOT NULL,
  `address` varchar(80) NOT NULL,
  `members` int NOT NULL,
  `last_received` date DEFAULT NULL,
  PRIMARY KEY (`card_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card_table`
--

LOCK TABLES `card_table` WRITE;
/*!40000 ALTER TABLE `card_table` DISABLE KEYS */;
INSERT INTO `card_table` VALUES (11546851,'Pawan Verma','Ward No. 16, New Bus Stand, Chirawa, Rajasthan',4,'2021-07-11'),(14468841,'Makhan Singh','Ward No. 25, kamal mandi, Chirawa, Rajasthan',3,'2021-07-10'),(14894441,'Gopal Sharma','Ward No. 12, Naya Bazaar, Chirawa, Rajasthan',8,'2021-07-05'),(14894561,'Veer Ram','Ward No. 16, Lohia School, Chirawa, Rajasthan',5,'2021-07-24'),(15478369,'Sukhdev Kumar','Ward no. 29, Purani Basti, Chirawa, Rajasthan',1,'2022-07-16');
/*!40000 ALTER TABLE `card_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dealer_info`
--

DROP TABLE IF EXISTS `dealer_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dealer_info` (
  `dealer_ID` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `age` int DEFAULT NULL,
  PRIMARY KEY (`dealer_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dealer_info`
--

LOCK TABLES `dealer_info` WRITE;
/*!40000 ALTER TABLE `dealer_info` DISABLE KEYS */;
INSERT INTO `dealer_info` VALUES ('#16g56f45ds55','ramavtar','4d5f6sdfew56',56),('#45h54jy5','kishan','4w6fe84f38ef',45),('#root','master','pass',0);
/*!40000 ALTER TABLE `dealer_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_table`
--

DROP TABLE IF EXISTS `person_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person_table` (
  `card_no` int NOT NULL,
  `name` varchar(20) NOT NULL,
  `aadhar_no` int NOT NULL,
  `verification_id` varchar(12) NOT NULL,
  `age` int NOT NULL,
  PRIMARY KEY (`aadhar_no`),
  KEY `card_no` (`card_no`),
  CONSTRAINT `person_table_ibfk_1` FOREIGN KEY (`card_no`) REFERENCES `card_table` (`card_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_table`
--

LOCK TABLES `person_table` WRITE;
/*!40000 ALTER TABLE `person_table` DISABLE KEYS */;
INSERT INTO `person_table` VALUES (15478369,'Sukhdev Kumar',159753456,'5#shk5f21de6',23),(11546851,'Bharati Verma',248484656,'46#67$86rb-b',66),(11546851,'Pawan Verma',348434684,'56$84=sn#s6n',34),(11546851,'Pankaj Verma',348454654,'46#67$86rb-b',38),(14468841,'Manisha Singh',455635946,'56#fkbjb6d',46),(14468841,'Maan Singh',456324546,'56#bsfb6+',20),(14468841,'Makhan Singh',456444656,'#kj/2+9#+',50),(11546851,'Ashok Verma',546454465,'45f#7$rwfe-b',70),(11546851,'Dayaram Verma',661866465,'52*-/2+9#+',100);
/*!40000 ALTER TABLE `person_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-21 21:56:53
