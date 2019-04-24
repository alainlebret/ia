package multiagents.agent;

import java.util.List;

import multiagents.Ocean;

public class Fish extends Agent {
    public static final int MEDIAN_MAX_AGE = 7;
    public static final double STEP = 3;
    public static final double MIN_DISTANCE = 5;
    public static final double MIN_SQUARED_DISTANCE = 25;
    public static final double MAX_DISTANCE = 40;
    public static final double MAX_SQUARED_DISTANCE = 1600;

    private double xVelocity_;
    private double yVelocity_;
    private final Ocean ocean_;

    public Fish(double x, double y, double direction, Ocean ocean) {
        super(x, y);
        xVelocity_ = Math.cos(direction);
        yVelocity_ = Math.sin(direction);
        ocean_ = ocean;
    }

    public double getSpeedX() {
        return xVelocity_;
    }

    public double getSpeedY() {
        return yVelocity_;
    }

    protected boolean inFrontOf(Fish p) {
        double theSquaredDistance = squaredDistance(p);
        return (theSquaredDistance < MAX_SQUARED_DISTANCE && theSquaredDistance > MIN_SQUARED_DISTANCE);
    }

    protected double minDistanceToWalls(double westWall, double northWall, double eastWall, double SouthWall) {
        double min = Math.min(getX() - westWall, getY() - northWall);
        min = Math.min(min, eastWall - getX());
        min = Math.min(min, SouthWall - getY());
        return min;
    }

    protected void normalize() {
        double length = Math.sqrt(xVelocity_ * xVelocity_ + yVelocity_ * yVelocity_);
        xVelocity_ /= length;
        yVelocity_ /= length;
    }

    protected boolean avoidObstacles(double westWall, double northWall, double eastWall, double southWall) {
        if (getX() < westWall) {
            setX(westWall);
        } else if (getY() < northWall) {
            setY(northWall);
        } else if (getX() > eastWall) {
            setX(eastWall);
        } else if (getY() > southWall) {
            setY(southWall);
        }

        // Change the direction
        double distance = minDistanceToWalls(westWall, northWall, eastWall, southWall);
        if (distance < MIN_DISTANCE) {
            if (distance == (getX() - westWall)) {
                xVelocity_ += 0.3;
            } else if (distance == (getY() - northWall)) {
                yVelocity_ += 0.3;
            } else if (distance == (eastWall - getX())) {
                xVelocity_ -= 0.3;
            } else if (distance == (southWall - getY())) {
                yVelocity_ -= 0.3;
            }
            normalize();
            return true;
        }
        return false;
    }

    protected boolean avoidObstacles(List<Obstacle> obstacles) {
        if (!obstacles.isEmpty()) {
            // Looking for the closest obstacle
            Obstacle closestObstacle = obstacles.get(0);
            double theSquaredDistance = squaredDistance(closestObstacle);
            for (Obstacle obstacle : obstacles) {
                if (squaredDistance(obstacle) < theSquaredDistance) {
                    closestObstacle = obstacle;
                    theSquaredDistance = squaredDistance(obstacle);
                }
            }

            // If collision computes the diff vector
            if (theSquaredDistance < (closestObstacle.getRadius() * closestObstacle.getRadius())) {
                double distance = Math.sqrt(theSquaredDistance);
                double diffX = (closestObstacle.getX() - getX()) / distance;
                double diffY = (closestObstacle.getY() - getY()) / distance;
                xVelocity_ = xVelocity_ - diffX / 2;
                yVelocity_ = yVelocity_ - diffY / 2;
                normalize();
                return true;
            }
        }
        return false;
    }

    protected boolean avoidFishes(Fish[] fishes) {
        // Looking for the closest fish
        Fish closestFish;
        double theSquaredDistance;

        if (!fishes[0].equals(this)) {
            closestFish = fishes[0];
        } else {
            closestFish = fishes[1];
        }

        theSquaredDistance = squaredDistance(closestFish);
        for (Fish fish : fishes) {
            if (squaredDistance(fish) < theSquaredDistance && !fish.equals(this)) {
                closestFish = fish;
                theSquaredDistance = squaredDistance(closestFish);
            }
        }

        // Avoid
        if (theSquaredDistance < MIN_SQUARED_DISTANCE) {
            double distance = Math.sqrt(theSquaredDistance);
            double diffX = (closestFish.getX() - getX()) / distance;
            double diffY = (closestFish.getY() - getY()) / distance;
            xVelocity_ = xVelocity_ - diffX / 4;
            yVelocity_ = yVelocity_ - diffY / 4;
            normalize();
            return true;
        }
        return false;
    }

    protected void calculateMeanDirection(Fish[] fishes) {
        double xTotalVelocity = 0;
        double yTotalVelocity = 0;
        int nbTotal = 0;

        for (Fish fish : fishes) {
            if (inFrontOf(fish)) {
                xTotalVelocity += fish.xVelocity_;
                yTotalVelocity += fish.yVelocity_;
                nbTotal++;
            }
        }
        if (nbTotal >= 1) {
            xVelocity_ = (xTotalVelocity / nbTotal + xVelocity_) / 2;
            yVelocity_ = (yTotalVelocity / nbTotal + yVelocity_) / 2;
            normalize();
        }
    }

    @Override
    public void action() {
        if (!avoidObstacles(0,0, ocean_.getWidth(), ocean_.getHeight())) {
            if (!avoidObstacles(ocean_.getObstacles())) {
                if (!avoidFishes(ocean_.getFishes())) {
                    calculateMeanDirection(ocean_.getFishes());
                }
            }
        }
        move();
    }

    @Override
    public boolean dead() {
        return false;
    }

    @Override
    public void move() {
        setX(getX() + STEP * xVelocity_);
        setY(getY() + STEP * yVelocity_);
    }

    @Override
    public String toString() {
        return String.format("Fish agent at (%s, %s) with velocity (%s, %s)", getX(), getY(), getSpeedX(), getSpeedY());
    }
}
