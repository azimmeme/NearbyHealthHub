-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 02, 2024 at 10:24 AM
-- Server version: 5.7.24
-- PHP Version: 7.2.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mymap`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `full_name`, `password`) VALUES
(1, 'ali', 'ali', '$2y$10$evWhTC.a/nzp1H70QUvlguGqCuq/uPlRHCi8TrZxQ5DAWDg8OjwVS'),
(2, 'mat', 'mat', '$2y$10$Vt9O5c8ibd9ZK56rsPPuHOw6bu90SpYfZNL3txHRDPJdv2s2Dgaei');

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

CREATE TABLE `locations` (
  `id` int(11) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `address` varchar(255) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_agent` varchar(255) NOT NULL DEFAULT '',
  `datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `locations`
--

INSERT INTO `locations` (`id`, `latitude`, `longitude`, `address`, `fullname`, `created_at`, `user_agent`, `datetime`) VALUES
(6, 6.428263, 100.21925, 'c22f, Jalan Padang Behor, 01000 Kangar, Perlis, Malaysia', 'Muhammad Haikal', '2024-02-01 07:26:29', 'Dalvik/2.1.0 (Linux; U; Android 13; sdk_gphone64_x86_64 Build/TE1A.220922.010)', '2024-02-01 07:26:29'),
(9, 6.449303, 100.230045, 'no 1, Lorong Seri Mawar, Kampung Jejawi, 01000 Kangar, Perlis, Malaysia', 'ali', '2024-02-02 09:23:35', 'Dalvik/2.1.0 (Linux; U; Android 14; sdk_gphone64_x86_64 Build/UE1A.230829.019)', '2024-02-02 01:23:34'),
(10, 6.449303, 100.230045, 'no 1, Lorong Seri Mawar, Kampung Jejawi, 01000 Kangar, Perlis, Malaysia', 'mat', '2024-02-02 09:44:31', 'Dalvik/2.1.0 (Linux; U; Android 14; sdk_gphone64_x86_64 Build/UE1A.230829.019)', '2024-02-02 01:44:30'),
(11, 6.449303, 100.230045, 'no 1, Lorong Seri Mawar, Kampung Jejawi, 01000 Kangar, Perlis, Malaysia', 'azim hensem', '2024-02-02 09:45:31', 'Dalvik/2.1.0 (Linux; U; Android 14; sdk_gphone64_x86_64 Build/UE1A.230829.019)', '2024-02-02 01:45:30');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `full_name`, `username`, `password`) VALUES
(1, 'Afiq Asyraf', 'afqasy', '$2y$10$izs8Ifw/pHXFsrG9nhjOO.D2Z3ldIoBxtZv..ztLQpkPbMysN71tO'),
(4, 'Afrinaalyaani', 'Afrina', '$2y$10$TkUa4aG7.YT3OTxcjiK5uO4WTg5Mp6AYDGE4oR/9ymKCi.9o9aahy'),
(10, 'Abu', 'abu', '$2y$10$Dxrm0sd3EbgzO.UpFNfDGe2xeMCExWeIbR6OaE6BG8zPqCmwPCYmW'),
(18, 'Hasan', 'Hasan', '$2y$10$/IRcWCI3FXde4HBlse225OTf261z0VKj9NYPw7ATIZZ6CvnFtnoXK'),
(19, 'Johaei', 'Johari', '$2y$10$eddMcMftanrA0FMdOGmWNureMCq.plwP4LrMwLGBI.4e6xtH3ATmO'),
(20, 'AFiq Hensem', 'asdjn', '$2y$10$Q8rC4la06hqRBh.rFTS2Z.Om/XUTHkUHYxrO2.spSNAmsNqr5w6Ca'),
(24, 'Amin', 'Amin', '$2y$10$V16DzUVTpm1Uc7BzK.pxKeLkBjYg83Igl6sqBTs3Lnx./raquCnze'),
(25, 'Haikal', 'haikal', '$2y$10$Jzs7JK7ijH6PKugWbEHDmOq1vG73ShdC79nVk2.MSkU4jWkoaQTVy'),
(26, 'Haikal Arifiin', 'haikalium', '$2y$10$AzvCnByXnW1lr4R4dO3souBlWnQCRL496d7ew8JjBnOAL0tUY0GJm'),
(29, 'lili', 'lili', '$2y$10$69DYqUMSqJBUdIEZTwQGCe3nx25toyT1XVbGUXmcX/MjTO.AzqsS.'),
(30, 'pipi', 'pipi', '$2y$10$rtuWe/wr1gxjlGVR9sbEzOps3Ek918AEvsAwI2EWLhMxsx4WZqPpC'),
(31, 'juju', 'juju', '$2y$10$z/h3QwYFdS9qd3W0gQcUiubjPs4T1vF0Tg0gT9NAan1Zg9WoNY6ga'),
(32, 'aina', 'aina', '$2y$10$1TqxhuRWBoggHgBA4Crituc2dEYETjiBoSEtHWogs.vpX22bYS2EO'),
(33, 'pupu', 'pupu', '$2y$10$HQMm5WqNF2EdK2FdeXIzf.mq7F1MwXhcqGHEvbuWWISCCIbIihQDq'),
(34, 'popo', 'popo', '$2y$10$3ExJl3hLef0NuTvBF4SJgu38IRoMZknkVHDHKMpFEtTyYOM/B8wXS'),
(35, 'gajah', 'gajah', '$2y$10$7E9hyIcnDZB4gzTQceoWa.3V9lZ0FbMr4IVEI1p3zQ04YiF.ZjiRC'),
(37, 'kancil', 'kancil', '$2y$10$MWlmIW/sECWaJGyLCQyCjO8hDzDyG.IBbj8eEjZU6pBZ9S2NPRax6'),
(38, 'meow', 'meow', '$2y$10$BiVwmmxmdPcEysdjMNEtmeyUdExpraOfHvwdA7Mo7gVSr9rPwFF9u'),
(39, 'burung', 'burung', '$2y$10$hUw0ztlBipByPwZ1teX1I.ERDdOZjnTChWCC36Ms7kLU14Fq2kY9q'),
(40, 'Muhammad Haikal', 'mumu', '$2y$10$BBOU7M9PLCbe9EosBHslDuLTdmfQuJCAVT8.ijjub804rHehqNUEu'),
(41, 'Haniey Haniey', 'hani', '$2y$10$NzIbWF3POn9L7qTjX/1cnu/MqN280HsTezGIJHfif9hB3fJg8t5.e'),
(42, 'afrinaalyaani', 'pina', '$2y$10$lO3kjFU./pTTSzrfGGiZ.OdJD3Kdv3JMZ7tl9907y6S8rmo6wpuH.'),
(43, 'afiq asyraf', 'asyraf', '$2y$10$rwfMhpAayxzuBYUQeDYRueJojR2Kk.0pnCXmui50eLY3TRY.P4W2K'),
(44, 'ali', 'ali', '$2y$10$iAxIbvkBjG9nyyyq94xuMOtEkjbju19H4tfhUsffA04OSoHXyOb4m'),
(45, 'YAB Laksamana Tun Tan Sri Dato\' Dr. Ts. Ir. Madya Azim', 'azimmeme', '$2y$10$R0LWOIBg1sknhjb1HPD8UOtS8ov8Ez9hubvfBhJfh/vTVNWrZb9w6'),
(46, 'mat', 'mat', '$2y$10$kP6ZPoSeXtsMkwBKkoCIgeXVYtJd9pPCb8tMSJwLsa.RTd4VdK3v.'),
(47, 'azim hensem', 'zzz', '$2y$10$M2nbmx8ZRoehe8xPb.k/BO5iyWmhYJIehe4Pvq6ZXz4yGFXp1l4ey');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `locations`
--
ALTER TABLE `locations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `locations`
--
ALTER TABLE `locations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
