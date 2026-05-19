package com.quanxiaoha.xiaohashu.search.biz.service;

import org.springframework.http.ResponseEntity;

/**
 * 拓展词典
 **/
public interface ExtDictService {

    /**
     * 获取热更新词典
     */
    ResponseEntity<String> getHotUpdateExtDict();

}
