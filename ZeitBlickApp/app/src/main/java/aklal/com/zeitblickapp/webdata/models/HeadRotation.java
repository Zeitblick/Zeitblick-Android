package aklal.com.zeitblickapp.webdata.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oliver on 12.10.16.
 * <p>
 * Define POJO object to be converted in json when using retrofit calls
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pan",
        "tilt",
        "roll"
})
public class HeadRotation {

    @JsonProperty("pan")
    private Double pan;
    @JsonProperty("tilt")
    private Double tilt;
    @JsonProperty("roll")
    private Double roll;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The pan
     */
    @JsonProperty("pan")
    public Double getPan() {
        return pan;
    }

    /**
     * @param pan The pan
     */
    @JsonProperty("pan")
    public void setPan(Double pan) {
        this.pan = pan;
    }

    /**
     * @return The tilt
     */
    @JsonProperty("tilt")
    public Double getTilt() {
        return tilt;
    }

    /**
     * @param tilt The tilt
     */
    @JsonProperty("tilt")
    public void setTilt(Double tilt) {
        this.tilt = tilt;
    }

    /**
     * @return The roll
     */
    @JsonProperty("roll")
    public Double getRoll() {
        return roll;
    }

    /**
     * @param roll The roll
     */
    @JsonProperty("roll")
    public void setRoll(Double roll) {
        this.roll = roll;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}