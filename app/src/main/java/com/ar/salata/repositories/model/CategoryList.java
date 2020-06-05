package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryList {
    @SerializedName("cats")
    private List<Category> categoryList;

    public CategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
