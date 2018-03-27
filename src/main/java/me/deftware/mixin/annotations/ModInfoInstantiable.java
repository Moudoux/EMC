package me.deftware.mixin.annotations;

public class ModInfoInstantiable {
    private String name, website, author, minversion, version, main;
    private boolean updateLinkOverride;

    ModInfoInstantiable() {
    } // Only this package can instantiate.

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getMinversion() {
        return minversion;
    }

    public void setMinversion(final String minversion) {
        this.minversion = minversion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getMain() {
        return main;
    }

    public void setMain(final String main) {
        this.main = main;
    }

    public boolean isUpdateLinkOverride() {
        return updateLinkOverride;
    }

    public void setUpdateLinkOverride(final boolean updateLinkOverride) {
        this.updateLinkOverride = updateLinkOverride;
    }
}
