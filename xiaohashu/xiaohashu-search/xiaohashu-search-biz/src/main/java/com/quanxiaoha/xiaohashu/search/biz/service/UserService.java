package com.quanxiaoha.xiaohashu.search.biz.service;

import com.quanxiaoha.framework.common.response.PageResponse;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.dto.RebuildUserDocumentReqDTO;
import com.quanxiaoha.xiaohashu.search.biz.model.vo.SearchUserReqVO;
import com.quanxiaoha.xiaohashu.search.biz.model.vo.SearchUserRspVO;

/**
 * 用户搜索业务
 **/
public interface UserService {

    /**
     * 搜索用户
     */
    PageResponse<SearchUserRspVO> searchUser(SearchUserReqVO searchUserReqVO);

    /**
     * 重建用户文档
     */
    Response<Long> rebuildDocument(RebuildUserDocumentReqDTO rebuildUserDocumentReqDTO);
}
