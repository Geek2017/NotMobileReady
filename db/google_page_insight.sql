-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 25, 2017 at 07:08 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `google_page_insight`
--

-- --------------------------------------------------------

--
-- Table structure for table `urllist`
--

CREATE TABLE `urllist` (
  `id` int(10) NOT NULL,
  `url` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `urlresult`
--

CREATE TABLE `urlresult` (
  `id` int(10) NOT NULL,
  `urllist_id` int(11) NOT NULL,
  `site_name` varchar(1000) NOT NULL,
  `site_url` varchar(1000) NOT NULL,
  `speed_score` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `mobile_freindly_score` varchar(255) NOT NULL,
  `time_stamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `urllist`
--
ALTER TABLE `urllist`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `urlresult`
--
ALTER TABLE `urlresult`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `urllist`
--
ALTER TABLE `urllist`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `urlresult`
--
ALTER TABLE `urlresult`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
