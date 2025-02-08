package td3;

import java.util.LinkedHashMap;
import java.util.Map;

public class PresetLoader {
    public static Map<String, double[]> getPresets() {
        Map<String, double[]> presets = new LinkedHashMap<>();
        presets.put("y = x^2", new double[]{0, 0, 1});
        presets.put("y = x^3 - x + 1", new double[]{1, -1, 0, 1});
        presets.put("y = -2x^2 + 5x - 1", new double[]{-1, 5, -2});
        return presets;
    }
}