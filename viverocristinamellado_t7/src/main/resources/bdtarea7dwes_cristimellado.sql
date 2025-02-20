-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-02-2025 a las 16:50:32
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdtarea7dwes_cristimellado`
--
CREATE DATABASE IF NOT EXISTS `bdtarea7dwes_cristimellado` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bdtarea7dwes_cristimellado`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credenciales`
--

CREATE TABLE `credenciales` (
  `id` bigint(20) NOT NULL,
  `usuario` varchar(255) DEFAULT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `credenciales`
--

INSERT INTO `credenciales` (`id`, `usuario`, `password`) VALUES
(0, 'admin', 'admin'),
(2, 'cristi', 'cristi'),
(3, 'miguel', 'miguel'),
(4, 'maria', 'maria');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ejemplares`
--

CREATE TABLE `ejemplares` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `id_planta` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ejemplares`
--

INSERT INTO `ejemplares` (`id`, `nombre`, `id_planta`) VALUES
(1, 'ROS_1', 1),
(2, 'GIR_2', 2),
(3, 'NOP_3', 3),
(4, 'ROB_4', 4),
(5, 'ROS_5', 1),
(6, 'ALB_6', 5),
(7, 'TRI_7', 6),
(8, 'TRI_8', 6),
(9, 'COC_9', 7),
(10, 'TRI_10', 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensajes`
--

CREATE TABLE `mensajes` (
  `id` bigint(20) NOT NULL,
  `fechahora` datetime(6) DEFAULT NULL,
  `mensaje` varchar(255) DEFAULT NULL,
  `id_ejemplar` bigint(20) DEFAULT NULL,
  `id_persona` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mensajes`
--

INSERT INTO `mensajes` (`id`, `fechahora`, `mensaje`, `id_ejemplar`, `id_persona`) VALUES
(1, '2025-02-10 16:39:32.000000', 'Mensaje inicial de: admin a las Mon Feb 10 16:39:32 CET 2025', 1, 0),
(2, '2025-02-10 16:39:39.000000', 'Mensaje inicial de: admin a las Mon Feb 10 16:39:39 CET 2025', 2, 0),
(3, '2025-02-10 16:39:42.000000', 'Mensaje inicial de: admin a las Mon Feb 10 16:39:42 CET 2025', 3, 0),
(4, '2025-02-10 16:39:46.000000', 'Mensaje inicial de: admin a las Mon Feb 10 16:39:46 CET 2025', 4, 0),
(5, '2025-02-10 16:40:01.000000', 'Mensaje inicial de: admin a las Mon Feb 10 16:40:01 CET 2025', 5, 0),
(6, '2025-02-10 16:40:22.000000', 'Mensaje inicial de: admin a las Mon Feb 10 16:40:22 CET 2025', 6, 0),
(7, '2025-02-10 16:40:25.000000', 'Mensaje inicial de: admin a las Mon Feb 10 16:40:25 CET 2025', 7, 0),
(8, '2025-02-10 16:40:29.000000', 'Mensaje inicial de: admin a las Mon Feb 10 16:40:29 CET 2025', 8, 0),
(9, '2025-02-10 16:40:52.000000', 'La rosa es bella', 1, 0),
(10, '2025-02-10 16:42:01.000000', 'El nopal es verde', 3, 6),
(11, '2025-02-10 16:42:51.000000', 'El trigo es muy bueno para la salud.', 7, 6),
(12, '2025-02-10 16:44:39.000000', 'El girasol se mueve con el sol', 2, 6),
(13, '2025-02-10 16:45:08.000000', 'Mensaje inicial de: cristi a las Mon Feb 10 16:45:08 CET 2025', 9, 6),
(14, '2025-02-10 16:45:49.000000', 'La albahaca se usa para cocinar', 6, 7),
(15, '2025-02-10 16:46:05.000000', 'Mensaje inicial de: Miguel a las Mon Feb 10 16:46:05 CET 2025', 10, 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`id`, `email`, `nombre`) VALUES
(0, 'admin@vivero.es', 'admin'),
(6, 'cristi@vivero.es', 'cristi'),
(7, 'miguel@hola.es', 'Miguel'),
(8, 'maria@vivero.es', 'maria');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plantas`
--

CREATE TABLE `plantas` (
  `id` bigint(20) NOT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `nombre_cientifico` varchar(255) DEFAULT NULL,
  `nombre_comun` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `plantas`
--

INSERT INTO `plantas` (`id`, `codigo`, `nombre_cientifico`, `nombre_comun`) VALUES
(1, 'ROS', 'rosa spp', 'rosa'),
(2, 'GIR', 'Helianthus annuus', 'girasol'),
(3, 'NOP', 'Opuntia', 'Nopal'),
(4, 'ROB', 'quercus spp', 'roble'),
(5, 'ALB', 'ocimum basilicum', 'albahaca'),
(6, 'TRI', 'triticum', 'trigo'),
(7, 'COC', 'cocotiux', 'coco');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKgl50fmouks2ue8s9yclvv059j` (`usuario`);

--
-- Indices de la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK44fnp4rtcs2j6ppkmkoqbtton` (`id_planta`);

--
-- Indices de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKidbx1mhngh3c3ry5bqisftxbv` (`id_ejemplar`),
  ADD KEY `FK2e6au5w562m7skcvx9jckiba6` (`id_persona`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKlrw7flsg11d8nhgyvueqtnp8e` (`email`);

--
-- Indices de la tabla `plantas`
--
ALTER TABLE `plantas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKbqo6lbeads0ifdh6dohhfhryp` (`codigo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `plantas`
--
ALTER TABLE `plantas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  ADD CONSTRAINT `FK44fnp4rtcs2j6ppkmkoqbtton` FOREIGN KEY (`id_planta`) REFERENCES `plantas` (`id`);

--
-- Filtros para la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD CONSTRAINT `FK2e6au5w562m7skcvx9jckiba6` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id`),
  ADD CONSTRAINT `FKidbx1mhngh3c3ry5bqisftxbv` FOREIGN KEY (`id_ejemplar`) REFERENCES `ejemplares` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
