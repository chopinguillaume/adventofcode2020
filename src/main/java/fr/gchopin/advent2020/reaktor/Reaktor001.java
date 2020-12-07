package fr.gchopin.advent2020.reaktor;

import java.util.Base64;
import java.util.List;

import fr.gchopin.advent2020.Input;

public class Reaktor001 {

    public static void main(String[] args) {
        Input input = new Input("reaktor/input-001.txt");
        List<String> lines = input.getLines();
        String line = lines.get(0);

        for (int i = 0; i < line.length() - 16; i++) {

            String substring = line.substring(i, i + 16);
            long countDistinct = substring.chars().distinct().count();
            if (countDistinct == 16) {
                System.out.println(substring);

                String decoded = new String(Base64.getDecoder().decode(substring));
                System.out.println(decoded);
            }

        }
    }
}
