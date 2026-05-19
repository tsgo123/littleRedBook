package com.quanxiaoha.xiaohashu.user.biz.rpc;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.count.api.CountFeignApi;
import com.quanxiaoha.xiaohashu.count.dto.FindNoteCountByIdReqDTO;
import com.quanxiaoha.xiaohashu.count.dto.FindNoteCountByIdRspDTO;
import com.quanxiaoha.xiaohashu.count.dto.FindUserCountByIdReqDTO;
import com.quanxiaoha.xiaohashu.count.dto.FindUserCountByIdRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 计数服务
 **/
@Component
public class CountRpcService {

    @Resource
    private CountFeignApi countFeignApi;

    /**
     * 查询用户计数信息
     */
    public FindUserCountByIdRspDTO findUserCountById(Long userId) {
        FindUserCountByIdReqDTO findUserCountByIdReqDTO = new FindUserCountByIdReqDTO();
        findUserCountByIdReqDTO.setUserId(userId);

        Response<FindUserCountByIdRspDTO> response = countFeignApi.findUserCount(findUserCountByIdReqDTO);

        if (Objects.isNull(response) || !response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

}
