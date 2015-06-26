package home.gym.entity;

import java.io.Serializable;

/**
 * Created by greg on 03.05.15.
 */


public class Profile extends BaseEntity implements Serializable {

    private String name;
    private BodyType bodyType;
    private boolean man;
    private float idealWeight;
    private float currentWeight;

    public Profile(String name, BodyType bodyType, boolean man, float idealWeight, float currentWeight) {

        this.man = man;
        this.name = name;
        this.bodyType = bodyType;
        this.idealWeight = idealWeight;
        this.currentWeight = currentWeight;
    }
    public Profile(String name, BodyType bodyType, int man, float idealWeight, float currentWeight) {

        this.man = man == 1?true:false;
        this.name = name;
        this.bodyType = bodyType;
        this.idealWeight = idealWeight;
        this.currentWeight = currentWeight;
    }

    public Profile() {
    }

    public Profile(String name, int wristGirth, int height, boolean man, int age) {//constructor for Registration activity
    /*Ecto < 18 M <15 W
	 * Meso 18-20 ;15 -17
	 * Endo >20;>17
	 */
        if (man) {
            if (wristGirth < 18) {
                bodyType = bodyType.ECTOMORPH;
            } else if (wristGirth < 20) {
                bodyType = bodyType.MESOMORPH;
            } else {
                bodyType = bodyType.ENDOMORPH;
            }
        } else {
            if (wristGirth < 15) {
                bodyType = bodyType.ECTOMORPH;
            } else if (wristGirth < 17) {
                bodyType = bodyType.MESOMORPH;
            } else {
                bodyType = bodyType.ENDOMORPH;
            }
        }
        this.man = man;
        this.name = name;
        idealWeight = idealWeight(height, age);
    }

    private int idealWeight(int height, int age) {
        int weight;
        if (height < 165) {
            weight = height - 100;
        } else if (height < 186) {
            weight = height - 105;
        } else {
            weight = height - 110;
        }
        if (bodyType == BodyType.ECTOMORPH) {
            weight -= (weight * 0.1);
        }
        if (bodyType == BodyType.ENDOMORPH) {
            weight += (weight * 0.1);
        }
        if (age >= 40) {
            weight += 5;
        }
        return weight;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public boolean isMan() {
        return man;
    }
    public int isManInt(){
        return man ? 1:0;
    }

    public void setIsMan(boolean isMan) {
        this.man = isMan;
    }
    public void setManFromInt(int value){
        this.man = value == 1?true:false;
    }


    public float getIdealWeight() {
        return idealWeight;
    }

    public void setIdealWeight(float idealWeight) {
        this.idealWeight = idealWeight;
    }

    public float getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(float currentWeight) {
        this.currentWeight = currentWeight;
    }

    @Override
    public String toString() {
        return name.toUpperCase();
    }
}
