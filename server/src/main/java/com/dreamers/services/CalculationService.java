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
        clearCalculation(facilityId);
        fillMeasurement(facilityId);
        fillCalculation(facilityId);
    }

    private void clearCalculation(Long facilityId) {
        resultService.findByFacilityId(facilityId).forEach(result -> {
            resultService.delete(result.getId());
        });

        measurementService.getAllByFacilityId(facilityId).forEach(measurement -> {
            measurementService.delete(measurement.getId());
        });

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
                                        wallsWidth.put(wall.getRoom().getId(), new ArrayList<Integer>(Arrays.asList(wall.getWidth())));
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

        return Math.sqrt(product/10000)/100;
    }

    private void fillCalculation(long facilityId) {

        List<Measurement> measurements = measurementService.getAllByFacilityId(facilityId);

        // fill info for apertures
        measurements.stream()
                .filter(measurement -> measurement.getApertureId() != null)
                .forEach(measurement -> {
                    resultService.save(new CalculationResult
                            .Builder(measurement, measurement.getWallsM2(), false)
                            .stuffName(apertureService.getNameById(measurement.getApertureId()))
                            .build());
                });

        // fill info for decorations
        measurements.stream()
                .filter(measurement -> measurement.getDecorationId() != null)
                .forEach(mst -> {
                    facilityEquipmentService.findByTypeAndFacilityID(FacilityType.DECORATION, mst.getDecorationId())
                            .forEach(equipment -> {
                                resultService.save(new CalculationResult
                                        .Builder(mst, mst.getWallsM2(), equipment.getStuff().isClean())
                                        .stuffId(equipment.getStuff().getId())
                                        .stuffName(equipment.getStuff().getName())
                                        .consumption(equipment.getStuff().getConsumption())
                                        .measureName(equipment.getStuff().getPacking().getUnit().getShortName())
                                        .packConsumption(equipment.getStuff().getPacking().getQuantity())
                                        .packName(equipment.getStuff().getPacking().getName())
                                        .build());
                            });
                });

        // fill info for walls
        measurements.stream()
                .filter(measurement -> measurement.getWallId() != null)
                .forEach(mst -> {
                    if(mst.getDecorationId() == null && mst.getApertureId() == null) {
                        double[] m2 = calculateM2(mst);
                        List<FacilityEquipment> cleanEquipments = facilityEquipmentService
                                .findByTypeFacilityIdPartTypeAndClean(FacilityType.WALL, mst.getWallId(), true, PartType.WALL);
                        if(cleanEquipments.size() == 0) {
                            cleanEquipments = facilityEquipmentService
                                    .findByTypeFacilityIdPartTypeAndClean(FacilityType.ROOM, mst.getRoomId(), true, PartType.WALL);
                        }
                        if(cleanEquipments.size() == 0) {
                            cleanEquipments = facilityEquipmentService
                                    .findByTypeFacilityIdPartTypeAndClean(FacilityType.FACILITY, mst.getFacilityId(), true, PartType.WALL);
                        }
                        cleanEquipments.forEach(equipment -> {
                                    resultService.save(new CalculationResult
                                            .Builder(mst, m2[0], true)
                                            .stuffId(equipment.getStuff().getId())
                                            .stuffName(equipment.getStuff().getName())
                                            .consumption(equipment.getStuff().getConsumption())
                                            .measureName(equipment.getStuff().getPacking().getUnit().getShortName())
                                            .packConsumption(equipment.getStuff().getPacking().getQuantity())
                                            .packName(equipment.getStuff().getPacking().getName())
                                            .build());
                                });

                        List<FacilityEquipment> roughEquipments = facilityEquipmentService
                                .findByTypeFacilityIdPartTypeAndClean(FacilityType.WALL, mst.getWallId(), false, PartType.WALL);
                        if(roughEquipments.size() == 0) {
                            roughEquipments = facilityEquipmentService
                                    .findByTypeFacilityIdPartTypeAndClean(FacilityType.ROOM, mst.getRoomId(), false, PartType.WALL);

                        }
                        if(roughEquipments.size() == 0) {
                            roughEquipments = facilityEquipmentService
                                    .findByTypeFacilityIdPartTypeAndClean(FacilityType.FACILITY, mst.getFacilityId(), false, PartType.WALL);

                        }
                        roughEquipments.forEach(equipment -> {
                            resultService.save(new CalculationResult
                                    .Builder(mst, m2[1], false)
                                    .stuffId(equipment.getStuff().getId())
                                    .stuffName(equipment.getStuff().getName())
                                    .consumption(equipment.getStuff().getConsumption())
                                    .measureName(equipment.getStuff().getPacking().getUnit().getShortName())
                                    .packConsumption(equipment.getStuff().getPacking().getQuantity())
                                    .packName(equipment.getStuff().getPacking().getName())
                                    .build());
                        });


                    }
                });

        // fill info for ceiling
        measurements.stream()
                .filter(mst -> mst.getCeilingM2() > 0.0)
                .forEach(mst -> {
                    List<FacilityEquipment> cleanEquipments = facilityEquipmentService
                            .findByTypeFacilityIdPartTypeAndClean(FacilityType.ROOM, mst.getRoomId(), true, PartType.CEILING);
                    if(cleanEquipments.size() == 0) {
                        cleanEquipments = facilityEquipmentService.findByTypeFacilityIdPartTypeAndClean(FacilityType.FACILITY
                        , mst.getFacilityId(), true, PartType.CEILING);
                    }
                    cleanEquipments.forEach(equipment -> {
                        resultService.save(new CalculationResult
                                .Builder(mst, mst.getCeilingM2(), true)
                                .stuffId(equipment.getStuff().getId())
                                .stuffName(equipment.getStuff().getName())
                                .consumption(equipment.getStuff().getConsumption())
                                .measureName(equipment.getStuff().getPacking().getUnit().getShortName())
                                .packConsumption(equipment.getStuff().getPacking().getQuantity())
                                .packName(equipment.getStuff().getPacking().getName())
                                .build());
                    });

                    List<FacilityEquipment> roughEquipments = facilityEquipmentService
                            .findByTypeFacilityIdPartTypeAndClean(FacilityType.ROOM, mst.getRoomId(), false, PartType.CEILING);
                    if(roughEquipments.size() == 0) {
                        roughEquipments = facilityEquipmentService.findByTypeFacilityIdPartTypeAndClean(FacilityType.FACILITY
                                , mst.getFacilityId(), false, PartType.CEILING);
                    }
                    roughEquipments.forEach(equipment -> {
                        resultService.save(new CalculationResult
                                .Builder(mst, mst.getCeilingM2(), false)
                                .stuffId(equipment.getStuff().getId())
                                .stuffName(equipment.getStuff().getName())
                                .consumption(equipment.getStuff().getConsumption())
                                .measureName(equipment.getStuff().getPacking().getUnit().getShortName())
                                .packConsumption(equipment.getStuff().getPacking().getQuantity())
                                .packName(equipment.getStuff().getPacking().getName())
                                .build());
                    });
                });

        // fill info for floor
        measurements.stream()
                .filter(mst -> mst.getFloorM2() > 0.0)
                .forEach(mst -> {
                    List<FacilityEquipment> cleanEquipments = facilityEquipmentService
                            .findByTypeFacilityIdPartTypeAndClean(FacilityType.ROOM, mst.getRoomId(), true, PartType.FLOOR);
                    if(cleanEquipments.size() == 0) {
                        cleanEquipments = facilityEquipmentService.findByTypeFacilityIdPartTypeAndClean(FacilityType.FACILITY
                                , mst.getFacilityId(), true, PartType.FLOOR);
                    }
                    cleanEquipments.forEach(equipment -> {
                        resultService.save(new CalculationResult
                                .Builder(mst, mst.getFloorM2(), true)
                                .stuffId(equipment.getStuff().getId())
                                .stuffName(equipment.getStuff().getName())
                                .consumption(equipment.getStuff().getConsumption())
                                .measureName(equipment.getStuff().getPacking().getUnit().getShortName())
                                .packConsumption(equipment.getStuff().getPacking().getQuantity())
                                .packName(equipment.getStuff().getPacking().getName())
                                .build());
                    });

                    List<FacilityEquipment> roughEquipments = facilityEquipmentService
                            .findByTypeFacilityIdPartTypeAndClean(FacilityType.ROOM, mst.getRoomId(), false, PartType.FLOOR);
                    if(roughEquipments.size() == 0) {
                        roughEquipments = facilityEquipmentService.findByTypeFacilityIdPartTypeAndClean(FacilityType.FACILITY
                                , mst.getFacilityId(), false, PartType.FLOOR);
                    }
                    roughEquipments.forEach(equipment -> {
                        resultService.save(new CalculationResult
                                .Builder(mst, mst.getFloorM2(), false)
                                .stuffId(equipment.getStuff().getId())
                                .stuffName(equipment.getStuff().getName())
                                .consumption(equipment.getStuff().getConsumption())
                                .measureName(equipment.getStuff().getPacking().getUnit().getShortName())
                                .packConsumption(equipment.getStuff().getPacking().getQuantity())
                                .packName(equipment.getStuff().getPacking().getName())
                                .build());
                    });
                });
    }

    private  double getPackConsumption(FacilityEquipment equipment, double m2) {
        return equipment.getStuff().getConsumption() * m2/equipment.getStuff().getPacking().getQuantity();
    }

    //returns arr[0] - for clean, arr[1] - for rough
    private double[] calculateM2(Measurement mst) {
        Set<Measurement> apertureSet = new HashSet<>();
        Set<Measurement> cleanDecSet = new HashSet<>();
        Set<Measurement> roughDecSet = new HashSet<>();
        resultService.findByWallId(mst.getWallId())
                .forEach(result -> {
                    if(result.getMeasurement().getApertureId() != null) {
                        apertureSet.add(result.getMeasurement());
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
        double aprM2 = calcSum(apertureSet);
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