package kmitl.project.bosstanayot.runranrun;

/**
 * Created by barjord on 11/20/2017 AD.
 */

public class ProfileInfo {
    public class KEY {
        public static final String GENDER = "gender";
        public static final String WEIGHT = "weight";
    }




    private int weight;

    private String gender;
    private ProfileInfo() {
        // Default Constructor for firebase mapping
    }
    public ProfileInfo(int weight, String gender) {
        setWeight(weight);
        setGender(gender);
    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}