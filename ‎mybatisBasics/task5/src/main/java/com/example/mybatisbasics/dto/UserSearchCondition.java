package com.example.mybatisbasics.dto;

import java.util.List;

public class UserSearchCondition {
    private String name;
    private String email;
    private List<Long> ids;
    private String sortBy;
    private Integer limit;
    private Integer offset;

    // デフォルトコンストラクタ
    public UserSearchCondition() {
    }

    // Getter
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Long> getIds() {
        return ids;
    }

    public String getSortBy() {
        return sortBy;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
