CREATE TABLE `simple_wallet` (
  `player_id` bigint(20) NOT NULL,
  `asset_id` int(11) NOT NULL,
  `amount` decimal(16,4) DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`player_id`,`asset_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;