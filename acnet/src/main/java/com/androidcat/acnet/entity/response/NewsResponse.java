package com.androidcat.acnet.entity.response;

import com.androidcat.acnet.entity.NewsContent;

/**
 * Created by Administrator on 2015-12-16.
 */
public class NewsResponse extends BaseResponse {
    private NewsContent content;

    public NewsContent getContent() {
        return content;
    }

    public void setContent(NewsContent content) {
        this.content = content;
    }
}