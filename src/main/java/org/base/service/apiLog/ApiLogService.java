package org.base.service.apiLog;

import org.base.dto.ApiLogResDto;

import java.util.List;

public interface ApiLogService {

    List<ApiLogResDto> getPaginated(int i, int size);

    long countTotal();
}
