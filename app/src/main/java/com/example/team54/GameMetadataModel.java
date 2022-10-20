package com.example.team54;
import com.google.gson.annotations.SerializedName;

public class GameMetadataModel {
    @SerializedName("total_pages")
    Integer total_pages;

    @SerializedName("current_page")
    Integer current_page;

    @SerializedName("next_page")
    Integer next_page;

    @SerializedName("per_page")
    Integer per_page;

    @SerializedName("total_count")
    Integer total_count;

    public GameMetadataModel(Integer total_pages, Integer current_page, Integer next_page,
                             Integer per_page, Integer total_count) {
        this.total_pages = total_pages;
        this.current_page = current_page;
        this.next_page = next_page;
        this.per_page = per_page;
        this.total_count = total_count;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public Integer getNext_page() {
        return next_page;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public Integer getTotal_count() {
        return total_count;
    }
}
