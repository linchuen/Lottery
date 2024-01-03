CREATE TABLE `order_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `player_id` bigint(20) DEFAULT NULL,
  `wallet_id` int(11) DEFAULT NULL,
  `asset_id` int(11) DEFAULT NULL,
  `lottery_id` int(11) DEFAULT NULL,
  `round` bigint(20) DEFAULT NULL,
  `game_code` varchar(20) DEFAULT NULL,
  `guess_string` varchar(30) DEFAULT NULL,
  `bet_amount` decimal(16,4) DEFAULT NULL,
  `odds` decimal(16,4) DEFAULT NULL,
  `bet_prize` decimal(16,4) DEFAULT NULL,
  `fee` decimal(16,4) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `game_status` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;