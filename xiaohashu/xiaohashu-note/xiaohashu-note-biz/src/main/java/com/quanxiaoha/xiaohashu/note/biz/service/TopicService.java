package com.quanxiaoha.xiaohashu.note.biz.service;


import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.note.biz.model.vo.FindTopicListReqVO;
import com.quanxiaoha.xiaohashu.note.biz.model.vo.FindTopicRspVO;

import java.util.List;

/**
 * 话题业务
 **/
public interface TopicService {

    Response<List<FindTopicRspVO>> findTopicList(FindTopicListReqVO findTopicListReqVO);
}
