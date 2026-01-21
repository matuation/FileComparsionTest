package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Abilities {

    @JsonProperty("PrimaryAbility")
    private String PrimaryAbility;

    @JsonProperty("SecondaryAbility")
    private String SecondaryAbility;

    public String getPrimaryAbility() {
        return PrimaryAbility;
    }

    public String getSecondaryAbility() {
        return SecondaryAbility;
    }
}
