package serialization;

import models.Shape;
import java.util.List;
import java.util.Map;

public interface ShapeSerializer {
    String serialize(Object obj);
    String serializeList(List<? extends Shape> shapes);
    String serializeMap(Map<String, ? extends Shape> shapeMap);
}
