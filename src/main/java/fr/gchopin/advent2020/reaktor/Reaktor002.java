package fr.gchopin.advent2020.reaktor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.gchopin.advent2020.Input;

public class Reaktor002 {

    public static void main(String[] args) {
        Input input = new Input("reaktor/input-002.txt");
        List<String> lines = input.getLines();
        String line = lines.get(0);

        StringBuilder sb = new StringBuilder();
        Arrays.stream(line.split(" ")).forEach(s -> sb.append((char) Integer.parseInt(s, 2)));
        String jsonString = sb.toString();
        JSONArray jsonArray = new JSONArray(jsonString);

        Map<Integer,String> readingTotals = new HashMap<>();

        for (int o = 0; o < jsonArray.length(); o++) {
            JSONObject jsonObject = jsonArray.getJSONObject(o);
            JSONArray readings = jsonObject.getJSONArray("readings");
            for (int r = 0; r < readings.length(); r++) {
                JSONObject reading = readings.getJSONObject(r);
                String readingId = reading.getString("id");
                JSONObject readingContaminants = reading.getJSONObject("contaminants");
                int readingTotal = 0;
                Iterator<String> iteratorKeys = readingContaminants.keys();
                while (iteratorKeys.hasNext()) {
                    String contaminant = iteratorKeys.next();
                    int value = readingContaminants.getInt(contaminant);
                    readingTotal += value;
                }
                readingTotals.put(readingTotal, readingId);
            }
        }

        Integer maxReading = readingTotals.keySet().stream().max(Comparator.naturalOrder()).get();
        String maxReadingId = readingTotals.get(maxReading);

        System.out.println(maxReading);
        System.out.println(maxReadingId);

        String decodedId = new String(hexStringToByteArray(maxReadingId));
        System.out.println(decodedId);
    }

    public static byte[] hexStringToByteArray(String hex) {
        int l = hex.length();
        byte[] data = new byte[l / 2];
        for (int i = 0; i < l; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
