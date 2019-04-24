package multiagents.agent;

public class Obstacle extends Entity {
    private final double radius_;
    private int remainingTime_;

    public Obstacle(double x, double y, double radius) {
        super(x, y);
        radius_ = radius;
        remainingTime_ = 500;
    }

    public double getRadius() {
        return radius_;
    }

    public void update() {
        remainingTime_--;
    }

    public boolean isOver() {
        return remainingTime_ <= 0;
    }

    @Override
    public String toString() {
        return null;
    }
}
