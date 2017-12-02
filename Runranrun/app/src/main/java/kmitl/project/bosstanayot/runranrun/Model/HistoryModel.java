package kmitl.project.bosstanayot.runranrun.Model;

/**
 * Created by barjord on 11/21/2017 AD.
 */

public class HistoryModel {
    private String time;
    private String sec;
    private String count_step;
    private String calories;
    private String distance;
    private String duration;
    public HistoryModel(String time, String calories, String distance, String duration,String count_step, String sec) {
        this.count_step = count_step;
        this.sec = sec;
        this.calories = calories;
        this.time = time;
        this.distance = distance;
        this.duration = duration;
    }
    public HistoryModel() {
    }
    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getCount_step() {
        return count_step;
    }

    public void setCount_step(String count_step) {
        this.count_step = count_step;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
