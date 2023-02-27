-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: ktx
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
-- Table structure for table `sinhvien`
--

DROP TABLE IF EXISTS `sinhvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sinhvien` (
  `sinhVien_ID` varchar(8) NOT NULL,
  `sinhVien_Name` varchar(40) DEFAULT NULL,
  `sinhVien_Mail` varchar(255) DEFAULT NULL,
  `sinhVien_Sdt` varchar(11) DEFAULT NULL,
  `sinhVien_Username` varchar(20) DEFAULT NULL,
  `sinhVien_Password` varchar(20) DEFAULT NULL,
  `room_ID` varchar(5) DEFAULT NULL,
  `Ngaythue` date DEFAULT NULL,
  PRIMARY KEY (`sinhVien_ID`),
  KEY `fk_room_ID` (`room_ID`),
  CONSTRAINT `fk_room_ID` FOREIGN KEY (`room_ID`) REFERENCES `room` (`room_ID`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sinhvien`
--

LOCK TABLES `sinhvien` WRITE;
/*!40000 ALTER TABLE `sinhvien` DISABLE KEYS */;
INSERT INTO `sinhvien` VALUES ('1','1','1','1','1234','1234','R5','2023-02-27'),('123','Nguyen Van A','Mail@gmail.com','123123123','123','123','R1','2023-02-26'),('22IT034','Nguyen Van B','A@gmail.com','123123123','test_01','test_01','R1','2023-01-15'),('22IT035','Nguyen C','C@gmail.com','CCCCCCCCC','test_02','test_02',NULL,NULL),('22IT036','Nguyen Van D','D@gmail.com','BBBBBBBBBB','test_03','test_03',NULL,NULL),('22IT11','Nguyễn Hồng Nguyên Hải','Nhailtvop@gmail.com','0777543918','test','test','R2','2023-01-15');
/*!40000 ALTER TABLE `sinhvien` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-27 20:21:45
