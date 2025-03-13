-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3308
-- Tiempo de generación: 13-03-2025 a las 18:11:14
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
-- Base de datos: `bdtarea7_cristinamellado`
--
CREATE DATABASE IF NOT EXISTS `bdtarea7_cristinamellado` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bdtarea7_cristinamellado`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` bigint(20) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fecha_nac` datetime(6) DEFAULT NULL,
  `nifnie` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `direccion`, `email`, `fecha_nac`, `nifnie`, `nombre`, `telefono`) VALUES
(1, 'corrida 25', 'pepe@gmail.com', '1999-05-10 00:00:00.000000', '60531927p', 'pepe', '684661589'),
(2, 'cacao 18', 'mario@gmail.com', '2010-10-10 00:00:00.000000', '89748598T', 'mario', '684661583'),
(3, 'menendez 30', 'carlos@hola.es', '2010-06-14 00:00:00.000000', '10789587X', 'carlos', '987152478');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credenciales`
--

CREATE TABLE `credenciales` (
  `id` bigint(20) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `perfil` enum('ADMINISTRADOR','CLIENTE','INVITADO','PERSONAL') DEFAULT NULL,
  `usuario` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `credenciales`
--

INSERT INTO `credenciales` (`id`, `password`, `perfil`, `usuario`) VALUES
(0, '$2a$10$39moihGn9HT0bXVOUzbpZer/3bipWNgQej1hFokL8AVKreeMPErq.', 'ADMINISTRADOR', 'admin'),
(2, '$2a$10$kl86E8DSe1M774OKZD3H8enIqEPKK/KpmHgVoO9lEEeMjJEoMVCGC', 'PERSONAL', 'cristi'),
(3, '$2a$10$MSDCRTfR5q5zSHhRyeCufOITT1JKgENcnEx8f30g29zQpxoWb68by', 'PERSONAL', 'juan'),
(4, '$2a$10$iYJhZj/w9V9DrxFSaWprvu8nRFmY.DKAxzaOroUNJPWBmIpFUYFcC', 'PERSONAL', 'seles'),
(5, '$2a$10$icGFxmLNrmOFfqYiOXfiZOZBdbYao4NHehxR91d0Y9BEKjss3O5lS', 'CLIENTE', 'pepe'),
(6, '$2a$10$Xfh8/F6sXgDq5MEpCR4usuWfW5MnyVgqv8sMnnfbJV3WAWmMbwzye', 'CLIENTE', 'mario'),
(7, '$2a$10$GhyOXTszMfTyAHG.6sEV0uZFF4oZ/rJR/OAA2jpTnHFNB5vkEHQwa', 'CLIENTE', 'carlos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ejemplares`
--

CREATE TABLE `ejemplares` (
  `id` bigint(20) NOT NULL,
  `disponible` bit(1) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `id_pedido` bigint(20) DEFAULT NULL,
  `id_planta` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ejemplares`
--

INSERT INTO `ejemplares` (`id`, `disponible`, `nombre`, `id_pedido`, `id_planta`) VALUES
(1, b'0', 'ALO_1', 1, 1),
(2, b'1', 'GIR_2', NULL, 3),
(3, b'1', 'ROB_3', NULL, 5),
(4, b'1', 'ALO_4', NULL, 1),
(5, b'0', 'ROS_5', 1, 2),
(6, b'1', 'COC_6', NULL, 7),
(7, b'1', 'COC_7', NULL, 7),
(8, b'0', 'TRI_8', 2, 6),
(9, b'1', 'TRI_9', NULL, 6),
(10, b'1', 'NOP_10', NULL, 4),
(11, b'1', 'ROS_11', NULL, 2);

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
(1, '2025-03-13 17:47:07.000000', 'Mensaje inicial de: cristi a las Thu Mar 13 17:47:07 CET 2025', 1, 2),
(2, '2025-03-13 17:47:10.000000', 'Mensaje inicial de: cristi a las Thu Mar 13 17:47:10 CET 2025', 2, 2),
(3, '2025-03-13 17:47:13.000000', 'Mensaje inicial de: cristi a las Thu Mar 13 17:47:13 CET 2025', 3, 2),
(4, '2025-03-13 17:47:16.000000', 'Mensaje inicial de: cristi a las Thu Mar 13 17:47:16 CET 2025', 4, 2),
(5, '2025-03-13 17:47:19.000000', 'Mensaje inicial de: cristi a las Thu Mar 13 17:47:19 CET 2025', 5, 2),
(6, '2025-03-13 17:47:35.000000', 'Mensaje inicial de: cristi a las Thu Mar 13 17:47:35 CET 2025', 6, 2),
(7, '2025-03-13 17:47:38.000000', 'Mensaje inicial de: cristi a las Thu Mar 13 17:47:38 CET 2025', 7, 2),
(8, '2025-03-13 17:48:04.000000', 'Mensaje inicial de: admin a las Thu Mar 13 17:48:04 CET 2025', 8, 0),
(9, '2025-03-13 17:48:07.000000', 'Mensaje inicial de: admin a las Thu Mar 13 17:48:07 CET 2025', 9, 0),
(10, '2025-03-13 17:48:29.000000', 'El aloe vera huele rico', 1, 0),
(11, '2025-03-13 17:48:35.000000', 'La rosa es roja', 5, 0),
(12, '2025-03-13 17:49:48.000000', 'El trigo esta caro', 8, 4),
(13, '2025-03-13 17:50:14.000000', 'insertando mensaje de coco', 6, 4),
(14, '2025-03-13 17:51:00.000000', 'probando mensaje', 3, 4),
(15, '2025-03-13 17:51:06.000000', 'probando mensaje', 3, 4),
(16, '2025-03-13 17:52:22.000000', 'prueba 3', 1, 4),
(17, '2025-03-13 17:55:31.000000', 'aloe vera', 1, 4),
(18, '2025-03-13 18:04:44.000000', 'el coco loco', 6, 4),
(19, '2025-03-13 18:06:21.000000', 'rosa rosa juan juan\r\n', 5, 3),
(20, '2025-03-13 18:07:11.000000', 'El cliente pepe compró el ejemplar ALO_1 el día 13-03-2025 18:07 en el pedido 1', 1, NULL),
(21, '2025-03-13 18:07:11.000000', 'El cliente pepe compró el ejemplar ROS_5 el día 13-03-2025 18:07 en el pedido 1', 5, NULL),
(22, '2025-03-13 18:08:01.000000', 'Mensaje inicial de: juan a las Thu Mar 13 18:08:01 CET 2025', 10, 3),
(23, '2025-03-13 18:08:36.000000', 'Mensaje inicial de: admin a las Thu Mar 13 18:08:36 CET 2025', 11, 0),
(24, '2025-03-13 18:10:30.000000', 'El cliente mario compró el ejemplar TRI_8 el día 13-03-2025 18:10 en el pedido 2', 8, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id` bigint(20) NOT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `id_cliente` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`id`, `fecha`, `id_cliente`) VALUES
(1, '2025-03-13 18:07:11.000000', 1),
(2, '2025-03-13 18:10:30.000000', 2);

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
(2, 'cristi@vivero.es', 'cristi'),
(3, 'juan@vivero.es', 'juan'),
(4, 'seles@vivero.es', 'seles');

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
(1, 'ALO', 'aloe ', 'Aloe Vera'),
(2, 'ROS', 'rosa spp', 'rosa'),
(3, 'GIR', 'giralito', 'girasol'),
(4, 'NOP', 'Opuntia', 'Nopal'),
(5, 'ROB', 'quercus spp', 'roble'),
(6, 'TRI', 'triticum', 'trigo'),
(7, 'COC', 'cocotiux', 'coco');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK9vhcgykl5io0b4neokltjgth4` (`nifnie`);

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
  ADD KEY `FKirjys0atrrrx9a1vc0pxcg2pf` (`id_pedido`),
  ADD KEY `FK44fnp4rtcs2j6ppkmkoqbtton` (`id_planta`);

--
-- Indices de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKidbx1mhngh3c3ry5bqisftxbv` (`id_ejemplar`),
  ADD KEY `FK2e6au5w562m7skcvx9jckiba6` (`id_persona`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdnomiluem4t3x66t6b9aher47` (`id_cliente`);

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
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

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
  ADD CONSTRAINT `FK44fnp4rtcs2j6ppkmkoqbtton` FOREIGN KEY (`id_planta`) REFERENCES `plantas` (`id`),
  ADD CONSTRAINT `FKirjys0atrrrx9a1vc0pxcg2pf` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id`);

--
-- Filtros para la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD CONSTRAINT `FK2e6au5w562m7skcvx9jckiba6` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id`),
  ADD CONSTRAINT `FKidbx1mhngh3c3ry5bqisftxbv` FOREIGN KEY (`id_ejemplar`) REFERENCES `ejemplares` (`id`);

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `FKdnomiluem4t3x66t6b9aher47` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
