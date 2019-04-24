package multiagents;

import multiagents.agent.Fish;
import multiagents.agent.Obstacle;

import java.util.Observable;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Ocean extends Observable {
    private final Fish[] fishes_;
    private final List<Obstacle> obstacles_;
    private final double width_;
    private final double height_;

    public Ocean(int numberOfFishes, double width, double height) {
        width_ = width;
        height_ = height;
        obstacles_ = new ArrayList<>();
        fishes_ = new Fish[numberOfFishes];

        Random random = new Random();
        for (int i = 0; i < numberOfFishes; i++) {
            fishes_[i] = new Fish(random.nextDouble() * this.width_, random.nextDouble() * this.height_, random.nextDouble() * 2 * Math.PI, this);
        }
    }

    public Fish[] getFishes() {
        return fishes_;
    }

    public List<Obstacle> getObstacles() {
        return obstacles_;
    }

    public double getWidth() {
        return width_;
    }

    public double getHeight() {
        return height_;
    }

    public void addObstacle(double x, double y, double radius) {
        obstacles_.add(new Obstacle(x, y, radius));
    }

    protected void updateObstacles() {
        for (Obstacle obstacle : obstacles_) {
            obstacle.update();
        }
        obstacles_.removeIf(Obstacle::isOver);
    }

    protected void updateFishes() {
        for (Fish fish : fishes_) {
            fish.action();
        }
    }

    public void update() {
        updateObstacles();
        updateFishes();
        setChanged();
        notifyObservers();
    }
}
