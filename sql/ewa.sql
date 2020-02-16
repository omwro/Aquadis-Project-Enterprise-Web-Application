-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Gegenereerd op: 16 feb 2020 om 11:20
-- Serverversie: 10.3.17-MariaDB-0+deb10u1
-- PHP-versie: 7.3.11-1~deb10u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ewa`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(88);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `map`
--

CREATE TABLE `map` (
  `id` int(11) NOT NULL,
  `keyname` varchar(255) DEFAULT NULL,
  `value` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `message`
--

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `disabled` bit(1) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `message_created` tinyblob DEFAULT NULL,
  `message_opend` tinyblob DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `message_seen_users`
--

CREATE TABLE `message_seen_users` (
  `message_id` int(11) NOT NULL,
  `seen_users_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `disabled` int(11) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT 'default',
  `score` int(11) NOT NULL DEFAULT 0,
  `status` int(11) NOT NULL DEFAULT 0,
  `user_type` varchar(255) DEFAULT NULL,
  `current_token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `map`
--
ALTER TABLE `map`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_apryb5cnmhyjnqboou75d5jv7` (`keyname`);

--
-- Indexen voor tabel `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKb3y6etti1cfougkdr0qiiemgv` (`user_id`);

--
-- Indexen voor tabel `message_seen_users`
--
ALTER TABLE `message_seen_users`
  ADD KEY `FKa64hmvcerifskppaagokg9fxx` (`seen_users_id`),
  ADD KEY `FKp5qjmqsnth605jhqytvcpa68o` (`message_id`);

--
-- Indexen voor tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
