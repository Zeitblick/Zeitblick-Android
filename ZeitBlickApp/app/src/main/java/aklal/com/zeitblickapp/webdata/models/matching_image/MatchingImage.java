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
 * Created by http://www.jsonschema2pojo.org/ on 03.11.16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "record_id",
        "mkg_metadata",
        "inventory_no"
})
public class MatchingImage implements Parcelable {

    @JsonProperty("record_id")
    private String recordId;
    @JsonProperty("mkg_metadata")
    private MkgMetadata mkgMetadata;
    @JsonProperty("inventory_no")
    private String inventoryNo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public MatchingImage() {
    }

    /**
     *
     * @param recordId
     * @param inventoryNo
     * @param mkgMetadata
     */
    public MatchingImage(String recordId, MkgMetadata mkgMetadata, String inventoryNo) {
        this.recordId = recordId;
        this.mkgMetadata = mkgMetadata;
        this.inventoryNo = inventoryNo;
    }

    /**
     *
     * @return
     *     The recordId
     */
    @JsonProperty("record_id")
    public String getRecordId() {
        return recordId;
    }

    /**
     *
     * @param recordId
     *     The record_id
     */
    @JsonProperty("record_id")
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    /**
     *
     * @return
     *     The mkgMetadata
     */
    @JsonProperty("mkg_metadata")
    public MkgMetadata getMkgMetadata() {
        return mkgMetadata;
    }

    /**
     *
     * @param mkgMetadata
     *     The mkg_metadata
     */
    @JsonProperty("mkg_metadata")
    public void setMkgMetadata(MkgMetadata mkgMetadata) {
        this.mkgMetadata = mkgMetadata;
    }

    /**
     *
     * @return
     *     The inventoryNo
     */
    @JsonProperty("inventory_no")
    public String getInventoryNo() {
        return inventoryNo;
    }

    /**
     *
     * @param inventoryNo
     *     The inventory_no
     */
    @JsonProperty("inventory_no")
    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
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
        dest.writeString(this.recordId);
        dest.writeParcelable(this.mkgMetadata, flags);
        dest.writeString(this.inventoryNo);
    }

    protected MatchingImage(Parcel in) {
        this.recordId = in.readString();
        this.mkgMetadata = in.readParcelable(MkgMetadata.class.getClassLoader());
        this.inventoryNo = in.readString();
    }

    public static final Creator<MatchingImage> CREATOR = new Creator<MatchingImage>() {
        @Override
        public MatchingImage createFromParcel(Parcel source) {
            return new MatchingImage(source);
        }

        @Override
        public MatchingImage[] newArray(int size) {
            return new MatchingImage[size];
        }
    };
}