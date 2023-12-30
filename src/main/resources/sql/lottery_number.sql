CREATE TABLE `lottery_number` (
  `lottery_id` int(11) NOT NULL,
  `round` bigint(20) NOT NULL,
  `winning_numbers` varchar(20) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`lottery_id`,`round`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;