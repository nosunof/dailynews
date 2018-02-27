package com.dailynews.android.entity;

import java.util.List;

/**
 * Created by sunfeiswag on 2018/2/22.
 */

public class ThemesEntity {

    /**
     * 主题列表返回的Json格式数据
     * 链接：http://news-at.zhihu.com/api/4/themes
     * {
     "limit": 1000,
     "subscribed": [],
     "others": [
     {
     "color": 15007,
     "thumbnail": "http://pic3.zhimg.com/0e71e90fd6be47630399d63c58beebfc.jpg",
     "description": "了解自己和别人，了解彼此的欲望和局限。",
     "id": 13,
     "name": "日常心理学"
     },...]
     }
     */

    private int limit;
    private List<?> subscribed;
    private List<OthersEntity> others;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<?> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(List<?> subscribed) {
        this.subscribed = subscribed;
    }

    public List<OthersEntity> getOthers() {
        return others;
    }

    public void setOthers(List<OthersEntity> others) {
        this.others = others;
    }
}
