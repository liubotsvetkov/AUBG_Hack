package com.example.demo.Beans;

import com.example.demo.models.City;
import com.example.demo.models.ParkingSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingZoneDto {
    private String zoneIdent;
    private List<ParkingSlotDto> parkingSlots;
}
