package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Chicken {

    private Abilities abilities;
    private List<Stats> stats;

    @JsonProperty("name")
    private String name;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("born")
    private String born;

    @JsonProperty("haveEgg")
    private boolean haveEgg;

    @JsonProperty("energy")
    private String energy;

    public Abilities getAbilities() {
        return abilities;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getBorn() {
        return born;
    }

    public boolean isHaveEgg() {
        return haveEgg;
    }

    public String getEnergy() {
        return energy;
    }

}


//        "name": "Anivia",
//        "age": 1000,
//        "born": "Frelyord",
//        "haveEgg": true,
//        "abilities":
//        {
//        "PrimaryAbility": "Iceball",
//        "SecondaryAbility" : "Ice Spike"
//        },
//        "stats": [
//        {
//        "title": "Ability Power",
//        "amount": 865
//        },
//        {
//        "title": "Cooldown reduction",
//        "amount": 25
//        }
//        ],
//        "energy": null
