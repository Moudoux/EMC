package me.deftware.client.framework.main;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class ModMeta {

    @SerializedName("name")
    private String name;

    @SerializedName("website")
    private String website;

    @SerializedName("author")
    private String author;

    @SerializedName("minVersion")
    private String minVersion;

    @SerializedName("version")
    private int version;

    @SerializedName("main")
    private String main;

    @SerializedName("updateLinkOverride")
    private boolean updateLinkOverride;

    @SerializedName("scheme")
    private int scheme;

}
