package fr.gchopin.advent2020.days;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import fr.gchopin.advent2020.Day;

public class Day13 extends Day {

    public Day13(String inputFile) {
        super(inputFile);
    }

    @Override
    public void part1() {
        List<String> lines = _input.getLines();
        int earliestDeparture = Integer.parseInt(lines.get(0));
        List<Integer> listOfBuses = listOfBuses(lines.get(1));

        int minBusDeparture = Integer.MAX_VALUE;
        int minBusId = 0;

        for (Integer busId : listOfBuses) {
            int busDeparture = (int) (Math.ceil((double) earliestDeparture / (double) busId) * busId);
            if (busDeparture < minBusDeparture) {
                minBusDeparture = busDeparture;
                minBusId = busId;
            }
        }

        System.out.println(minBusId * (minBusDeparture - earliestDeparture));
    }

    private List<Integer> listOfBuses(String string) {
        List<Integer> list = new ArrayList<>();

        String[] split = string.split(",");

        for (String busIdStr : split) {
            if (!busIdStr.equals("x")) {
                list.add(Integer.parseInt(busIdStr));
            }
        }

        return list;
    }

    @Override
    public void part2() {
        List<String> lines = _input.getLines();
        List<Integer> listAllBuses = listAllBuses(lines.get(1));

        List<Integer> buses = listOfBuses(lines.get(1));
        List<Integer> offsets = new ArrayList<>();

        for (Integer bus : buses) {
            offsets.add(listAllBuses.indexOf(bus));
        }

        // Increment starts at the value of the first bus.
        // Time starts at the first possible time for the first bus, which is its value
        // minus its offset
        BigInteger incr = BigInteger.valueOf(buses.get(0));
        BigInteger time = incr.subtract(BigInteger.valueOf(offsets.get(0)));

        // Each loop, we add the next bus into the calculation
        int i = 1;
        while (i < buses.size()) {

            // We calculate the valid time for all buses from index 0 to i.
            BigInteger timePlusOffset = time.add(BigInteger.valueOf(offsets.get(i)));
            BigInteger modulo = timePlusOffset.mod(BigInteger.valueOf(buses.get(i)));
            // The time is correct for bus i if it is a multiplier of the bus value,
            // considering the offset
            if (modulo.equals(BigInteger.ZERO)) {
                // The time is valid for bus i. "incr" is multiplied by the new bus value.
                // We can do this because all input buses are prime numbers.
                // The multiplication is the LCM of all values.
                incr = incr.multiply(BigInteger.valueOf(buses.get(i)));
                i++;
            } else {
                // By increments of "incr", we only try the values that are
                // correct for the buses from index 0 to i-1.
                time = time.add(incr);
            }
        }

        System.out.println(time);
    }

    private List<Integer> listAllBuses(String string) {
        List<Integer> list = new ArrayList<>();

        String[] split = string.split(",");

        for (String busIdStr : split) {
            if (busIdStr.equals("x")) {
                list.add(0);
            } else {
                list.add(Integer.parseInt(busIdStr));
            }
        }

        return list;
    }

}
