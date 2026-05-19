# 小哈希 API 文档

## 0. 系统架构与配置说明

### 0.1 网关配置
- **网关地址**: `http://localhost:8000`
- **路由规则**:
  | 服务名称 | 路由前缀 | 目标服务 |
  |----------|----------|----------|
  | 认证服务 | /auth/** | xiaohashu-auth |
  | 用户服务 | /user/** | xiaohashu-user |
  | 笔记服务 | /note/** | xiaohashu-note |
  | 用户关系服务 | /relation/** | xiaohashu-user-relation |
  | 评论服务 | /comment/** | xiaohashu-comment |
  | 文件服务 | /oss/** | xiaohashu-oss |
  | 搜索服务 | /search/** | xiaohashu-search |

### 0.2 Sa-Token 认证配置
- **Token 名称**: Authorization
- **Token 前缀**: Bearer
- **Token 有效期**: 30天（2592000秒）
- **Token 风格**: random-128
- **同一账号登录策略**: 允许同时登录，共用一个Token
- **白名单接口**:
  - /auth/login
  - /auth/verification/code/send
  - /note/channel/list
  - /note/discover/note/list
  - /note/profile/note/list
  - /note/note/detail
  - /note/note/isLikedAndCollectedData
  - /comment/comment/list
  - /comment/comment/child/list
  - /user/user/profile
  - /search/search/note

### 0.3 Feign 客户端调用
各服务间通过 Feign 客户端进行内部调用，服务名称配置如下：
- **用户服务**: xiaohashu-user
- **搜索服务**: xiaohashu-search
- **文件服务**: xiaohashu-oss
- **键值存储服务**: xiaohashu-kv
- **分布式ID生成服务**: xiaohashu-distributed-id-generator
- **计数服务**: xiaohashu-count

## 1. 认证服务 (Auth Service)

### 1.1 用户登录/注册
- **API路径**: `POST /login`
- **完整访问地址**: `http://localhost:8000/auth/login`
- **认证要求**: 不需要登录
- **功能描述**: 用户登录，支持密码或验证码两种方式
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | phone | String | 是 | 手机号 |
  | code | String | 否 | 验证码（验证码登录时必填） |
  | password | String | 否 | 密码（密码登录时必填） |
  | type | Integer | 是 | 登录类型：手机号验证码，或者是账号密码 |
- **响应参数**: String（token）

### 1.2 账号登出
- **API路径**: `POST /logout`
- **完整访问地址**: `http://localhost:8000/auth/logout`
- **认证要求**: 需要登录
- **功能描述**: 用户登出
- **请求参数**: 无
- **响应参数**: 无

### 1.3 修改密码
- **API路径**: `POST /password/update`
- **完整访问地址**: `http://localhost:8000/auth/password/update`
- **认证要求**: 需要登录
- **功能描述**: 修改用户密码
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | newPassword | String | 是 | 新密码 |
- **响应参数**: 无

### 1.4 发送短信验证码
- **API路径**: `POST /verification/code/send`
- **完整访问地址**: `http://localhost:8000/auth/verification/code/send`
- **认证要求**: 不需要登录（白名单接口）
- **功能描述**: 发送短信验证码
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | phone | String | 是 | 手机号 |
  | type | Integer | 是 | 验证码类型 |
- **响应参数**: 无

## 2. 用户服务 (User Service)

### 2.1 用户信息修改
- **API路径**: `POST /user/update`
- **完整访问地址**: `http://localhost:8000/user/user/update`
- **认证要求**: 需要登录
- **功能描述**: 修改用户信息
- **请求参数**: 表单数据（multipart/form-data）
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | userId | Long | 是 | 用户ID |
  | nickname | String | 否 | 昵称 |
  | avatar | MultipartFile | 否 | 头像文件 |
  | signature | String | 否 | 个性签名 |
  | gender | Integer | 否 | 性别 |
- **响应参数**: 无

### 2.2 获取用户主页信息
- **API路径**: `POST /user/profile`
- **完整访问地址**: `http://localhost:8000/user/user/profile`
- **认证要求**: 不需要登录（白名单接口）
- **功能描述**: 获取用户主页信息
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | userId | Long | 是 | 用户ID |
- **响应参数**: 用户主页信息对象

### 2.3 用户注册（内部服务调用）
- **API路径**: `POST /user/register`
- **完整访问地址**: `http://localhost:8000/user/user/register`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 用户注册
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | phone | String | 是 | 手机号 |
  | password | String | 是 | 密码 |
  | nickname | String | 否 | 昵称 |
- **响应参数**: Long（用户ID）

### 2.4 手机号查询用户信息（内部服务调用）
- **API路径**: `POST /user/findByPhone`
- **完整访问地址**: `http://localhost:8000/user/user/findByPhone`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 通过手机号查询用户信息
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | phone | String | 是 | 手机号 |
- **响应参数**: 用户信息对象

### 2.5 密码更新（内部服务调用）
- **API路径**: `POST /user/password/update`
- **完整访问地址**: `http://localhost:8000/user/user/password/update`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 更新用户密码
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | userId | Long | 是 | 用户ID |
  | password | String | 是 | 新密码 |
- **响应参数**: 无

### 2.6 查询用户信息（内部服务调用）
- **API路径**: `POST /user/findById`
- **完整访问地址**: `http://localhost:8000/user/user/findById`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 通过ID查询用户信息
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | userId | Long | 是 | 用户ID |
- **响应参数**: 用户信息对象

### 2.7 批量查询用户信息（内部服务调用）
- **API路径**: `POST /user/findByIds`
- **完整访问地址**: `http://localhost:8000/user/user/findByIds`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 批量查询用户信息
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | userIds | List<Long> | 是 | 用户ID列表 |
- **响应参数**: List<用户信息对象>

## 3. 笔记服务 (Note Service)

### 3.1 笔记发布
- **API路径**: `POST /note/publish`
- **完整访问地址**: `http://localhost:8000/note/note/publish`
- **认证要求**: 需要登录
- **功能描述**: 发布笔记
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | title | String | 是 | 笔记标题 |
  | content | String | 是 | 笔记内容 |
  | topicIds | List<Long> | 否 | 话题ID列表 |
  | channelIds | List<Long> | 否 | 频道ID列表 |
  | cover | String | 否 | 封面图片URL |
  | visible | Integer | 否 | 可见性（0：公开，1：仅自己可见） |
- **响应参数**: 无

### 3.2 笔记详情
- **API路径**: `POST /note/detail`
- **完整访问地址**: `http://localhost:8000/note/note/detail`
- **认证要求**: 不需要登录（白名单接口）
- **功能描述**: 获取笔记详情
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
- **响应参数**: 笔记详情对象

### 3.3 笔记修改
- **API路径**: `POST /note/update`
- **完整访问地址**: `http://localhost:8000/note/note/update`
- **认证要求**: 需要登录
- **功能描述**: 修改笔记
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
  | title | String | 是 | 笔记标题 |
  | content | String | 是 | 笔记内容 |
  | topicIds | List<Long> | 否 | 话题ID列表 |
  | channelIds | List<Long> | 否 | 频道ID列表 |
  | cover | String | 否 | 封面图片URL |
- **响应参数**: 无

### 3.4 删除笔记
- **API路径**: `POST /note/delete`
- **完整访问地址**: `http://localhost:8000/note/note/delete`
- **认证要求**: 需要登录
- **功能描述**: 删除笔记
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
- **响应参数**: 无

### 3.5 笔记仅对自己可见
- **API路径**: `POST /note/visible/onlyme`
- **完整访问地址**: `http://localhost:8000/note/note/visible/onlyme`
- **认证要求**: 需要登录
- **功能描述**: 设置笔记仅对自己可见
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
  | visible | Integer | 是 | 可见性（1：仅自己可见） |
- **响应参数**: 无

### 3.6 置顶/取消置顶笔记
- **API路径**: `POST /note/top`
- **完整访问地址**: `http://localhost:8000/note/note/top`
- **认证要求**: 需要登录
- **功能描述**: 置顶或取消置顶笔记
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
  | top | Integer | 是 | 是否置顶（1：置顶，0：取消置顶） |
- **响应参数**: 无

### 3.7 点赞笔记
- **API路径**: `POST /note/like`
- **完整访问地址**: `http://localhost:8000/note/note/like`
- **认证要求**: 需要登录
- **功能描述**: 点赞笔记
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
- **响应参数**: 无

### 3.8 取消点赞笔记
- **API路径**: `POST /note/unlike`
- **完整访问地址**: `http://localhost:8000/note/note/unlike`
- **认证要求**: 需要登录
- **功能描述**: 取消点赞笔记
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
- **响应参数**: 无

### 3.9 收藏笔记
- **API路径**: `POST /note/collect`
- **完整访问地址**: `http://localhost:8000/note/note/collect`
- **认证要求**: 需要登录
- **功能描述**: 收藏笔记
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
- **响应参数**: 无

### 3.10 取消收藏笔记
- **API路径**: `POST /note/uncollect`
- **完整访问地址**: `http://localhost:8000/note/note/uncollect`
- **认证要求**: 需要登录
- **功能描述**: 取消收藏笔记
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
- **响应参数**: 无

### 3.11 获取当前用户是否点赞、收藏数据
- **API路径**: `POST /note/isLikedAndCollectedData`
- **完整访问地址**: `http://localhost:8000/note/note/isLikedAndCollectedData`
- **认证要求**: 不需要登录（白名单接口）
- **功能描述**: 获取当前用户对笔记的点赞和收藏状态
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
- **响应参数**:
  | 参数名 | 类型 | 描述 |
  |--------|------|------|
  | isLiked | Boolean | 是否已点赞 |
  | isCollected | Boolean | 是否已收藏 |

### 3.12 获取发现页笔记列表
- **API路径**: `POST /discover/note/list`
- **完整访问地址**: `http://localhost:8000/note/discover/note/list`
- **认证要求**: 不需要登录（白名单接口）
- **功能描述**: 获取发现页笔记列表
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | pageNum | Integer | 是 | 页码 |
  | pageSize | Integer | 是 | 每页数量 |
- **响应参数**: 笔记列表

### 3.13 获取个人主页笔记列表
- **API路径**: `POST /profile/note/list`
- **完整访问地址**: `http://localhost:8000/note/profile/note/list`
- **认证要求**: 不需要登录（白名单接口）
- **功能描述**: 获取个人主页笔记列表
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | userId | Long | 是 | 用户ID |
  | pageNum | Integer | 是 | 页码 |
  | pageSize | Integer | 是 | 每页数量 |
- **响应参数**: 笔记列表

### 3.14 获取话题列表
- **API路径**: `POST /topic/list`
- **完整访问地址**: `http://localhost:8000/note/topic/list`
- **认证要求**: 不需要登录
- **功能描述**: 获取话题列表
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | pageNum | Integer | 是 | 页码 |
  | pageSize | Integer | 是 | 每页数量 |
- **响应参数**: 话题列表

### 3.15 获取频道列表
- **API路径**: `POST /channel/list`
- **完整访问地址**: `http://localhost:8000/note/channel/list`
- **认证要求**: 不需要登录（白名单接口）
- **功能描述**: 获取频道列表
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | pageNum | Integer | 是 | 页码 |
  | pageSize | Integer | 是 | 每页数量 |
- **响应参数**: 频道列表

## 4. 用户关系服务 (User Relation Service)

### 4.1 关注用户
- **API路径**: `POST /relation/follow`
- **完整访问地址**: `http://localhost:8000/relation/relation/follow`
- **认证要求**: 需要登录
- **功能描述**: 关注用户
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | followUserId | Long | 是 | 被关注用户ID |
- **响应参数**: 无

### 4.2 取消关注用户
- **API路径**: `POST /relation/unfollow`
- **完整访问地址**: `http://localhost:8000/relation/relation/unfollow`
- **认证要求**: 需要登录
- **功能描述**: 取消关注用户
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | followUserId | Long | 是 | 被关注用户ID |
- **响应参数**: 无

### 4.3 获取关注列表
- **API路径**: `POST /relation/following/list`
- **完整访问地址**: `http://localhost:8000/relation/relation/following/list`
- **认证要求**: 需要登录
- **功能描述**: 获取关注列表
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | userId | Long | 是 | 用户ID |
  | pageNum | Integer | 是 | 页码 |
  | pageSize | Integer | 是 | 每页数量 |
- **响应参数**: 用户列表

### 4.4 获取粉丝列表
- **API路径**: `POST /relation/fans/list`
- **完整访问地址**: `http://localhost:8000/relation/relation/fans/list`
- **认证要求**: 需要登录
- **功能描述**: 获取粉丝列表
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | userId | Long | 是 | 用户ID |
  | pageNum | Integer | 是 | 页码 |
  | pageSize | Integer | 是 | 每页数量 |
- **响应参数**: 用户列表

## 5. 评论服务 (Comment Service)

### 5.1 发布评论
- **API路径**: `POST /comment/publish`
- **完整访问地址**: `http://localhost:8000/comment/comment/publish`
- **认证要求**: 需要登录
- **功能描述**: 发布评论
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
  | content | String | 是 | 评论内容 |
  | parentId | Long | 否 | 父评论ID（用于二级评论） |
- **响应参数**: 无

### 5.2 评论分页查询
- **API路径**: `POST /comment/list`
- **完整访问地址**: `http://localhost:8000/comment/comment/list`
- **认证要求**: 不需要登录（白名单接口）
- **功能描述**: 获取评论分页列表
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
  | pageNum | Integer | 是 | 页码 |
  | pageSize | Integer | 是 | 每页数量 |
- **响应参数**: 评论分页列表

### 5.3 二级评论分页查询
- **API路径**: `POST /comment/child/list`
- **完整访问地址**: `http://localhost:8000/comment/comment/child/list`
- **认证要求**: 不需要登录（白名单接口）
- **功能描述**: 获取二级评论分页列表
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | parentId | Long | 是 | 父评论ID |
  | pageNum | Integer | 是 | 页码 |
  | pageSize | Integer | 是 | 每页数量 |
- **响应参数**: 二级评论分页列表

### 5.4 评论点赞
- **API路径**: `POST /comment/like`
- **完整访问地址**: `http://localhost:8000/comment/comment/like`
- **认证要求**: 需要登录
- **功能描述**: 点赞评论
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | commentId | Long | 是 | 评论ID |
- **响应参数**: 无

### 5.5 评论取消点赞
- **API路径**: `POST /comment/unlike`
- **完整访问地址**: `http://localhost:8000/comment/comment/unlike`
- **认证要求**: 需要登录
- **功能描述**: 取消点赞评论
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | commentId | Long | 是 | 评论ID |
- **响应参数**: 无

### 5.6 删除评论
- **API路径**: `POST /comment/delete`
- **完整访问地址**: `http://localhost:8000/comment/comment/delete`
- **认证要求**: 需要登录
- **功能描述**: 删除评论
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | commentId | Long | 是 | 评论ID |
- **响应参数**: 无

## 6. 搜索服务 (Search Service)

### 6.1 搜索用户
- **API路径**: `POST /search/user`
- **完整访问地址**: `http://localhost:8000/search/search/user`
- **认证要求**: 需要登录
- **功能描述**: 搜索用户
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | keyword | String | 是 | 搜索关键词 |
  | pageNum | Integer | 是 | 页码 |
  | pageSize | Integer | 是 | 每页数量 |
- **响应参数**: 用户列表

### 6.2 搜索笔记
- **API路径**: `POST /search/note`
- **完整访问地址**: `http://localhost:8000/search/search/note`
- **认证要求**: 不需要登录（白名单接口）
- **功能描述**: 搜索笔记
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | keyword | String | 是 | 搜索关键词 |
  | pageNum | Integer | 是 | 页码 |
  | pageSize | Integer | 是 | 每页数量 |
- **响应参数**: 笔记列表

### 6.3 重建用户搜索文档（内部服务调用）
- **API路径**: `POST /search/user/document/rebuild`
- **完整访问地址**: `http://localhost:8000/search/search/user/document/rebuild`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 重建用户搜索文档
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | userId | Long | 是 | 用户ID |
- **响应参数**: 无

### 6.4 重建笔记搜索文档（内部服务调用）
- **API路径**: `POST /search/note/document/rebuild`
- **完整访问地址**: `http://localhost:8000/search/search/note/document/rebuild`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 重建笔记搜索文档
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
- **响应参数**: 无

### 6.5 获取扩展词典
- **API路径**: `GET /search/ext/dict`
- **完整访问地址**: `http://localhost:8000/search/search/ext/dict`
- **认证要求**: 不需要登录
- **功能描述**: 获取扩展词典
- **请求参数**: 无
- **响应参数**: 扩展词典内容

## 7. 文件服务 (OSS Service)

### 7.1 文件上传
- **API路径**: `POST /file/upload`
- **完整访问地址**: `http://localhost:8000/oss/file/upload`
- **认证要求**: 需要登录
- **功能描述**: 文件上传
- **请求参数**: 表单数据（multipart/form-data）
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | file | MultipartFile | 是 | 上传文件 |
- **响应参数**: String（文件URL）

## 8. 键值存储服务 (KV Service)

### 8.1 批量添加评论内容
- **API路径**: `POST /kv/comment/content/batchAdd`
- **完整访问地址**: `http://localhost:8000/kv/kv/comment/content/batchAdd`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 批量添加评论内容
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | commentContents | List<CommentContentVO> | 是 | 评论内容列表 |
- **响应参数**: 无

### 8.2 批量查询评论内容
- **API路径**: `POST /kv/comment/content/batchFind`
- **完整访问地址**: `http://localhost:8000/kv/kv/comment/content/batchFind`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 批量查询评论内容
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | commentIds | List<Long> | 是 | 评论ID列表 |
- **响应参数**: List<CommentContentVO>

### 8.3 删除评论内容
- **API路径**: `POST /kv/comment/content/delete`
- **完整访问地址**: `http://localhost:8000/kv/kv/comment/content/delete`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 删除评论内容
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | commentId | Long | 是 | 评论ID |
- **响应参数**: 无

### 8.4 添加笔记内容
- **API路径**: `POST /kv/note/content/add`
- **完整访问地址**: `http://localhost:8000/kv/kv/note/content/add`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 添加笔记内容
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
  | content | String | 是 | 笔记内容 |
- **响应参数**: 无

### 8.5 查询笔记内容
- **API路径**: `POST /kv/note/content/find`
- **完整访问地址**: `http://localhost:8000/kv/kv/note/content/find`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 查询笔记内容
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
- **响应参数**: String（笔记内容）

### 8.6 删除笔记内容
- **API路径**: `POST /kv/note/content/delete`
- **完整访问地址**: `http://localhost:8000/kv/kv/note/content/delete`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 删除笔记内容
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
- **响应参数**: 无

## 9. 计数服务 (Count Service)

### 9.1 获取用户计数数据
- **API路径**: `POST /count/user/data`
- **完整访问地址**: `http://localhost:8000/count/count/user/data`
- **认证要求**: 需要登录
- **功能描述**: 获取用户计数数据
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | userId | Long | 是 | 用户ID |
- **响应参数**: 用户计数数据对象

### 9.2 获取笔记计数数据
- **API路径**: `POST /count/note/data`
- **完整访问地址**: `http://localhost:8000/count/count/note/data`
- **认证要求**: 需要登录
- **功能描述**: 获取笔记计数数据
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | noteId | Long | 是 | 笔记ID |
- **响应参数**: 笔记计数数据对象

## 10. 分布式ID生成服务 (Distributed ID Generator Service)

### 10.1 获取分段ID
- **API路径**: `GET /id/segment/get/{key}`
- **完整访问地址**: `http://localhost:8000/id/id/segment/get/{key}`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 获取分段ID
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | key | String | 是 | 业务标识（路径参数） |
- **响应参数**: String（分段ID）

### 10.2 获取雪花ID
- **API路径**: `GET /id/snowflake/get/{key}`
- **完整访问地址**: `http://localhost:8000/id/id/snowflake/get/{key}`
- **认证要求**: 内部服务调用，无需外部认证
- **功能描述**: 获取雪花ID
- **请求参数**:
  | 参数名 | 类型 | 必填 | 描述 |
  |--------|------|------|------|
  | key | String | 是 | 业务标识（路径参数） |
- **响应参数**: String（雪花ID）

## 11. 响应格式

所有API的响应格式统一为：

```json
{
  "code": 0,
  "message": "success",
  "data": "响应数据"
}
```

| 字段名 | 类型 | 描述 |
|--------|------|------|
| code | Integer | 响应码（0：成功，其他：失败） |
| message | String | 响应消息 |
| data | Object | 响应数据 |

## 12. 错误码说明

| 错误码 | 描述 |
|--------|------|
| 0 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |
| 501 | 方法未实现 |
| 502 | 网关错误 |
| 503 | 服务不可用 |
| 504 | 网关超时 |

## 13. 认证说明

### 13.1 Token 传递方式
所有需要认证的API请求，必须在请求头中携带Token，格式如下：
```
Authorization: Bearer {token}
```

### 13.2 登录状态验证
- 已登录用户：请求头中携带有效的Token
- 未登录用户：访问白名单接口或登录接口

### 13.3 权限控制
- 目前系统主要基于登录状态进行控制
- 部分敏感操作需要额外的权限验证

## 14. 内部服务调用

### 14.1 Feign 调用配置
- 服务间通过 Spring Cloud Feign 进行调用
- 每个服务都有对应的 API 模块，定义了对外提供的 Feign 接口
- 服务名称通过 ApiConstants.SERVICE_NAME 统一管理

### 14.2 调用示例
```java
@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface UserFeignApi {
    // 接口定义
}
```

## 15. 网关路由说明

### 15.1 路由转发规则
- 所有外部请求通过网关进行转发
- 网关根据请求路径前缀将请求转发到对应的服务
- 转发时会去掉路径前缀（StripPrefix=1）

### 15.2 跨域配置
- 网关已配置跨域支持
- 允许所有来源的请求访问

## 16. 服务发现与注册

- 采用 Nacos 作为服务注册中心
- 每个服务启动时自动注册到 Nacos
- 网关通过服务名从 Nacos 获取服务实例信息