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
        "display_date",
        "latest_date",
        "eventType",
        "location",
        "eventActor",
        "earliest_date"
})

public class Event implements Parcelable {

    @JsonProperty("display_date")
    private String displayDate;
    @JsonProperty("latest_date")
    private String latestDate;
    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("location")
    private String location;
    @JsonProperty("eventActor")
    private String eventActor;
    @JsonProperty("earliest_date")
    private String earliestDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Event() {
    }

    /**
     *
     * @param earliestDate
     * @param displayDate
     * @param latestDate
     * @param location
     * @param eventType
     * @param eventActor
     */
    public Event(String displayDate, String latestDate, String eventType, String location, String eventActor, String earliestDate) {
        this.displayDate = displayDate;
        this.latestDate = latestDate;
        this.eventType = eventType;
        this.location = location;
        this.eventActor = eventActor;
        this.earliestDate = earliestDate;
    }

    /**
     *
     * @return
     *     The displayDate
     */
    @JsonProperty("display_date")
    public String getDisplayDate() {
        return displayDate;
    }

    /**
     *
     * @param displayDate
     *     The display_date
     */
    @JsonProperty("display_date")
    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    /**
     *
     * @return
     *     The latestDate
     */
    @JsonProperty("latest_date")
    public String getLatestDate() {
        return latestDate;
    }

    /**
     *
     * @param latestDate
     *     The latest_date
     */
    @JsonProperty("latest_date")
    public void setLatestDate(String latestDate) {
        this.latestDate = latestDate;
    }

    /**
     *
     * @return
     *     The eventType
     */
    @JsonProperty("eventType")
    public String getEventType() {
        return eventType;
    }

    /**
     *
     * @param eventType
     *     The eventType
     */
    @JsonProperty("eventType")
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     *
     * @return
     *     The location
     */
    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     *     The location
     */
    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     *     The eventActor
     */
    @JsonProperty("eventActor")
    public String getEventActor() {
        return eventActor;
    }

    /**
     *
     * @param eventActor
     *     The eventActor
     */
    @JsonProperty("eventActor")
    public void setEventActor(String eventActor) {
        this.eventActor = eventActor;
    }

    /**
     *
     * @return
     *     The earliestDate
     */
    @JsonProperty("earliest_date")
    public String getEarliestDate() {
        return earliestDate;
    }

    /**
     *
     * @param earliestDate
     *     The earliest_date
     */
    @JsonProperty("earliest_date")
    public void setEarliestDate(String earliestDate) {
        this.earliestDate = earliestDate;
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
        dest.writeString(this.displayDate);
        dest.writeString(this.latestDate);
        dest.writeString(this.eventType);
        dest.writeString(this.location);
        dest.writeString(this.eventActor);
        dest.writeString(this.earliestDate);
    }

    protected Event(Parcel in) {
        this.displayDate = in.readString();
        this.latestDate = in.readString();
        this.eventType = in.readString();
        this.location = in.readString();
        this.eventActor = in.readString();
        this.earliestDate = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
