package home.gym.entity;

/**
 * Created by greg on 03.05.15.
 */
public enum BodyType {
    ECTOMORPH("ECTOMORPH"), MESOMORPH("MESOMORPH"), ENDOMORPH("ENDOMORPH"), UNDIFINED("UNDIFINED");

private final String type;
    BodyType(String type) {
        this.type = type;
    }
    public String getType(){
        return type;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
