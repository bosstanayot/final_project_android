package kmitl.project.bosstanayot.runranrun.Validator;

/**
 * Created by barjord on 12/3/2017 AD.
 */

public class NullWeightValidator implements UserWeightValidator{

    @Override
    public boolean isValid(Integer input) {
        return input != null;
    }
}
