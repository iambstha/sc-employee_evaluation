package org.base.mapper;

import org.base.dto.ScoreReqDto;
import org.base.dto.ScoreResDto;
import org.base.model.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = QuarkusMappingConfig.class)
public interface ScoreMapper {

    ScoreReqDto toReqDto(Score score);

    ScoreResDto toResDto(Score score);

    @Mapping(target = "scoreId", ignore = true)
    Score toEntity(ScoreReqDto scoreReqDto);

    @Mapping(target = "scoreId", ignore = true)
    void updateEntityFromDto(ScoreReqDto scoreReqDto, @MappingTarget Score existingScore);

}
