package fr.gchopin.advent2020.days;

import java.util.List;

import fr.gchopin.advent2020.Day;

public class Day12 extends Day {

    public Day12(String inputFile) {
        super(inputFile);
    }

    @Override
    public void part1() {
        List<String> instructions = _input.getLines();

        FerryState ferry = new FerryState(Direction.East, 0, 0);
        for (String instruction : instructions) {
            ferry.nextState(instruction);
        }

        int manhattan = Math.abs(ferry.x) + Math.abs(ferry.y);
        System.out.println(manhattan);
    }

    @Override
    public void part2() {
        List<String> instructions = _input.getLines();

        FerryWithWaypointState ferry = new FerryWithWaypointState(10, -1, 0, 0);
        for (String instruction : instructions) {
            ferry.nextState(instruction);
        }

        int manhattan = Math.abs(ferry.ferryX) + Math.abs(ferry.ferryY);
        System.out.println(manhattan);
    }

    enum Direction {
        North, South, East, West;
    }

    class FerryState {

        Direction direction = Direction.East; // Facing direction
        int x = 0; // West is X < 0, East is X > 0
        int y = 0; // North is Y < 0, South is Y > 0

        public FerryState(Direction direction, int x, int y) {
            this.direction = direction;
            this.x = x;
            this.y = y;
        }

        public void nextState(String instruction) {
            Character instructionType = instruction.charAt(0);
            Integer value = Integer.parseInt(instruction.substring(1));

            switch (instructionType) {
                case 'N':
                    goNorth(value);
                    break;
                case 'S':
                    goSouth(value);
                    break;
                case 'W':
                    goWest(value);
                    break;
                case 'E':
                    goEast(value);
                    break;
                case 'L':
                    turnLeft(value);
                    break;
                case 'R':
                    turnRight(value);
                    break;
                case 'F':
                    forward(value);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }

        private void forward(Integer value) {
            switch (direction) {
                case North:
                    goNorth(value);
                    break;
                case South:
                    goSouth(value);
                    break;
                case West:
                    goWest(value);
                    break;
                case East:
                    goEast(value);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }

        private void turnRight(Integer value) {
            for (int i = 0; i < value / 90; i++) {
                switch (direction) {
                    case North:
                        direction = Direction.East;
                        break;
                    case East:
                        direction = Direction.South;
                        break;
                    case South:
                        direction = Direction.West;
                        break;
                    case West:
                        direction = Direction.North;
                        break;

                    default:
                        throw new IllegalStateException();
                }
            }
        }

        private void turnLeft(Integer value) {
            for (int i = 0; i < value / 90; i++) {
                switch (direction) {
                    case North:
                        direction = Direction.West;
                        break;
                    case West:
                        direction = Direction.South;
                        break;
                    case South:
                        direction = Direction.East;
                        break;
                    case East:
                        direction = Direction.North;
                        break;

                    default:
                        throw new IllegalStateException();
                }
            }
        }

        private void goEast(Integer value) {
            x = x + value;
        }

        private void goWest(Integer value) {
            x = x - value;
        }

        private void goSouth(Integer value) {
            y = y + value;
        }

        private void goNorth(Integer value) {
            y = y - value;
        }

    }

    class FerryWithWaypointState {

        int waypointX;
        int waypointY;
        int ferryX;
        int ferryY;

        public FerryWithWaypointState(int waypointX, int waypointY, int ferryX, int ferryY) {
            this.waypointX = waypointX;
            this.waypointY = waypointY;
            this.ferryX = ferryX;
            this.ferryY = ferryY;
        }

        public void nextState(String instruction) {
            Character instructionType = instruction.charAt(0);
            Integer value = Integer.parseInt(instruction.substring(1));

            switch (instructionType) {
                case 'N':
                    goNorth(value);
                    break;
                case 'S':
                    goSouth(value);
                    break;
                case 'W':
                    goWest(value);
                    break;
                case 'E':
                    goEast(value);
                    break;
                case 'L':
                    turnLeft(value);
                    break;
                case 'R':
                    turnRight(value);
                    break;
                case 'F':
                    forward(value);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }

        private void forward(Integer value) {
            ferryX = ferryX + value * waypointX;
            ferryY = ferryY + value * waypointY;
        }

        private void turnRight(Integer value) {
            for (int i = 0; i < value / 90; i++) {
                // Turn right once
                int new_x = -waypointY;
                int new_y = waypointX;
                waypointX = new_x;
                waypointY = new_y;
            }
        }

        private void turnLeft(Integer value) {
            for (int i = 0; i < value / 90; i++) {
                // Turn left once
                int new_x = waypointY;
                int new_y = -waypointX;
                waypointX = new_x;
                waypointY = new_y;
            }
        }

        private void goEast(Integer value) {
            waypointX = waypointX + value;
        }

        private void goWest(Integer value) {
            waypointX = waypointX - value;
        }

        private void goSouth(Integer value) {
            waypointY = waypointY + value;
        }

        private void goNorth(Integer value) {
            waypointY = waypointY - value;
        }
    }
}
