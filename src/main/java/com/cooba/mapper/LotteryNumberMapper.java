package com.cooba.mapper;

import org.apache.ibatis.annotations.Param;

public interface LotteryNumberMapper {
    int insertNumbers(@Param("lotteryId") int lotteryId, @Param("round") long round, @Param("winningNumbers") String numberString);
}
