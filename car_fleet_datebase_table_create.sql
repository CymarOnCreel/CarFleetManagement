SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `carfleet_manager` DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci;
USE `carfleet_manager`;

CREATE TABLE `car` (
  `license_plate` varchar(20) COLLATE utf8_hungarian_ci NOT NULL,
  `make` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `model` varchar(45) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `category` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `fuel` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `doors` int(11) NOT NULL,
  `seats` int(11) NOT NULL,
  `transmission_type` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `mileage` int(11) NOT NULL,
  `service_interval` int(11) NOT NULL,
  `inspection_expiry_date` date NOT NULL,
  `site_name` varchar(45) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT 1,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `car_picture` (
  `picture_path` varchar(200) COLLATE utf8_hungarian_ci NOT NULL,
  `license_plate` varchar(20) COLLATE utf8_hungarian_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `category` (
  `name_category` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `picture_path_category` varchar(45) COLLATE utf8_hungarian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `employee` (
  `id_employee` int(11) NOT NULL,
  `last_name` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `first_name` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `email` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `password` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `driver_license` varchar(45) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `role_name` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `fuel` (
  `fuel_type` varchar(45) COLLATE utf8_hungarian_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `insurance` (
  `id_insurance` int(11) NOT NULL,
  `license_plate` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `insurance_type` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `insurer_name` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `price` int(11) DEFAULT NULL,
  `expire_date` date NOT NULL,
  `pay_period` varchar(45) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `insurance_type` (
  `name_insurance_type` varchar(45) COLLATE utf8_hungarian_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `maintenance` (
  `id_maintenance` int(11) NOT NULL,
  `license_plate` varchar(20) COLLATE utf8_hungarian_ci NOT NULL,
  `maintenance_type` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `service_company` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `date` date NOT NULL,
  `mileage` int(11) NOT NULL,
  `description` varchar(500) COLLATE utf8_hungarian_ci NOT NULL,
  `amount` int(11) DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `maintenance_type` (
  `name_maintenance_type` varchar(45) COLLATE utf8_hungarian_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `make` (
  `name_make` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `picture_path_make` varchar(200) COLLATE utf8_hungarian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `model` (
  `name_model` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `make` varchar(45) COLLATE utf8_hungarian_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `reservation` (
  `id_reservation` int(11) NOT NULL,
  `id_employee` int(11) NOT NULL,
  `license_plate` varchar(20) COLLATE utf8_hungarian_ci NOT NULL,
  `start_date_time` datetime NOT NULL,
  `end_date_time` datetime NOT NULL,
  `description` varchar(100) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `role` (
  `name_role` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `description` varchar(100) COLLATE utf8_hungarian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `service_company` (
  `name_service_company` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `location` varchar(45) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `contact_person` varchar(45) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `contact_email` varchar(45) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `contact_phone` varchar(20) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `site` (
  `name_site` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `location` varchar(45) COLLATE utf8_hungarian_ci NOT NULL,
  `capacity` int(11) NOT NULL,
  `contact_person` varchar(45) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `contact_email` varchar(45) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `contact_phone` varchar(45) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `description` varchar(45) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `created_at` date NOT NULL,
  `updated_at` date DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

CREATE TABLE `transmission` (
  `transmission_type` varchar(45) COLLATE utf8_hungarian_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;


ALTER TABLE `car`
  ADD PRIMARY KEY (`license_plate`),
  ADD UNIQUE KEY `license_plate_UNIQUE` (`license_plate`),
  ADD KEY `transmission_idx` (`transmission_type`),
  ADD KEY `make_idx` (`make`),
  ADD KEY `category_idx` (`category`),
  ADD KEY `engine_idx` (`fuel`),
  ADD KEY `model` (`model`);

ALTER TABLE `car_picture`
  ADD PRIMARY KEY (`picture_path`),
  ADD UNIQUE KEY `picture_path_UNIQUE` (`picture_path`),
  ADD KEY `pic-car` (`license_plate`);

ALTER TABLE `category`
  ADD PRIMARY KEY (`name_category`),
  ADD UNIQUE KEY `name_category_UNIQUE` (`name_category`);

ALTER TABLE `employee`
  ADD PRIMARY KEY (`id_employee`),
  ADD UNIQUE KEY `id_employee_UNIQUE` (`id_employee`),
  ADD KEY `role_idx` (`role_name`);

ALTER TABLE `fuel`
  ADD PRIMARY KEY (`fuel_type`),
  ADD UNIQUE KEY `engine_type_UNIQUE` (`fuel_type`);

ALTER TABLE `insurance`
  ADD PRIMARY KEY (`id_insurance`),
  ADD UNIQUE KEY `id_insurance_UNIQUE` (`id_insurance`),
  ADD KEY `insurance_type_idx` (`insurance_type`),
  ADD KEY `license_plate_idx` (`license_plate`);

ALTER TABLE `insurance_type`
  ADD PRIMARY KEY (`name_insurance_type`),
  ADD UNIQUE KEY `name_insurance_type_UNIQUE` (`name_insurance_type`);

ALTER TABLE `maintenance`
  ADD PRIMARY KEY (`id_maintenance`),
  ADD UNIQUE KEY `id_maintenance_UNIQUE` (`id_maintenance`),
  ADD KEY `car-main` (`license_plate`),
  ADD KEY `main-type` (`maintenance_type`),
  ADD KEY `main-service` (`service_company`);

ALTER TABLE `maintenance_type`
  ADD PRIMARY KEY (`name_maintenance_type`),
  ADD UNIQUE KEY `name_maintenance_type_UNIQUE` (`name_maintenance_type`);

ALTER TABLE `make`
  ADD PRIMARY KEY (`name_make`),
  ADD UNIQUE KEY `make_name_UNIQUE` (`name_make`);

ALTER TABLE `model`
  ADD PRIMARY KEY (`name_model`),
  ADD UNIQUE KEY `name_model_UNIQUE` (`name_model`),
  ADD KEY `make-model` (`make`);

ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id_reservation`),
  ADD UNIQUE KEY `id_reservation_UNIQUE` (`id_reservation`),
  ADD KEY `id_employee_id` (`id_employee`),
  ADD KEY `car-reservation_idx` (`license_plate`);

ALTER TABLE `role`
  ADD PRIMARY KEY (`name_role`),
  ADD UNIQUE KEY `name_role_UNIQUE` (`name_role`);

ALTER TABLE `service_company`
  ADD PRIMARY KEY (`name_service_company`),
  ADD UNIQUE KEY `id_service_UNIQUE` (`name_service_company`);

ALTER TABLE `site`
  ADD PRIMARY KEY (`name_site`),
  ADD UNIQUE KEY `name_site_UNIQUE` (`name_site`);

ALTER TABLE `transmission`
  ADD PRIMARY KEY (`transmission_type`),
  ADD UNIQUE KEY `transmission_type_UNIQUE` (`transmission_type`);


ALTER TABLE `employee`
  MODIFY `id_employee` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `insurance`
  MODIFY `id_insurance` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `maintenance`
  MODIFY `id_maintenance` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `reservation`
  MODIFY `id_reservation` int(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `car`
  ADD CONSTRAINT `category` FOREIGN KEY (`category`) REFERENCES `category` (`name_category`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fuel` FOREIGN KEY (`fuel`) REFERENCES `fuel` (`fuel_type`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `make` FOREIGN KEY (`make`) REFERENCES `make` (`name_make`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `model` FOREIGN KEY (`model`) REFERENCES `model` (`name_model`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `transmission` FOREIGN KEY (`transmission_type`) REFERENCES `transmission` (`transmission_type`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `car_picture`
  ADD CONSTRAINT `pic-car` FOREIGN KEY (`license_plate`) REFERENCES `car` (`license_plate`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `employee`
  ADD CONSTRAINT `role` FOREIGN KEY (`role_name`) REFERENCES `role` (`name_role`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `insurance`
  ADD CONSTRAINT `insurance_type` FOREIGN KEY (`insurance_type`) REFERENCES `insurance_type` (`name_insurance_type`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `license_plate` FOREIGN KEY (`license_plate`) REFERENCES `car` (`license_plate`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `maintenance`
  ADD CONSTRAINT `car-main` FOREIGN KEY (`license_plate`) REFERENCES `car` (`license_plate`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `main-service` FOREIGN KEY (`service_company`) REFERENCES `service_company` (`name_service_company`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `main-type` FOREIGN KEY (`maintenance_type`) REFERENCES `maintenance_type` (`name_maintenance_type`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `model`
  ADD CONSTRAINT `make-model` FOREIGN KEY (`make`) REFERENCES `make` (`name_make`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `reservation`
  ADD CONSTRAINT `car-reservation` FOREIGN KEY (`license_plate`) REFERENCES `car` (`license_plate`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `employee-reservation` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id_employee`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
