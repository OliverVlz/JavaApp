package services;

import java.awt.Color;
import models.Shape;
import java.util.List;

public class AreaCalculator {
    private double areaAzules = 0;
    private double areaRojos = 0;

    public void updateAreas(List<Shape> shapes) {
        areaAzules = 0;
        areaRojos = 0;
        for (Shape shape : shapes) {
            double area = shape.area();
            if (shape.getColor().equals(Color.BLUE)) {
                areaAzules += area;
            } else if (shape.getColor().equals(Color.RED)) {
                areaRojos += area;
            }
        }
    }

    public double calculatePercentage() {
        if (areaAzules == 0) {
            return 0;
        }
        return (areaRojos / areaAzules) * 100;
    }

    public double getAreaAzules() {
        return areaAzules;
    }

    public double getAreaRojos() {
        return areaRojos;
    }
}

