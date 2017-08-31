package com.dreamers.services;

import com.dreamers.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculationService {

    @Autowired
    private RoomService roomService;
    @Autowired
    private WallService wallService;
    @Autowired
    private ApertureService apertureService;
    @Autowired
    private DecorationService decorationService;
    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private FacilityEquipmentService facilityEquipmentService;
    @Autowired
    private CalculationResultService resultService;

    private static final int WALL_NUMBER_FOR_FLOOR_CALC = 4;

    public void doCalculation(Long facilityId) {
        fillMeasurement(facilityId);
    }

    void recalculate() {
    }

    void clearCalculation(Long facilityId) {
    }

    void fillMeasurement(Long facilityId) {
        Map<Long, List<Integer>> wallsWidth = new HashMap<>();
        roomService.getAllByFacilityId(facilityId)
                .forEach(room -> {
                    wallService.getWallsForRoom(room.getId())
                            .forEach(wall -> {
                                if (wall.isForFloorCalculation()) {
                                    if (wallsWidth.containsKey(wall.getRoom().getId())) {
                                        wallsWidth.get(wall.getRoom().getId()).add(wall.getWidth());
                                    } else {
                                        wallsWidth.put(wall.getRoom().getId(), Arrays.asList(wall.getWidth()));
                                    }
                                }

                                apertureService.getAperturesForWall(wall.getId())
                                        .forEach(aperture -> {
                                            measurementService.save(new Measurement
                                                    .Builder(facilityId, aperture.getWall().getRoom().getId())
                                                    .wallId(aperture.getWall().getId())
                                                    .apertureId(aperture.getId())
                                                    .wallsM2(aperture.getMeasurement())
                                                    .build());

                                        });

                                decorationService.getDecorationsForWall(wall.getId())
                                        .forEach(decoration -> {
                                            measurementService.save(new Measurement
                                                    .Builder(facilityId, decoration.getWall().getRoom().getId())
                                                    .wallId(decoration.getWall().getId())
                                                    .decorationId(decoration.getId())
                                                    .wallsM2(decoration.getMeasurement())
                                                    .build());

                                        });

                                measurementService.save(new Measurement
                                        .Builder(facilityId, wall.getRoom().getId())
                                        .wallId(wall.getId())
                                        .wallsM2(wall.getMeasurement())
                                        .build());
                            });
                });

        wallsWidth.forEach((roomId, widthList) -> {
                    double square = calculateSquare(widthList);
                    if (widthList.size() == WALL_NUMBER_FOR_FLOOR_CALC) {
                        measurementService.save(new Measurement
                                .Builder(facilityId, roomId)
                                .floorM2(square)
                                .build());
                        measurementService.save(new Measurement
                                .Builder(facilityId, roomId)
                                .ceilingM2(square)
                                .build());
                    }
                }

        );
    }

    private double calculateSquare(List<Integer> widthList) {
        double perimeter = 0;
        for (Integer i : widthList) {
            perimeter += (double)i;
        }
        double sperimeter = perimeter/2.0;
        double product = 1;
        for (Integer i : widthList) {
            product = (sperimeter - i) * product;
        }

        return Math.sqrt(product)/100;
    }

    void fillCalculation(long facilityId) {

        List<Measurement> measurements = measurementService.getAllByFacilityId(facilityId);

        measurements.stream()
                .filter(measurement -> measurement.getApertureId() != null)
                .forEach(measurement -> {
                    resultService.save(new CalculationResult
                            .Builder(measurement, measurement.getWallsM2(), false)
                            .stuffName(apertureService.getNameById(measurement.getApertureId()))
                            .build());
                });

        measurements.stream()
                .filter(measurement -> measurement.getDecorationId() != null)
                .forEach(mst -> {
                    facilityEquipmentService.findByTypeAndFacilityID(FacilityType.DECORATION, mst.getDecorationId())
                            .forEach(equipment -> {
                                resultService.save(new CalculationResult
                                        .Builder(mst, mst.getWallsM2(), equipment.getStuff().isClean())
                                        .stuffName(equipment.getStuff().getName())
                                        .consumption(equipment.getStuff().getConsumption() * mst.getWallsM2())
                                        .measureName(equipment.getStuff().getPacking().getUnit().getShortName())
                                        .packConsumption(getPackConsumption(equipment, mst.getWallsM2()))
                                        .packName(equipment.getStuff().getPacking().getName())
                                        .build());
                            });
                });

    }

    private  double getPackConsumption(FacilityEquipment equipment, double m2) {
        return equipment.getStuff().getConsumption() * m2/equipment.getStuff().getPacking().getQuantity();
    }

}
