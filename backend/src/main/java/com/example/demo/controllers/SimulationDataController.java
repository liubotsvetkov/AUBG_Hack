package com.example.demo.controllers;

import com.example.demo.Beans.*;
import com.example.demo.models.City;
import com.example.demo.models.ParkingSlot;
import com.example.demo.models.ParkingZone;
import com.example.demo.models.Person;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.ParkingSlotRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class SimulationDataController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @PostMapping("/simulationDataInsert")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public String insertData(@RequestBody List<CityDto> citiesDto) {
        for (CityDto cityDto : citiesDto) {
            City city = new City();
            city.setName(cityDto.getName());
            ParkingZone parkingZone = new ParkingZone();
            for (ParkingZoneDto parkingZoneDto : cityDto.getParkingZones()) {
                parkingZone.setZoneIdent(parkingZoneDto.getZoneIdent());
                ParkingSlot parkingSlot = new ParkingSlot();
                for (ParkingSlotDto parkingSlotDto : parkingZoneDto.getParkingSlots()) {
                    parkingSlot.setLatitude(parkingSlotDto.getLatitude());
                    parkingSlot.setLongitude(parkingSlotDto.getLongitude());
                    parkingSlot.setState(parkingSlotDto.getState());
                    parkingSlot.setSlotIdent(parkingSlotDto.getSlotIdent());
                    parkingSlot.setDateTimeUpdated(parkingSlotDto.getDateTimeUpdated());
                    parkingSlot.setParkingZone(parkingZone);
                }
                if (parkingZone.getParkingSlots() == null) {
                    parkingZone.setParkingSlots(new ArrayList<ParkingSlot>());
                }
                parkingZone.getParkingSlots().add(parkingSlot);
                parkingZone.setCity(city);
            }
            if (city.getParkingZones() == null) {
                city.setParkingZones(new ArrayList<ParkingZone>());
            }
            city.getParkingZones().add(parkingZone);
            cityRepository.save(city);
        }
        return "success";
    }

    @PostMapping("/simulationDataUpdate")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public String updateData(@RequestBody List<SimUpdateData> updateData) {
        for (SimUpdateData data : updateData) {
            List<ParkingSlot> parkingSlots =
                    parkingSlotRepository.findBySlotIdentAndParkingZoneZoneIdent(data.getSlot_id(), data.getParking());
            if (!parkingSlots.isEmpty()) {
                parkingSlots.get(0).setState(data.getState());
                parkingSlots.get(0).setDateTimeUpdated(new Date());
            }
            else {
                throw new IllegalArgumentException();
            }
            parkingSlotRepository.save(parkingSlots.get(0));
        }
        return "success";
    }
}
