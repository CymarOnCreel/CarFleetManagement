
INSERT INTO `category` (`name_category`) VALUES
('Kisbusz'),
('SUV'),
('Szédán'),
('Sport'),
('Teherautó');


INSERT INTO `fuel` (`fuel_type`) VALUES
('Benzin'),
('Dízel'),
('Elektromos'),
('Hibrid');


INSERT INTO `insurance_type` (`name_insurance_type`) VALUES
('Casco'),
('Kiterjedt biztosítás'),
('Kötelező felelőségbiztosítás');


INSERT INTO `make` (`name_make`) VALUES
('Chevrolet'),
('Ford'),
('Honda'),
('BMW'),
('Toyota');

INSERT INTO `model` (`name_model`, `make`) VALUES
('Silverado', 'Chevrolet'),
('F-150', 'Ford'),
('Civic', 'Honda'),
('Silverad', 'Toyota'),
('Silvera', 'Ford'),
('A5', 'BMW'),
('Camry', 'Toyota');


INSERT INTO `role` (`name_role`, `description`) VALUES
('admin', 'mindenre van jogosultsága kivéve a felhasználó létrehozása'),
('superadmin', 'mindenre van jogosultsága'),
('user', 'lekérdezés jellegű funkciók');

INSERT INTO `service_company` (`name_service_company`, `location`, `contact_person`, `contact_email`, `contact_phone`, `created_at`, `updated_at`, `enabled`) VALUES
('AutoCare', 'Budapest, Váci utca 1', 'János Kovács', 'janos.kovacs@autocare.hu', '+36-1-234-5678', '2024-01-22', NULL, 1),
('Speedy Repairs', 'Budapest, Rákóczi út 10', 'Judit Szabó', 'judit.szabo@speedyrepairs.hu', '+36-1-876-5432', '2024-01-22', NULL, 1);

INSERT INTO `site` (`name_site`, `location`, `capacity`, `contact_person`, `contact_email`, `contact_phone`, `description`, `created_at`, `updated_at`, `enabled`) VALUES
('Iroda', 'Budapest, Deák tér 5', 50, 'Gábor Nagy', 'gabor.nagy@office.hu', '+36-1-345-6789', 'Az iroda leírása 1', '2024-01-22', NULL, 1),
('Raktár', 'Budapest, Blaha Lujza tér 7', 75, 'Katalin Kiss', 'katalin.kiss@warehouse.hu', '+36-1-987-6543', 'A raktár leírása 2', '2024-01-22', NULL, 1);

INSERT INTO `transmission` (`transmission_type`) VALUES
('Automata'),
('Kézi');

INSERT INTO `car` (`license_plate`, `make`, `model`,`category`, `fuel`, `doors`, `seats`, `transmission_type`, `mileage`, `service_interval`, `inspection_expiry_date`, `site_name`, `status`, `created_at`, `updated_at`, `enabled`) VALUES
('ABC123', 'Toyota', 'Camry', 'Szédán', 'Benzin', 4, 5, 'Automata', 50000, 10000, '2023-12-22', 'Iroda', 1, '2024-01-22', NULL, 1),
('DEF456', 'Ford', 'F-150', 'Teherautó', 'Dízel', 2, 3, 'Automata', 70000, 15000, '2023-12-22', 'Iroda', 1, '2024-01-22', NULL, 1),
('GHI789', 'Chevrolet', 'Silverado', 'Teherautó', 'Benzin', 2, 3, 'Kézi', 80000, 18000, '2023-12-22', 'Raktár', 1, '2024-01-22', NULL, 1),
('ABC127', 'Toyota', 'Camry', 'Sport', 'Benzin', 2, 3, 'Kézi', 80000, 18000, '2023-02-22', 'Raktár', 1, '2024-01-22', NULL, 1),
('ABC125', 'Toyota', 'Silverad', 'Sport', 'Benzin', 2, 3, 'Kézi', 80000, 18000, '2023-12-22', 'Raktár', 1, '2024-01-22', NULL, 1),
('ABC124', 'Toyota', 'Camry', 'SUV', 'Benzin', 2, 3, 'Kézi', 80000, 18000, '2023-12-22', 'Raktár', 1, '2024-01-22', NULL, 1),
('ABC126', 'Ford', 'Silvera', 'SUV', 'Benzin', 2, 3, 'Kézi', 80000, 18000, '2023-12-22', 'Raktár', 1, '2024-01-22', NULL, 1),
('AA:BB:111', 'BMW', 'A5', 'Sport', 'Benzin', 2, 3, 'Kézi', 80000, 18000, '2023-12-22', 'Raktár', 1, '2024-01-22', NULL, 1),
('XYZ789', 'Honda', 'Civic', 'Szédán', 'Benzin', 4, 5, 'Kézi', 60000, 12000, '2023-12-22', 'Raktár', 1, '2024-01-22', NULL, 1);

INSERT INTO `employee` (`id_employee`, `last_name`, `first_name`, `email`, `password`, `driver_license`, `role_name`, `created_at`, `updated_at`, `enabled`) VALUES
(1, 'Teszt', 'Elek', 'teszt.elek@pelda.com', 'cd1782eaa0babbad3736b8821720c55f961c7416', 'DL12345', 'admin', '2024-01-22', NULL, 1), -- jelszo123
(2, 'Nap', 'Pali', 'nap.pali@pelda.com', '77807704eef190e89d39ba68a3db1b4a0e4021ff', 'DL67890', 'user', '2024-01-22', NULL, 1), -- jelszo456
(3, 'Super', 'admin', 'super.admin@pelda.com', '889a3a791b3875cfae413574b53da4bb8a90d53e', 'DL67890', 'superadmin', '2024-01-22', NULL, 1); -- superadmin

INSERT INTO `insurance` (`id_insurance`, `license_plate`, `insurance_type`, `insurer_name`, `price`, `expire_date`, `pay_period`, `created_at`, `updated_at`, `enabled`) VALUES
(1, 'ABC123', 'Kötelező felelőségbiztosítás', 'BiztosítóCég1', 500, '2024-12-31', 'Éves', '2024-01-22', NULL, 1),
(2, 'XYZ789', 'Casco', 'BiztosítóCég2', 700, '2024-12-31', 'Fél éves', '2024-01-22', NULL, 1),
(3, 'DEF456', 'Kiterjedt biztosítás', 'BiztosítóCég3', 1000, '2024-12-31', 'Havi', '2024-01-22', NULL, 1),
(4, 'GHI789', 'Kötelező felelőségbiztosítás', 'BiztosítóCég4', 600, '2024-12-31', 'Éves', '2024-01-22', NULL, 1);

INSERT INTO `maintenance_type` (`name_maintenance_type`) VALUES
('Fékellenőrzés'),
('Gumikerekek forgatása'),
('Motorkarbantartás'),
('Olajcsere');

INSERT INTO `maintenance` (`id_maintenance`, `license_plate`, `maintenance_type`, `service_company`, `date`, `mileage`, `description`, `amount`, `created_at`, `updated_at`) VALUES
(1, 'ABC123', 'Olajcsere', 'AutoCare', '2024-01-23', 51000, 'Rendszeres olajcsere', 50, '2024-01-22', NULL),
(2, 'XYZ789', 'Fékellenőrzés', 'Speedy Repairs', '2024-01-24', 62000, 'Fékrendszer ellenőrzés', 80, '2024-01-22', NULL),
(3, 'DEF456', 'Gumikerekek forgatása', 'AutoCare', '2024-01-25', 72000, 'Gumik forgatása egyenletes kopás érdekében', 40, '2024-01-22', NULL),
(4, 'GHI789', 'Motorkarbantartás', 'Speedy Repairs', '2024-01-26', 82000, 'Teljes motorkarbantartás', 120, '2024-01-22', NULL);


INSERT INTO `reservation` (`id_reservation`, `id_employee`, `license_plate`, `start_date_time`, `end_date_time`, `description`, `created_at`, `updated_at`, `is_deleted`) VALUES
(1, 1, 'ABC123', '2024-01-23 08:00:00', '2024-01-23 17:00:00', 'Ügyfél találkozó', '2024-01-25', NULL,1),
(2, 2, 'XYZ789', '2024-01-24 10:30:00', '2024-01-24 15:30:00', 'Bevásárlás', '2024-01-25', NULL,1),
(3, 1, 'DEF456', '2024-01-25 14:00:00', '2024-01-25 18:00:00', 'Raktárból áruátvétel', '2024-01-24', NULL,0),
(4, 2, 'GHI789', '2024-01-26 09:00:00', '2024-01-26 16:00:00', 'Városi kirándulás', '2024-01-27', NULL,0),
(5, 1, 'ABC123', '2024-01-27 11:30:00', '2024-01-27 14:30:00', 'Rövid üzleti út', '2024-01-27', NULL,0),
(6, 2, 'XYZ789', '2024-01-28 13:00:00', '2024-01-28 18:00:00', 'Iskolai esemény', '2024-01-28', NULL,0),
(7, 1, 'DEF456', '2024-01-29 08:30:00', '2024-01-29 12:30:00', 'Orvosi vizsgálat', '2024-01-29', NULL,1),
(8, 2, 'GHI789', '2024-01-30 15:00:00', '2024-01-30 17:30:00', 'Ünnepi vásárlás', '2024-02-01', NULL,0),
(9, 1, 'ABC123', '2024-01-31 10:00:00', '2024-01-31 16:00:00', 'Hétvégi kirándulás', '2024-02-01', NULL,0),
(10, 2, 'XYZ789', '2024-01-01 12:30:00', '2024-02-01 20:00:00', 'Szülinapi buli', '2024-02-01', NULL,1);

INSERT INTO `car_picture` (`picture_path`, `license_plate`) VALUES
('application/pictures/type/toyota luxury.png','ABC127'),
('application/pictures/type/toyota sports.png','ABC125'),
('application/pictures/type/toyota suv.png','ABC124'),
('application/pictures/type/ford luxury.png','DEF456'),
('application/pictures/type/tesla.png','XYZ789'),
('application/pictures/type/ford suv.png','ABC126'),
('application/pictures/type/kia cabrio.png','ABC123'),
('application/pictures/type/bmw luxury.png','AA:BB:111'),
('application/pictures/type/mercedes luxury.png','GHI789');