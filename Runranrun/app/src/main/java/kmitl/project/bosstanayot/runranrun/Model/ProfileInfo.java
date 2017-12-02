package kmitl.project.bosstanayot.runranrun.Model;

/**
 * Created by barjord on 11/20/2017 AD.
 */

public class ProfileInfo {
    public class KEY {
        public static final String GENDER = "gender";
        public static final String WEIGHT = "weight";
    }
    public static String uid;
    public static int weight;
    public static CharSequence gender;
    public ProfileInfo() {

    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        ProfileInfo.uid = uid;
    }

    public ProfileInfo(int weight, String gender, String uid) {
        setWeight(weight);
        setGender(gender);
        setUid(uid);
    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public CharSequence getGender() {
        return gender;
    }

    public void setGender(CharSequence gender) {
        this.gender = gender;
    }
}