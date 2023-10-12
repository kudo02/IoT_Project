package IoT.example.IoT;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "history")
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float temperature;
    private Float humidity;
    private Float light;

    private Float bui;


    @Column(name = "timestamp")
    private String timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Float getLight() {
        return light;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setLight(Float light) {
        this.light = light;
    }

    public Float getBui() {
        return bui;
    }

    public void setBui(Float bui) {
        this.bui = bui;
    }

}
