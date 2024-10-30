package org.base.service.apiLog;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.base.dto.ApiLogResDto;
import org.base.exception.BadRequestException;
import org.base.mapper.ApiLogMapper;
import org.base.repository.ApiLogRepository;

import java.util.List;

@ApplicationScoped
@Transactional
public class ApiLogServiceImpl implements ApiLogService {

    @Inject
    ApiLogRepository apiLogRepository;

    @Inject
    ApiLogMapper apiLogMapper;

    @Override
    public List<ApiLogResDto> getPaginated(int page, int size) {

        try {
            page = Math.max(page, 0);
            size = Math.max(size, 0);

            return apiLogRepository.findAll()
                    .page(page, size)
                    .list()
                    .stream()
                    .map(apiLogMapper::toResDto)
                    .toList();
        } catch (Exception e){
            throw new BadRequestException("Error occurred while fetching api logs: " + e.getMessage());
        }
    }

    public long countTotal() {
        return apiLogRepository.count();
    }

}
