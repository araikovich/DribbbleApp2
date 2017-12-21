package araikovichinc.dribbbleapp.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tigran on 19.12.2017.
 */

public class Images {
    @SerializedName("hidpi")
    private String hidpi;

    @SerializedName("normal")
    private String normal;

    @SerializedName("teaser")
    private String teaser;

    public String getHidpi() {
        return hidpi;
    }

    public void setHidpi(String hidpi) {
        this.hidpi = hidpi;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }
}
