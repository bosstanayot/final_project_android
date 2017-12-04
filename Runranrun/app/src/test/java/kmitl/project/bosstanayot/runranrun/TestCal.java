package kmitl.project.bosstanayot.runranrun;

import org.junit.Test;

import kmitl.project.bosstanayot.runranrun.Control.ConcludeActivity;
import kmitl.project.bosstanayot.runranrun.Control.RunActivity;
import kmitl.project.bosstanayot.runranrun.Model.ProfileInfo;

import static org.junit.Assert.*;

public class TestCal {
    @Test
    public void caltest() throws Exception {
        ConcludeActivity c = new ConcludeActivity();
        double weight = 50;
        double distance = 5;
        assertEquals((int)258.75, (int)c.setCal(weight,distance));
    }
    @Test
    public void calzero() throws Exception {
        ConcludeActivity c = new ConcludeActivity();
        double weight = 0;
        double distance = 5;
        assertEquals(0, (int)c.setCal(weight,distance));
    }
    @Test (expected = Exception.class)
    public void nullweight() throws Exception{
        ProfileInfo p = new ProfileInfo();
        Integer weight = null;
        p.setWeight(weight);
    }

    @Test
    public void distanTest() throws Exception{
        RunActivity r = new RunActivity();
        int step = 200;
        assertEquals(0.156,r.getDistanceRun(step),r.getDistanceRun(step));
    }

}