package multiagents.agent;

public abstract class Agent extends Entity {

    private int age_;

    public Agent(double x, double y) {
        super(x, y);
        age_ = 0;
    }

    public int getAge() {
        return age_;
    }

    public void setAge(int age) {
        age_ = age;
    }

    public abstract void action();

    public abstract boolean dead();

    public abstract void move();

    @Override
    public abstract String toString();

}
