package com.dreamers.services;

import com.dreamers.entities.Measurement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final int WALL_NUMBER_FOR_FLOOR_CALC = 4;

    public void getCalculation() {}

    void recalculate() {}

    void clearCalculation(Long facilityId) {}

    void fillMeasurement(Long facilityId) {
        Map<Long, List<Integer>> wallsWidth = new HashMap<>();
        roomService.getAllByFacilityId(facilityId)
                .forEach(room -> {
                    wallService.getWallsForRoom(room.getId())
                            .forEach(wall -> {
                                if(wall.isForFloorCalculation()) {
                                    if(wallsWidth.containsKey(wall.getRoom().getId())) {
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

        wallsWidth.forEach( (roomId, widthList) -> {
                    double square = calculateSquare(widthList);
                    if(widthList.size() == WALL_NUMBER_FOR_FLOOR_CALC) {
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

    }
}
