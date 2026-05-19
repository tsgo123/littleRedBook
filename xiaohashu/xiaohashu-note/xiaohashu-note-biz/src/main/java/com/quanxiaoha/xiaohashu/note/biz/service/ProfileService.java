package com.quanxiaoha.xiaohashu.note.biz.service;

import com.quanxiaoha.framework.common.response.PageResponse;
import com.quanxiaoha.xiaohashu.note.biz.model.vo.FindProfileNotePageListReqVO;
import com.quanxiaoha.xiaohashu.note.biz.model.vo.FindProfileNoteRspVO;

/**
 * 个人主页业务
 **/
public interface ProfileService {

    PageResponse<FindProfileNoteRspVO> findNoteList(FindProfileNotePageListReqVO findProfileNotePageListReqVO);
}
