package com.example.demo.controllers;

import com.example.demo.Beans.Position;
import com.example.demo.Beans.ParkingSlotDtoMap;
import com.example.demo.Beans.ParkingSlotRetDto;
import com.example.demo.models.City;
import com.example.demo.models.ParkingSlot;
import com.example.demo.models.ParkingZone;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ParkingDataController {

    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    @Autowired
    CityRepository cityRepository;

    @GetMapping("/viewAllParkingSlots")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<ParkingSlotDtoMap> viewAllParkingSlots() {
        List<ParkingSlot> parkingSlots = parkingSlotRepository.findAll();
        List<ParkingSlotDtoMap> parkingSlotDtoMapList = new ArrayList<ParkingSlotDtoMap>();
        for (ParkingSlot slot : parkingSlots) {
            ParkingSlotDtoMap parkingSlotDtoMap = new ParkingSlotDtoMap();
            Position position = new Position();
            position.setLat(slot.getLatitude());
            position.setLng(slot.getLongitude());
            parkingSlotDtoMap.setCoordinates(position);
            parkingSlotDtoMap.setState(slot.getState());
            parkingSlotDtoMapList.add(parkingSlotDtoMap);
        }
        return parkingSlotDtoMapList;
    }

    @GetMapping("/view")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<ParkingSlotRetDto> viewParkingSlotByCity(@RequestParam("city") String city) {
        List<City> cities = cityRepository.findByName(city);
        List<ParkingSlotRetDto> parkingSlotRetDtoList = new ArrayList<ParkingSlotRetDto>();
        for (City cityIter : cities) {
            for (ParkingZone zoneIter : cityIter.getParkingZones()) {
                for (ParkingSlot slotIter : zoneIter.getParkingSlots()) {
                    ParkingSlotRetDto parkingSlotRetDto = new ParkingSlotRetDto();
                    parkingSlotRetDto.setLatitude(slotIter.getLatitude());
                    parkingSlotRetDto.setLongitude(slotIter.getLongitude());
                    parkingSlotRetDto.setState(slotIter.getState());
                    parkingSlotRetDto.setZoneIdent(slotIter.getParkingZone().getZoneIdent());
                    parkingSlotRetDto.setDateTimeUpdated(slotIter.getDateTimeUpdated());
                    parkingSlotRetDtoList.add(parkingSlotRetDto);
                }
            }
        }
        return parkingSlotRetDtoList;
    }

    @GetMapping("/view/zone")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<ParkingSlotRetDto> viewParkingSlotByCityAndZone(@RequestParam("city") String city,
                                                         @RequestParam("zone") String zone) {
        List<City> cities = cityRepository.findByName(city);
        List<ParkingSlotRetDto> parkingSlotRetDtoList = new ArrayList<ParkingSlotRetDto>();
        for (City cityIter : cities) {
            for (ParkingZone zoneIter : cityIter.getParkingZones()) {
                if (zoneIter.getZoneIdent().equals(zone)) {
                    for (ParkingSlot slotIter : zoneIter.getParkingSlots()) {
                        ParkingSlotRetDto parkingSlotRetDto = new ParkingSlotRetDto();
                        parkingSlotRetDto.setLatitude(slotIter.getLatitude());
                        parkingSlotRetDto.setLongitude(slotIter.getLongitude());
                        parkingSlotRetDto.setState(slotIter.getState());
                        parkingSlotRetDto.setZoneIdent(slotIter.getParkingZone().getZoneIdent());
                        parkingSlotRetDto.setDateTimeUpdated(slotIter.getDateTimeUpdated());
                        parkingSlotRetDtoList.add(parkingSlotRetDto);
                    }
                }
            }
        }
        return parkingSlotRetDtoList;
    }


}
