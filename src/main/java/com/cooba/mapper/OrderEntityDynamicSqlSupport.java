package com.cooba.mapper;

import jakarta.annotation.Generated;
import java.math.BigDecimal;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class OrderEntityDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final OrderEntity orderEntity = new OrderEntity();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> id = orderEntity.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> playerId = orderEntity.playerId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> walletId = orderEntity.walletId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> assetId = orderEntity.assetId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> round = orderEntity.round;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> gameCode = orderEntity.gameCode;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> guessString = orderEntity.guessString;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<BigDecimal> betAmount = orderEntity.betAmount;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<BigDecimal> odds = orderEntity.odds;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<BigDecimal> betPrize = orderEntity.betPrize;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<BigDecimal> fee = orderEntity.fee;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> status = orderEntity.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> gameStatus = orderEntity.gameStatus;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> createdTime = orderEntity.createdTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> updatedTime = orderEntity.updatedTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class OrderEntity extends AliasableSqlTable<OrderEntity> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<Long> playerId = column("player_id", JDBCType.BIGINT);

        public final SqlColumn<Integer> walletId = column("wallet_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> assetId = column("asset_id", JDBCType.INTEGER);

        public final SqlColumn<Long> round = column("round", JDBCType.BIGINT);

        public final SqlColumn<String> gameCode = column("game_code", JDBCType.VARCHAR);

        public final SqlColumn<String> guessString = column("guess_string", JDBCType.VARCHAR);

        public final SqlColumn<BigDecimal> betAmount = column("bet_amount", JDBCType.DECIMAL);

        public final SqlColumn<BigDecimal> odds = column("odds", JDBCType.DECIMAL);

        public final SqlColumn<BigDecimal> betPrize = column("bet_prize", JDBCType.DECIMAL);

        public final SqlColumn<BigDecimal> fee = column("fee", JDBCType.DECIMAL);

        public final SqlColumn<Integer> status = column("status", JDBCType.INTEGER);

        public final SqlColumn<Integer> gameStatus = column("game_status", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> createdTime = column("created_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> updatedTime = column("updated_time", JDBCType.TIMESTAMP);

        public OrderEntity() {
            super("order_entity", OrderEntity::new);
        }
    }
}