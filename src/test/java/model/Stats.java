package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Stats {
    @JsonProperty("title")
    private String title;

    @JsonProperty("amount")
    private Integer amount;

    public String getTitle() {
        return title;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return Objects.equals(title, stats.title) && Objects.equals(amount, stats.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, amount);
    }

    public Stats(String title, Integer amount) {
        this.title = title;
        this.amount = amount;
    }

    public Stats() {
    }

    @Override
    public String toString() {
        return "Stats{" +
                "title='" + title + '\'' +
                ", amount=" + amount +
                '}';

    }
}