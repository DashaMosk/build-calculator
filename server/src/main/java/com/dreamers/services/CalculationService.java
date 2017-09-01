package com.dreamers.services;

import com.dreamers.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    private void fillMeasurement(Long facilityId) {
        Map<Long, List<Integer>> wallsWidth = new HashMap<>();
        roomService.getAllByFacilityId(facilityId)
                .forEach(room -> {
                    wallService.getWallsForRoom(room.getId())
                            .forEach(wall -> {
                                if (wall.isForFloorCalculation()) {
                                    if (wallsWidth.containsKey(wall.getRoom().getId())) {
                                        wallsWidth.get(wall.getRoom().getId()).add(wall.getWidth());
                                    } else {
                                        wallsWidth.put(wall.getRoom().getId(), Collections.singletonList(wall.getWidth()));
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

        measurements.stream()
                .filter(measurement -> measurement.getWallId() != null)
                .forEach(mst -> {
                    if(mst.getDecorationId() == null && mst.getApertureId() == null) {
                        double[] m2 = calculateM2(mst);
                        facilityEquipmentService.findByTypeAndFacilityID(FacilityType.WALL, mst.getWallId())
                                .forEach(equipment -> {
                                    double m2Calc = equipment.getStuff().isClean() ? m2[0] : m2[1];
                                    resultService.save(new CalculationResult
                                            .Builder(mst, m2Calc, equipment.getStuff().isClean())
                                            .stuffName(equipment.getStuff().getName())
                                            .consumption(equipment.getStuff().getConsumption() * m2Calc)
                                            .measureName(equipment.getStuff().getPacking().getUnit().getShortName())
                                            .packConsumption(getPackConsumption(equipment, m2Calc))
                                            .packName(equipment.getStuff().getPacking().getName())
                                            .build());
                                });
                    }
                });

    }

    private  double getPackConsumption(FacilityEquipment equipment, double m2) {
        return equipment.getStuff().getConsumption() * m2/equipment.getStuff().getPacking().getQuantity();
    }

    //returns arr[0] - for clean, arr[1] - for rough
    private double[] calculateM2(Measurement mst) {
        Set<Measurement> apreturesSet = new HashSet<>();
        Set<Measurement> cleanDecSet = new HashSet<>();
        Set<Measurement> roughDecSet = new HashSet<>();
        resultService.findByWallId(mst.getWallId())
                .forEach(result -> {
                    if(result.getMeasurement().getApertureId() != null) {
                        apreturesSet.add(result.getMeasurement());
                    }
                    if(result.getMeasurement().getDecorationId() != null) {
                        if(result.isClean()) {
                            cleanDecSet.add(result.getMeasurement());
                        } else {
                            roughDecSet.add(result.getMeasurement());
                        }
                    }
                });
        double[] calcResult = new double[2];
        double aprM2 = calcSum(apreturesSet);
        calcResult[0] = mst.getWallsM2() -  aprM2 - calcSum(cleanDecSet);
        calcResult[1] = mst.getWallsM2() -  aprM2 - calcSum(roughDecSet);
        return calcResult;
    }


    private double calcSum(Set<Measurement> set) {
        double sum = 0.0;
        for (Measurement m : set) {
            sum += m.getWallsM2();
        }
        return sum;
    }

}
