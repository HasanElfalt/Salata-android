package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

public class PaginatedResponse {
    @SerializedName("current_page")
    protected int currentPage;
    protected Links links;

    public static class Links{
        @SerializedName("first_page_url")
        private String firstPageUrl;
        private int from;
        @SerializedName("last_page_url")
        private String lastPageUrl;
        @SerializedName("next_page_url")
        private String nextPageUrl;
        private String path;
        private int per_page;
        @SerializedName("prev_page_url")
        private String prevPageUrl;
        private int to;
        private int total;
    }
}