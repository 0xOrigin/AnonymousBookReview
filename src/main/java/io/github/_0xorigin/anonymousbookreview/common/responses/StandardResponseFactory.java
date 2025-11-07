package io.github._0xorigin.anonymousbookreview.common.responses;

import io.github._0xorigin.anonymousbookreview.common.responses.apierrorresponse.ApiErrorResponse;
import io.github._0xorigin.anonymousbookreview.common.responses.apierrorresponse.StandardApiErrorResponse;
import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.ApiResponse;
import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.MetaInfoResponse;
import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.PaginationInfoResponse;
import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.StandardApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StandardResponseFactory implements ResponseFactory {

    private static final String SUCCESS_MESSAGE = "Success";

    @Override
    public <T> ApiResponse<T> createResponse(String message, Page<?> page, T data) {
        return StandardApiResponse
            .<T>builder()
            .message(message)
            .meta(MetaInfoResponse.builder().count(page.getTotalElements()).build())
            .pagination(PaginationInfoResponse.builder().page(page).build())
            .data(data)
            .build();
    }

    @Override
    public <T> ApiResponse<T> createSuccessResponse(Page<?> page, T data) {
        return createResponse(SUCCESS_MESSAGE, page, data);
    }

    @Override
    public <T> ApiResponse<T> createResponse(String message, Map<String, ?> extraMetaInfo, Page<?> page, T data) {
        return StandardApiResponse
            .<T>builder()
            .message(message)
            .meta(MetaInfoResponse.builder().count(page.getTotalElements()).info(extraMetaInfo).build())
            .pagination(PaginationInfoResponse.builder().page(page).build())
            .data(data)
            .build();
    }

    @Override
    public <T> ApiResponse<T> createSuccessResponse(Map<String, ?> extraMetaInfo, Page<?> page, T data) {
        return createResponse(SUCCESS_MESSAGE, extraMetaInfo, page, data);
    }

    @Override
    public <T> ApiResponse<T> createResponse(String message, T data) {
        return StandardApiResponse
            .<T>builder()
            .message(message)
            .data(data)
            .build();
    }

    @Override
    public <T> ApiResponse<T> createSuccessResponse(T data) {
        return createResponse(SUCCESS_MESSAGE, data);
    }

    @Override
    public ApiResponse<?> createEmptyResponse(String message) {
        return StandardApiResponse
            .builder()
            .message(message)
            .data(Map.of())
            .build();
    }

    @Override
    public ApiResponse<?> createEmptySuccessResponse() {
        return createEmptyResponse(SUCCESS_MESSAGE);
    }

    @Override
    public ApiErrorResponse createErrorResponse(String message, String path, Map<String, List<String>> errors) {
        return new StandardApiErrorResponse(message, path, errors);
    }
}
