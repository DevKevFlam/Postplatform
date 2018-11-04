-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: postplatform
-- ------------------------------------------------------
-- Server version	5.5.5-10.3.7-MariaDB

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
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contenu` text DEFAULT NULL,
  `date` bigint(20) NOT NULL,
  `love_its` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2jm25hjrq6iv4w8y1dhi0d9p4` (`title`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'Dans un ultime rebondissement, la vérité éclate enfin. « Je ne comprends pas: personne ne s’en est rendu compte jusqu’à maintenant. » a-t-il déclaré penaud à son avocat, « même mes plus fidèles fanzouzes n’ont rien vu. Je suis meurtri. » C’est un homme abattu et épuisé qui se confesse « Je bosse 20h par jour pour divertir mon public dépressif et voilà comment on me remercie pour quelques coups de matraques. Non vraiment je ne m’attendais pas à un tel acharnement. » Baba fond en larme, lui qui assure ne pas jouer un rôle en expliquant que ces multiples petits boulots étaient pour aider son ami Greg Guillotin. « Greg m’a demandé de l’aide pour ses caméras cachées alors j’ai dit oui. » Usant de ses relations, suite à sa rencontre avec Manu lors d’un prime de son émission phare TPMP, il a pu infiltrer divers services de l’état français pendant quelques mois. Et lorsqu’on l’interpelle sur le débordement du 1er mai il répond sans sommation « Vous ne me parlez que de cette affaire du 1er mai et jamais des autres. Oui j’ai gentiment tapé 2 personnes…mais elles m’avaient mis des dislikes sur BooBook ! Et ça c’est intolérable car Baba n’est qu’amour. » Suite à cet aveu, une nouvelle audition au sénat est prévu afin d’éclaircir les zones d’ombres. Pour le moment seul Arthur a saisi le CSA pour présence trop longue à l’antenne de l’animateur.',1537961234380,0,'L’aveu de Benalla: « Cyril Hanouna c’est moi.','https://nordpresse.be/laveu-de-benalla-cyril-hanouna-cest-moi/',1),(2,'Nouveau rebondissement dans l’affaire Benalla, il se serait rendu ce matin au bureau de la PJ du 36 quai des orfèvres pour se libérer la conscience: « Manu et moi ça fait longtemps que l’on se fréquente, mais il ne veut pas que celà se sache. Je l’aime en secret car Monsieur ne veut pas que Brigitte l’apprenne… Il m’a rapproché de lui professionnellement mais ne s’est jamais séparé de sa femme comme il me l’a promis. Aujourd’hui, c’est allé trop loin, j’ai décidé de me venger! » \\n\\nVoici l’explication que donne à présent le jeune homme dans ce dossier plein de zones d’ombres. Suffira-t-elle à élucider le mystère de cette garde (trés) rapprochée qui entoure Manu française? Brigitte Macron ne s’est pas prononcée suite à cette révélation explosive que Médiapart a savamment laissé fuiter dans la matinée. Affaire à suivre',1537961185786,0,'Aveux de Benalla: « J’étais l’amant du président mais c’était notre secret','https://nordpresse.be/aveux-de-benalla-jetais-lamant-president-cetait-secret/',2),(3,'z',1537961185786,0,'z',NULL,2);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-05 10:47:11
