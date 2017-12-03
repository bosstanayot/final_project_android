package kmitl.project.bosstanayot.runranrun.Model;

import java.util.ArrayList;
import java.util.List;

import kmitl.project.bosstanayot.runranrun.Validator.NullWeightValidator;
import kmitl.project.bosstanayot.runranrun.Validator.UserWeightValidator;

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
        try {
            setWeight(weight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setGender(gender);
        setUid(uid);
    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight)throws Exception {
        List<UserWeightValidator> userWeightValidators = new ArrayList<>();
        userWeightValidators.add(new NullWeightValidator());
        for (UserWeightValidator userWeightValidator : userWeightValidators){
            if(!userWeightValidator.isValid(weight)){
                throw new Exception();
            }
        }
        this.weight = weight;
    }

    public CharSequence getGender() {
        return gender;
    }

    public void setGender(CharSequence gender) {
        this.gender = gender;
    }
}