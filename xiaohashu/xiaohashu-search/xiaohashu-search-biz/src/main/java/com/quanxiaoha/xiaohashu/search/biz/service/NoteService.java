package com.quanxiaoha.xiaohashu.search.biz.service;

import com.quanxiaoha.framework.common.response.PageResponse;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.dto.RebuildNoteDocumentReqDTO;
import com.quanxiaoha.xiaohashu.search.biz.model.vo.SearchNoteReqVO;
import com.quanxiaoha.xiaohashu.search.biz.model.vo.SearchNoteRspVO;

/**
 * 笔记搜索业务
 **/
public interface NoteService {

    /**
     * 搜索笔记
     */
    PageResponse<SearchNoteRspVO> searchNote(SearchNoteReqVO searchNoteReqVO);

    /**
     * 重建笔记文档
     */
    Response<Long> rebuildDocument(RebuildNoteDocumentReqDTO rebuildNoteDocumentReqDTO);
}
