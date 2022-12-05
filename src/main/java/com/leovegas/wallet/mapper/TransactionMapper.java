package com.leovegas.wallet.mapper;

import com.leovegas.wallet.dto.TransactionDto;
import com.leovegas.wallet.entity.Transaction;
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
public interface TransactionMapper {

	TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);


	TransactionDto toDto(Transaction source);

	List<TransactionDto> toDTOs(List<Transaction> source);

}
