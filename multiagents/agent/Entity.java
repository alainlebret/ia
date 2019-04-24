package multiagents.agent;

public class Entity {

    private double x_;
    private double y_;

    public Entity() {
        x_ = 0;
        y_ = 0;
    }

    public Entity(double x, double y) {
        x_ = x;
        y_ = y;
    }

    public double getX() {
        return x_;
    }

    public void setX(double x) {
        x_ = x;
    }

    public double getY() {
        return y_;
    }

    public void setY(double y) {
        y_ = y;
    }

    public double distance(Entity entity) {
        return Math.sqrt((entity.x_ - x_) * (entity.x_ - x_) +
                (entity.y_ - y_) * (entity.y_ - y_));
    }

    public double squaredDistance(Entity entity) {
        return (entity.x_ - x_) * (entity.x_ - x_) +
                (entity.y_ - y_) * (entity.y_ - y_);
    }

}
