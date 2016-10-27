package aklal.com.zeitblickapp.webdata.models;

/**
 * Created by Aklal on 16.10.16.
 */

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "inventory_no"
})
public class Bild {

    @JsonProperty("inventory_no")
    private String inventoryNo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The inventoryNo
     */
    @JsonProperty("inventory_no")
    public String getInventoryNo() {
        return inventoryNo;
    }

    /**
     * @param inventoryNo The inventory_no
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
}
