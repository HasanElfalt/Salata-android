package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

public class PaginatedResponse {
    @SerializedName("current_page")
    protected int currentPage;

    public Links getLinks() {
        return links;
    }

    protected Links links = new Links();

    public class Links{
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

        public String getFirstPageUrl() {
            return firstPageUrl;
        }

        public void setFirstPageUrl(String firstPageUrl) {
            this.firstPageUrl = firstPageUrl;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public String getLastPageUrl() {
            return lastPageUrl;
        }

        public void setLastPageUrl(String lastPageUrl) {
            this.lastPageUrl = lastPageUrl;
        }

        public String getNextPageUrl() {
            return nextPageUrl;
        }

        public void setNextPageUrl(String nextPageUrl) {
            this.nextPageUrl = nextPageUrl;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public String getPrevPageUrl() {
            return prevPageUrl;
        }

        public void setPrevPageUrl(String prevPageUrl) {
            this.prevPageUrl = prevPageUrl;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}