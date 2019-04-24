package multiagents;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import multiagents.agent.Fish;
import multiagents.agent.Obstacle;

public class OceanPanel extends JPanel implements Observer, MouseListener {
    private Ocean ocean;

    public OceanPanel() {
        this.setBackground(new Color(15, 76, 100  ));
        this.addMouseListener(this);
    }

    public void run() {
        ocean = new Ocean(600, getWidth(), getHeight());
        ocean.addObserver(this);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ocean.update();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 15);
    }

    protected void drawFish(Fish p, Graphics g) {
        g.setColor(new Color(240, 123, 18));
        g.drawLine((int) p.getX(), (int) p.getY(), (int) (p.getX() - 10 * p.getSpeedX()), (int) (p.getY() - 10 * p.getSpeedY()));
    }

    protected void drawObstacle(Obstacle o, Graphics g) {
        g.setColor(new Color(240, 240, 10));
        g.fillOval((int) (o.getX() - o.getRadius()), (int) (o.getY() - o.getRadius()), (int) o.getRadius() * 2, (int) o.getRadius() * 2);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Fish fish : ocean.getFishes()) {
            drawFish(fish, g);
        }
        for (Obstacle obstacle : ocean.getObstacles()) {
            drawObstacle(obstacle, g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ocean.addObstacle(e.getX(), e.getY(), 20);
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
