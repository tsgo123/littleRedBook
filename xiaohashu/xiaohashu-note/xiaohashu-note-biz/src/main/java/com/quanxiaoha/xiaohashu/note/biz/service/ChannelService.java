package com.quanxiaoha.xiaohashu.note.biz.service;


import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.note.biz.model.vo.FindChannelRspVO;

import java.util.List;

/**
 * 频道业务
 **/
public interface ChannelService {

    /**
     * 查询所有频道
     */
    Response<List<FindChannelRspVO>> findChannelList();
}
