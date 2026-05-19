package com.quanxiaoha.xiaohashu.note.biz.service;


import com.quanxiaoha.framework.common.response.PageResponse;
import com.quanxiaoha.xiaohashu.note.biz.model.vo.FindDiscoverNotePageListReqVO;
import com.quanxiaoha.xiaohashu.note.biz.model.vo.FindDiscoverNoteRspVO;

/**
 * 发现页业务
 **/
public interface DiscoverService {

    PageResponse<FindDiscoverNoteRspVO> findNoteList(FindDiscoverNotePageListReqVO findDiscoverNoteListReqVO);
}
