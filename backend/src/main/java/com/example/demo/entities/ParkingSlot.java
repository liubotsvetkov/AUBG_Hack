package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ParkingSlot", uniqueConstraints= {
        @UniqueConstraint(columnNames = {"parkingZoneId", "slotIdent"}),
        @UniqueConstraint(columnNames = {"latitude", "longitude"})
})
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false, length=255)
    private String slotIdent;
    @Column(nullable=false)
    private Date dateTimeUpdated;
    @Column(nullable=false)
    private int state;
    @Column(nullable=false)
    private double latitude;
    @Column(nullable=false)
    private double longitude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="parkingZoneId", nullable=false)
    private ParkingZone parkingZone;

    @PrePersist
    protected void onCreate() {
        dateTimeUpdated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        dateTimeUpdated = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0 && slotIdent.equals(that.slotIdent);
    }

    public ParkingSlot(String slotIdent, Date dateTimeUpdated, int state, double latitude, double longitude, ParkingZone parkingZone) {
        this.slotIdent = slotIdent;
        this.dateTimeUpdated = dateTimeUpdated;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.parkingZone = parkingZone;
    }
}
