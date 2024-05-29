package com.hho.web.domain.mapper;

import com.hho.domain.page.PageParam;
import com.hho.domain.page.PageResult;
import com.hho.domain.param.SearchParam;
import com.hho.domain.param.UpdateBatchParam;
import com.hho.domain.result.ContentResult;
import com.hho.web.domain.common.PageRequest;
import com.hho.web.domain.common.PageResponse;
import com.hho.web.domain.request.SearchRequest;
import com.hho.web.domain.request.UpdateBatchRequest;
import com.hho.web.domain.response.ContentResponse;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface SearchApiObjMapper {

    /**
     *
     * @param requestList
     * @return
     */
    List<UpdateBatchParam> toUpdateBatchParam(List<UpdateBatchRequest> requestList);

    /**
     *
     * @param pageRequest
     * @return
     */
    PageParam<SearchParam> toSearchParam(PageRequest<SearchRequest> pageRequest);

    /**
     *
     * @param pageResult
     * @return
     */
    PageResponse<ContentResponse> toContentResponse(PageResult<ContentResult> pageResult);

    /**
     *
     * @param request
     * @return
     */
    SearchParam toSearchParam(SearchRequest request);





}