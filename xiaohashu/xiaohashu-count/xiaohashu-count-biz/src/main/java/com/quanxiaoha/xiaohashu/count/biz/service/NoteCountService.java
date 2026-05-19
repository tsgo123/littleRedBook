package com.quanxiaoha.xiaohashu.count.biz.service;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.count.dto.FindNoteCountByIdReqDTO;
import com.quanxiaoha.xiaohashu.count.dto.FindNoteCountByIdRspDTO;

/**
 * 笔记计数业务
 **/
public interface NoteCountService {

    /**
     * 查询笔记计数数据
     */
    Response<FindNoteCountByIdRspDTO> findNoteCountData(FindNoteCountByIdReqDTO findNoteCountByIdReqDTO);
}
