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
        "description",
        "administrativeMetadata",
        "title",
        "inventory_no",
        "record_id",
        "event"
})
public class MkgMetadata implements Parcelable {

    @JsonProperty("description")
    private String description;
    @JsonProperty("administrativeMetadata")
    private AdministrativeMetadata administrativeMetadata;
    @JsonProperty("title")
    private String title;
    @JsonProperty("inventory_no")
    private String inventoryNo;
    @JsonProperty("record_id")
    private String recordId;
    @JsonProperty("event")
    private Event event;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public MkgMetadata() {
    }

    /**
     *
     * @param recordId
     * @param administrativeMetadata
     * @param title
     * @param event
     * @param inventoryNo
     * @param description
     */
    public MkgMetadata(String description, AdministrativeMetadata administrativeMetadata, String title, String inventoryNo, String recordId, Event event) {
        this.description = description;
        this.administrativeMetadata = administrativeMetadata;
        this.title = title;
        this.inventoryNo = inventoryNo;
        this.recordId = recordId;
        this.event = event;
    }

    /**
     *
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     *     The administrativeMetadata
     */
    @JsonProperty("administrativeMetadata")
    public AdministrativeMetadata getAdministrativeMetadata() {
        return administrativeMetadata;
    }

    /**
     *
     * @param administrativeMetadata
     *     The administrativeMetadata
     */
    @JsonProperty("administrativeMetadata")
    public void setAdministrativeMetadata(AdministrativeMetadata administrativeMetadata) {
        this.administrativeMetadata = administrativeMetadata;
    }

    /**
     *
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
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
     *     The event
     */
    @JsonProperty("event")
    public Event getEvent() {
        return event;
    }

    /**
     *
     * @param event
     *     The event
     */
    @JsonProperty("event")
    public void setEvent(Event event) {
        this.event = event;
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
        dest.writeString(this.description);
        dest.writeParcelable(this.administrativeMetadata, flags);
        dest.writeString(this.title);
        dest.writeString(this.inventoryNo);
        dest.writeString(this.recordId);
        dest.writeParcelable(this.event, flags);
    }

    protected MkgMetadata(Parcel in) {
        this.description = in.readString();
        this.administrativeMetadata = in.readParcelable(AdministrativeMetadata.class.getClassLoader());
        this.title = in.readString();
        this.inventoryNo = in.readString();
        this.recordId = in.readString();
        this.event = in.readParcelable(Event.class.getClassLoader());
    }

    public static final Creator<MkgMetadata> CREATOR = new Creator<MkgMetadata>() {
        @Override
        public MkgMetadata createFromParcel(Parcel source) {
            return new MkgMetadata(source);
        }

        @Override
        public MkgMetadata[] newArray(int size) {
            return new MkgMetadata[size];
        }
    };
}
