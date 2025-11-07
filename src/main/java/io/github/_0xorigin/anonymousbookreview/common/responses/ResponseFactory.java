package io.github._0xorigin.anonymousbookreview.common.responses;

import io.github._0xorigin.anonymousbookreview.common.responses.apierrorresponse.ApiErrorResponse;
import io.github._0xorigin.anonymousbookreview.common.responses.apiresponse.ApiResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ResponseFactory {
    <T> ApiResponse<T> createResponse(String message, Page<?> page, T data);

    <T> ApiResponse<T> createSuccessResponse(Page<?> page, T data);

    <T> ApiResponse<T> createResponse(String message, Map<String, ?> extraMetaInfo, Page<?> page, T data);

    <T> ApiResponse<T> createSuccessResponse(Map<String, ?> extraMetaInfo, Page<?> page, T data);

    <T> ApiResponse<T> createResponse(String message, T data);

    <T> ApiResponse<T> createSuccessResponse(T data);

    ApiResponse<?> createEmptyResponse(String message);

    ApiResponse<?> createEmptySuccessResponse();

    ApiErrorResponse createErrorResponse(String message, String path, Map<String, List<String>> errors);
}
