package org.base.service.competencyGroupComment;

import org.base.dto.CompetencyGroupCommentReqDto;
import org.base.dto.CompetencyGroupCommentResDto;

import java.util.List;

public interface CompetencyGroupCommentService {


    CompetencyGroupCommentResDto save(CompetencyGroupCommentReqDto competencyGroupCommentReqDto);

    List<CompetencyGroupCommentResDto> getAll();

    CompetencyGroupCommentResDto getById(Long id);

    CompetencyGroupCommentResDto updateById(Long id, CompetencyGroupCommentReqDto competencyGroupCommentReqDto);

    void deleteById(Long id);

}
