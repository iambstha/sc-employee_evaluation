package org.base.service.apiLog;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
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

    private static final List<String> ALLOWED_SORT_COLUMNS = List.of("apiLogId", "statusCode", "userIp");

    @Override
    public List<ApiLogResDto> getPaginated(int page, int size, String sortDirection, String sortColumn) {

        try {
            page = Math.max(page, 0);
            size = Math.max(size, 0);

            String direction = (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) ? "Descending" : "Ascending";
            String sortBy = (sortColumn != null && !sortColumn.isEmpty()) ? sortColumn : ALLOWED_SORT_COLUMNS.getFirst();

            if (!ALLOWED_SORT_COLUMNS.contains(sortBy)) {
                throw new BadRequestException("Invalid sort column: " + sortBy);
            }

            return apiLogRepository
                    .findAll(Sort.by(sortBy, Sort.Direction.valueOf(direction)))
                    .page(Page.of(page, size))
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
