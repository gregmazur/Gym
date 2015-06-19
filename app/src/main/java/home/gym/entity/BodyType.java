package home.gym.entity;

/**
 * Created by greg on 03.05.15.
 */
public enum BodyType {
    ECTOMORPH, MESOMORPH, ENDOMORPH, UNDIFINED;


    @Override
    public String toString() {
        return this.name();
    }
}
