-- Tábla `category` adatok
INSERT INTO `category` (`name_category`) VALUES
('Szédán'),
('SUV'),
('Teherautó'),
('Kisbusz');

-- Tábla `fuel` adatok
INSERT INTO `fuel` (`fuel_type`) VALUES
('Benzin'),
('Dízel'),
('Elektromos'),
('Hibrid');

-- Tábla `make` adatok
INSERT INTO `make` (`name_make`) VALUES
('Toyota'),
('Honda'),
('Ford'),
('Chevrolet');

-- Tábla `model` adatok
INSERT INTO `model` (`name_model`) VALUES
('Camry'),
('Civic'),
('F-150'),
('Silverado');

-- Tábla `transmission` adatok
INSERT INTO `transmission` (`transmission_type`) VALUES
('Automata'),
('Kézi');
-
-- A tábla adatainak kiíratása `role`

INSERT INTO `role` (`name_role`, `description`) VALUES
('admin', 'mindenre van jogosultsága kivéve a felhasználó létrehozása'),
('superadmin', 'mindenre van jogosultsága'),
('user', 'lekérdezés jellegű funkciók');


-- Tábla `service_company` adatok
INSERT INTO `service_company` (`name_service_company`, `location`, `contact_person`, `contact_email`, `contact_phone`, `created_at`) VALUES
('AutoCare', 'Budapest, Váci utca 1', 'János Kovács', 'janos.kovacs@autocare.hu', '+36-1-234-5678', '2023-12-22'),
('Speedy Repairs', 'Budapest, Rákóczi út 10', 'Judit Szabó', 'judit.szabo@speedyrepairs.hu', '+36-1-876-5432', '2023-12-22');


-- Tábla `site` adatok
INSERT INTO `site` (`name_site`, `location`, `capacity`, `contact_person`, `contact_email`, `contact_phone`, `description`, `created_at`) VALUES
('Iroda', 'Budapest, Deák tér 5', 50, 'Gábor Nagy', 'gabor.nagy@office.hu', '+36-1-345-6789', 'Az iroda leírása 1', '2023-12-22'),
('Raktár', 'Budapest, Blaha Lujza tér 7', 75, 'Katalin Kiss', 'katalin.kiss@warehouse.hu', '+36-1-987-6543', 'A raktár leírása 2', '2023-12-22');
-- Tábla `insurance_type` adatok
INSERT INTO `insurance_type` (`name_insurance_type`) VALUES
('Kötelező felelőségbiztosítás'),
('Casco'),
('Kiterjedt biztosítás');

-- Tábla `car` adatok
INSERT INTO `car` (`license plate`, `make`, `model`, `category`, `fuel`, `doors`, `seats`, `transmission_type`, `mileage`, `service_interval`, `inspection_expiry_date`, `site_name`, `status`, `created_at`, `updated_at`, `enabled`) VALUES
('ABC123', 'Toyota', 'Camry', 'Szédán', 'Benzin', 4, 5, 'Automata', 50000, 10000, '2024-12-22', 'Iroda', 1, '2023-12-22', NULL, 1),
('XYZ789', 'Honda', 'Civic', 'Szédán', 'Benzin', 4, 5, 'Kézi', 60000, 12000, '2024-12-22', 'Raktár', 1, '2023-12-22', NULL, 1),
('DEF456', 'Ford', 'F-150', 'Teherautó', 'Dízel', 2, 3, 'Automata', 70000, 15000, '2024-12-22', 'Iroda', 1, '2023-12-22', NULL, 1),
('GHI789', 'Chevrolet', 'Silverado', 'Teherautó', 'Benzin', 2, 3, 'Kézi', 80000, 18000, '2024-12-22', 'Raktár', 1, '2023-12-22', NULL, 1);

-- Tábla `employee` adatok
INSERT INTO `employee` (`last_name`, `first_name`, `email`, `password`, `driver_license`, `role_name`, `created_at`, `updated_at`, `enabled`) VALUES
('Teszt', 'Elek', 'teszt.elek@pelda.com', 'jelszo123', 'DL12345', 'admin', '2023-12-22', NULL, 1),
('Nap', 'Pali', 'nap.pali@pelda.com', 'jelszo456', 'DL67890', 'user', '2023-12-22', NULL, 1),
('Super', 'admin', 'super.admin@pelda.com', 'superadmin', 'DL67890', 'superadmin', '2023-12-22', NULL, 1);

-- Tábla `insurance` adatok
INSERT INTO `insurance` (`license_plate`, `insurance_type`, `insurer_name`, `price`, `expire_date`, `pay_period`, `created_at`, `updated_at`, `enabled`) VALUES
('ABC123', 'Kötelező felelőségbiztosítás', 'BiztosítóCég1', 500, '2024-12-31', 'Éves', '2023-12-22', NULL, 1),
('XYZ789', 'Casco', 'BiztosítóCég2', 700, '2024-12-31', 'Fél éves', '2023-12-22', NULL, 1),
('DEF456', 'Kiterjedt biztosítás', 'BiztosítóCég3', 1000, '2024-12-31', 'Havi', '2023-12-22', NULL, 1),
('GHI789', 'Kötelező felelőségbiztosítás', 'BiztosítóCég4', 600, '2024-12-31', 'Éves', '2023-12-22', NULL, 1);

-- Tábla `maintenance_type` adatok
INSERT INTO `maintenance_type` (`name_maintenance_type`) VALUES
('Olajcsere'),
('Fékellenőrzés'),
('Gumikerekek forgatása'),
('Motorkarbantartás');

-- Tábla `maintenance` adatok
INSERT INTO `maintenance` (`license_plate`, `maintenance_type`, `service_company`, `date`, `mileage`, `description`, `amount`, `created_at`, `updated_at`) VALUES
('ABC123', 'Olajcsere', 'AutoCare', '2023-12-23', 51000, 'Rendszeres olajcsere', 50, '2023-12-22', NULL),
('XYZ789', 'Fékellenőrzés', 'Speedy Repairs', '2023-12-24', 62000, 'Fékrendszer ellenőrzés', 80, '2023-12-22', NULL),
('DEF456', 'Gumikerekek forgatása', 'AutoCare', '2023-12-25', 72000, 'Gumik forgatása egyenletes kopás érdekében', 40, '2023-12-22', NULL),
('GHI789', 'Motorkarbantartás', 'Speedy Repairs', '2023-12-26', 82000, 'Teljes motorkarbantartás', 120, '2023-12-22', NULL);

-- Tábla `reservation` adatok
INSERT INTO `reservation` (`id_reservation`, `id_employee`, `license_plate`, `start_date_time`, `end_date_time`, `description`, `created_at`, `updated_at`) VALUES
(1, 1, 'ABC123', '2023-12-23 08:00:00', '2023-12-23 17:00:00', 'Ügyfél találkozó', '2023-12-22', NULL),
(2, 2, 'XYZ789', '2023-12-24 10:30:00', '2023-12-24 15:30:00', 'Bevásárlás', '2023-12-22', NULL),
(3, 1, 'DEF456', '2023-12-25 14:00:00', '2023-12-25 18:00:00', 'Raktárból áruátvétel', '2023-12-22', NULL),
(4, 2, 'GHI789', '2023-12-26 09:00:00', '2023-12-26 16:00:00', 'Városi kirándulás', '2023-12-22', NULL),
(5, 1, 'ABC123', '2023-12-27 11:30:00', '2023-12-27 14:30:00', 'Rövid üzleti út', '2023-12-22', NULL),
(6, 2, 'XYZ789', '2023-12-28 13:00:00', '2023-12-28 18:00:00', 'Iskolai esemény', '2023-12-22', NULL),
(7, 1, 'DEF456', '2023-12-29 08:30:00', '2023-12-29 12:30:00', 'Orvosi vizsgálat', '2023-12-22', NULL),
(8, 2, 'GHI789', '2023-12-30 15:00:00', '2023-12-30 17:30:00', 'Ünnepi vásárlás', '2023-12-22', NULL),
(9, 1, 'ABC123', '2023-12-31 10:00:00', '2023-12-31 16:00:00', 'Hétvégi kirándulás', '2023-12-22', NULL),
(10, 2, 'XYZ789', '2024-01-01 12:30:00', '2024-01-01 20:00:00', 'Szilveszteri buli', '2023-12-22', NULL);
