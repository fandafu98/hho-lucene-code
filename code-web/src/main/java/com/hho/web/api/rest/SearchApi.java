/**
 * @author: 郑延康 * @date: 2024-05-29 10:40
 */
package com.hho.web.api.rest;

import com.hho.domain.page.PageParam;
import com.hho.domain.page.PageResult;
import com.hho.domain.param.SearchParam;
import com.hho.domain.param.UpdateBatchParam;
import com.hho.domain.result.ContentResult;
import com.hho.service.LuceneDemoManager;
import com.hho.web.domain.RestResponseInfo;
import com.hho.web.domain.common.PageRequest;
import com.hho.web.domain.common.PageResponse;
import com.hho.web.domain.mapper.SearchApiObjMapper;
import com.hho.web.domain.request.SearchRequest;
import com.hho.web.domain.request.UpdateBatchRequest;
import com.hho.web.domain.response.ContentResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhengyankang * @version SearchAPi.java, v 0.1 2024-05-29 10:40
 */
@RestController
@RequestMapping("/search")
@AllArgsConstructor
public class SearchApi {

    private LuceneDemoManager luceneDemoManager;
    private SearchApiObjMapper searchApiObjMapper;

    /**
     * 分页检索满足条件的doc
     *
     * @param pageRequest
     * @return
     */
    @ApiModelProperty("分页检索满足条件的doc")
    @PostMapping("/page-query")
    public RestResponseInfo<PageResponse<ContentResponse>> pageQuery(@RequestBody PageRequest<SearchRequest> pageRequest) {
        PageParam<SearchParam> pageParam = searchApiObjMapper.toSearchParam(pageRequest);
        PageResult<ContentResult> pageResult = luceneDemoManager.pageQuery(pageParam);
        return RestResponseInfo.ok(searchApiObjMapper.toContentResponse(pageResult));
    }


    /**
     * 查询满足条件的文档数
     *
     * @param request
     * @return
     */
    @ApiModelProperty("查询满足条件的文档数")
    @PostMapping("/get-docs-count")
    public RestResponseInfo<Integer> getDocsCount(@RequestBody SearchRequest request) {
        SearchParam param = searchApiObjMapper.toSearchParam(request);
        Integer result = luceneDemoManager.getDocsCount(param);
        return RestResponseInfo.ok(result);
    }


    /**
     * 基于id增量批量更新索引
     *
     * @param requestList
     * @return
     */
    @ApiModelProperty("基于id增量批量更新索引")
    @PostMapping("/update-batch")
    public RestResponseInfo<String> updateBatch(@RequestBody List<UpdateBatchRequest> requestList) {
        List<UpdateBatchParam> paramList = searchApiObjMapper.toUpdateBatchParam(requestList);
        luceneDemoManager.updateBatch(paramList);
        return RestResponseInfo.ok();
    }


}
