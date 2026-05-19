package com.quanxiaoha.xiaohashu.count.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  根据用户 ID 查询计数
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindUserCountByIdRspDTO {

    private Long userId;

    private Long fansTotal;

    private Long followingTotal;

    private Long noteTotal;

    private Long likeTotal;

    private Long collectTotal;
}
