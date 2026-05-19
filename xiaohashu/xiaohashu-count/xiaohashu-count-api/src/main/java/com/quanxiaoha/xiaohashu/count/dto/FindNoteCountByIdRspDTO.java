package com.quanxiaoha.xiaohashu.count.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据笔记 ID 查询计数
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindNoteCountByIdRspDTO {

    private Long noteId;

    private Long likeTotal;

    private Long collectTotal;

    private Long commentTotal;
}
