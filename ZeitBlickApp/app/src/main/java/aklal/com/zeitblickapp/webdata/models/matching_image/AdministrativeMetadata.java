package aklal.com.zeitblickapp.webdata.models.matching_image;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aklal on 03.11.16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "infoLink",
        "license"
})
public class AdministrativeMetadata implements Parcelable {

    @JsonProperty("infoLink")
    private String infoLink;
    @JsonProperty("license")
    private String license;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public AdministrativeMetadata() {
    }

    /**
     *
     * @param infoLink
     * @param license
     */
    public AdministrativeMetadata(String infoLink, String license) {
        this.infoLink = infoLink;
        this.license = license;
    }

    /**
     *
     * @return
     *     The infoLink
     */
    @JsonProperty("infoLink")
    public String getInfoLink() {
        return infoLink;
    }

    /**
     *
     * @param infoLink
     *     The infoLink
     */
    @JsonProperty("infoLink")
    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    /**
     *
     * @return
     *     The license
     */
    @JsonProperty("license")
    public String getLicense() {
        return license;
    }

    /**
     *
     * @param license
     *     The license
     */
    @JsonProperty("license")
    public void setLicense(String license) {
        this.license = license;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }




    // ***************************** Parcelable *****************************

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.infoLink);
        dest.writeString(this.license);
    }

    protected AdministrativeMetadata(Parcel in) {
        this.infoLink = in.readString();
        this.license = in.readString();
    }

    public static final Creator<AdministrativeMetadata> CREATOR = new Creator<AdministrativeMetadata>() {
        @Override
        public AdministrativeMetadata createFromParcel(Parcel source) {
            return new AdministrativeMetadata(source);
        }

        @Override
        public AdministrativeMetadata[] newArray(int size) {
            return new AdministrativeMetadata[size];
        }
    };
}
