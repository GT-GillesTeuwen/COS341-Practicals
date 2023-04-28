package Visualisation;

import java.util.ArrayList;

public class Force {
    private double xComponent;
    private double yComponent;

    public Force(double xComponent, double yComponent) {
        this.xComponent = xComponent;
        this.yComponent = yComponent;
    }

    public Force(Force[] components) {
        this.xComponent = 0;
        this.yComponent = 0;
        for (Force force : components) {
            this.xComponent += force.xComponent;
            this.yComponent = force.yComponent;
        }
    }

    public Force(ArrayList<Force> components) {
        this.xComponent = 0;
        this.yComponent = 0;
        for (Force force : components) {
            this.xComponent += force.xComponent;
            this.yComponent = force.yComponent;
        }
    }

    public double getxComponent() {
        return xComponent;
    }

    public double getyComponent() {
        return yComponent;
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(xComponent, 2) + Math.pow(yComponent, 2));
    }

}
