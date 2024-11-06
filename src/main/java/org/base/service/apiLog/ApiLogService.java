package org.base.service.apiLog;

import org.base.dto.ApiLogResDto;

import java.util.List;

public interface ApiLogService {

    List<ApiLogResDto> getPaginated(int page, int size, String sortDirection, String sortColumn);

    long countTotal();
}
