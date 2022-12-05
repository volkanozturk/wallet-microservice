package com.leovegas.wallet.mapper;

import com.leovegas.wallet.dto.ApiLogDto;
import com.leovegas.wallet.entity.ApiLog;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

/**
 * @author volkanozturk
 */
@Mapper(
		componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.ERROR, unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface ApiLogMapper {

	ApiLogMapper INSTANCE = Mappers.getMapper(ApiLogMapper.class);

	ApiLog toEntity(ApiLogDto apiLogDto);

	ApiLogDto toDto(ApiLog apiLog);

	List<ApiLog> toEntityList(List<ApiLogDto> apiLogDtoList);

	List<ApiLogDto> toDtoList(List<ApiLog> apiLogList);

	@AfterMapping
	default void setEntityId(ApiLogDto apiLogDto, @MappingTarget ApiLog apiLog) {
		if (Objects.nonNull(apiLogDto.getId()))
			apiLog.setId(apiLogDto.getId());
	}

}
