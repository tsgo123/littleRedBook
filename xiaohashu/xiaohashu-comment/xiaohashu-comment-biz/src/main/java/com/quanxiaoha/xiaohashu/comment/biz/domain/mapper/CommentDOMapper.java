package com.quanxiaoha.xiaohashu.comment.biz.domain.mapper;

import com.quanxiaoha.xiaohashu.comment.biz.domain.dataobject.CommentDO;
import com.quanxiaoha.xiaohashu.comment.biz.model.bo.CommentBO;
import com.quanxiaoha.xiaohashu.comment.biz.model.bo.CommentHeatBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDOMapper {
    int deleteByPrimaryKey(Long id);

    /**
     * 删除一级评论下，所有二级评论
     */
    int deleteByParentId(Long commentId);

    /**
     * 批量删除评论
     */
    int deleteByIds(@Param("commentIds") List<Long> commentIds);

    int insert(CommentDO record);

    /**
     * 批量插入评论
     */
    int batchInsert(@Param("comments") List<CommentBO> comments);

    int insertSelective(CommentDO record);

    CommentDO selectByPrimaryKey(Long id);

    /**
     * 根据 reply_comment_id 查询
     */
    CommentDO selectByReplyCommentId(Long commentId);

    /**
     * 根据评论 ID 批量查询
     */
    List<CommentDO> selectByCommentIds(@Param("commentIds") List<Long> commentIds);

    /**
     * 批量查询计数数据
     */
    List<CommentDO> selectCommentCountByIds(@Param("commentIds") List<Long> commentIds);

    /**
     * 查询子评论
     * @param parentId
     */
    List<CommentDO> selectChildCommentsByParentIdAndLimit(@Param("parentId") Long parentId,
                                                          @Param("limit") int limit);

    /**
     * 批量查询二级评论
     */
    List<CommentDO> selectTwoLevelCommentByIds(@Param("commentIds") List<Long> commentIds);

    /**
     * 查询一级评论下最早回复的评论
     */
    CommentDO selectEarliestByParentId(Long parentId);

    /**
     * 查询评论分页数据
     */
    List<CommentDO> selectPageList(@Param("noteId") Long noteId,
                                   @Param("offset") long offset,
                                   @Param("pageSize") long pageSize);

    /**
     * 查询二级评论分页数据
     */
    List<CommentDO> selectChildPageList(@Param("parentId") Long parentId,
                                        @Param("offset") long offset,
                                        @Param("pageSize") long pageSize);

    /**
     * 查询热门评论
     */
    List<CommentDO> selectHeatComments(Long noteId);

    /**
     * 查询一级评论下子评论总数
     */
    Long selectChildCommentTotalById(Long commentId);

    int updateByPrimaryKeySelective(CommentDO record);

    int updateByPrimaryKey(CommentDO record);

    /**
     * 批量更新热度值
     */
    int batchUpdateHeatByCommentIds(@Param("commentIds") List<Long> commentIds,
                                    @Param("commentHeatBOS") List<CommentHeatBO> commentHeatBOS);

    /**
     * 更新一级评论的 first_reply_comment_id
     */
    int updateFirstReplyCommentIdByPrimaryKey(@Param("firstReplyCommentId") Long firstReplyCommentId,
                                              @Param("id") Long id);

}