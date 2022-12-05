package com.leovegas.wallet.mapper;

import com.leovegas.wallet.dto.PaymentDto;
import com.leovegas.wallet.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author volkanozturk
 */

@Mapper(
		componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PaymentMapper {

	PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

	@Mapping(source = "wallet.playerId", target = "playerId")
	@Mapping(source = "wallet.balance", target = "balance")
	@Mapping(source = "wallet.lastUpdatedAt", target = "lastUpdatedAt")
	@Mapping(ignore = true, target = "createdAt")
	PaymentDto toDto(Wallet wallet);
}
