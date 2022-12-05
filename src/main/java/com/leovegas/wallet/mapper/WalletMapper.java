package com.leovegas.wallet.mapper;

import com.leovegas.wallet.dto.TransactionDto;
import com.leovegas.wallet.dto.WalletDto;
import com.leovegas.wallet.entity.Transaction;
import com.leovegas.wallet.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author volkanozturk
 */
@Mapper(
		componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WalletMapper {

	WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

	WalletDto toDto(Wallet source);

	List<WalletDto> toDTOs(List<Wallet> source);

}
