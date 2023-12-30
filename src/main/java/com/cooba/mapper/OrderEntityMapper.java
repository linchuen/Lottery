package com.cooba.mapper;

import static com.cooba.mapper.OrderEntityDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import com.cooba.entity.OrderEntity;
import jakarta.annotation.Generated;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface OrderEntityMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(id, playerId, walletId, assetId, round, gameCode, guessString, betAmount, odds, betPrize, fee, status, gameStatus, createdTime, updatedTime);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="row.id", before=false, resultType=Long.class)
    int insert(InsertStatementProvider<OrderEntity> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="OrderEntityResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="player_id", property="playerId", jdbcType=JdbcType.BIGINT),
        @Result(column="wallet_id", property="walletId", jdbcType=JdbcType.INTEGER),
        @Result(column="asset_id", property="assetId", jdbcType=JdbcType.INTEGER),
        @Result(column="round", property="round", jdbcType=JdbcType.BIGINT),
        @Result(column="game_code", property="gameCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="guess_string", property="guessString", jdbcType=JdbcType.VARCHAR),
        @Result(column="bet_amount", property="betAmount", jdbcType=JdbcType.DECIMAL),
        @Result(column="odds", property="odds", jdbcType=JdbcType.DECIMAL),
        @Result(column="bet_prize", property="betPrize", jdbcType=JdbcType.DECIMAL),
        @Result(column="fee", property="fee", jdbcType=JdbcType.DECIMAL),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="game_status", property="gameStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="created_time", property="createdTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updated_time", property="updatedTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<OrderEntity> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("OrderEntityResult")
    Optional<OrderEntity> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, orderEntity, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, orderEntity, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(OrderEntity row) {
        return MyBatis3Utils.insert(this::insert, row, orderEntity, c ->
            c.map(playerId).toProperty("playerId")
            .map(walletId).toProperty("walletId")
            .map(assetId).toProperty("assetId")
            .map(round).toProperty("round")
            .map(gameCode).toProperty("gameCode")
            .map(guessString).toProperty("guessString")
            .map(betAmount).toProperty("betAmount")
            .map(odds).toProperty("odds")
            .map(betPrize).toProperty("betPrize")
            .map(fee).toProperty("fee")
            .map(status).toProperty("status")
            .map(gameStatus).toProperty("gameStatus")
            .map(createdTime).toProperty("createdTime")
            .map(updatedTime).toProperty("updatedTime")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(OrderEntity row) {
        return MyBatis3Utils.insert(this::insert, row, orderEntity, c ->
            c.map(playerId).toPropertyWhenPresent("playerId", row::getPlayerId)
            .map(walletId).toPropertyWhenPresent("walletId", row::getWalletId)
            .map(assetId).toPropertyWhenPresent("assetId", row::getAssetId)
            .map(round).toPropertyWhenPresent("round", row::getRound)
            .map(gameCode).toPropertyWhenPresent("gameCode", row::getGameCode)
            .map(guessString).toPropertyWhenPresent("guessString", row::getGuessString)
            .map(betAmount).toPropertyWhenPresent("betAmount", row::getBetAmount)
            .map(odds).toPropertyWhenPresent("odds", row::getOdds)
            .map(betPrize).toPropertyWhenPresent("betPrize", row::getBetPrize)
            .map(fee).toPropertyWhenPresent("fee", row::getFee)
            .map(status).toPropertyWhenPresent("status", row::getStatus)
            .map(gameStatus).toPropertyWhenPresent("gameStatus", row::getGameStatus)
            .map(createdTime).toPropertyWhenPresent("createdTime", row::getCreatedTime)
            .map(updatedTime).toPropertyWhenPresent("updatedTime", row::getUpdatedTime)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<OrderEntity> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, orderEntity, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<OrderEntity> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, orderEntity, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<OrderEntity> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, orderEntity, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<OrderEntity> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, orderEntity, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(OrderEntity row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(playerId).equalTo(row::getPlayerId)
                .set(walletId).equalTo(row::getWalletId)
                .set(assetId).equalTo(row::getAssetId)
                .set(round).equalTo(row::getRound)
                .set(gameCode).equalTo(row::getGameCode)
                .set(guessString).equalTo(row::getGuessString)
                .set(betAmount).equalTo(row::getBetAmount)
                .set(odds).equalTo(row::getOdds)
                .set(betPrize).equalTo(row::getBetPrize)
                .set(fee).equalTo(row::getFee)
                .set(status).equalTo(row::getStatus)
                .set(gameStatus).equalTo(row::getGameStatus)
                .set(createdTime).equalTo(row::getCreatedTime)
                .set(updatedTime).equalTo(row::getUpdatedTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(OrderEntity row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(playerId).equalToWhenPresent(row::getPlayerId)
                .set(walletId).equalToWhenPresent(row::getWalletId)
                .set(assetId).equalToWhenPresent(row::getAssetId)
                .set(round).equalToWhenPresent(row::getRound)
                .set(gameCode).equalToWhenPresent(row::getGameCode)
                .set(guessString).equalToWhenPresent(row::getGuessString)
                .set(betAmount).equalToWhenPresent(row::getBetAmount)
                .set(odds).equalToWhenPresent(row::getOdds)
                .set(betPrize).equalToWhenPresent(row::getBetPrize)
                .set(fee).equalToWhenPresent(row::getFee)
                .set(status).equalToWhenPresent(row::getStatus)
                .set(gameStatus).equalToWhenPresent(row::getGameStatus)
                .set(createdTime).equalToWhenPresent(row::getCreatedTime)
                .set(updatedTime).equalToWhenPresent(row::getUpdatedTime);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(OrderEntity row) {
        return update(c ->
            c.set(playerId).equalTo(row::getPlayerId)
            .set(walletId).equalTo(row::getWalletId)
            .set(assetId).equalTo(row::getAssetId)
            .set(round).equalTo(row::getRound)
            .set(gameCode).equalTo(row::getGameCode)
            .set(guessString).equalTo(row::getGuessString)
            .set(betAmount).equalTo(row::getBetAmount)
            .set(odds).equalTo(row::getOdds)
            .set(betPrize).equalTo(row::getBetPrize)
            .set(fee).equalTo(row::getFee)
            .set(status).equalTo(row::getStatus)
            .set(gameStatus).equalTo(row::getGameStatus)
            .set(createdTime).equalTo(row::getCreatedTime)
            .set(updatedTime).equalTo(row::getUpdatedTime)
            .where(id, isEqualTo(row::getId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(OrderEntity row) {
        return update(c ->
            c.set(playerId).equalToWhenPresent(row::getPlayerId)
            .set(walletId).equalToWhenPresent(row::getWalletId)
            .set(assetId).equalToWhenPresent(row::getAssetId)
            .set(round).equalToWhenPresent(row::getRound)
            .set(gameCode).equalToWhenPresent(row::getGameCode)
            .set(guessString).equalToWhenPresent(row::getGuessString)
            .set(betAmount).equalToWhenPresent(row::getBetAmount)
            .set(odds).equalToWhenPresent(row::getOdds)
            .set(betPrize).equalToWhenPresent(row::getBetPrize)
            .set(fee).equalToWhenPresent(row::getFee)
            .set(status).equalToWhenPresent(row::getStatus)
            .set(gameStatus).equalToWhenPresent(row::getGameStatus)
            .set(createdTime).equalToWhenPresent(row::getCreatedTime)
            .set(updatedTime).equalToWhenPresent(row::getUpdatedTime)
            .where(id, isEqualTo(row::getId))
        );
    }
}