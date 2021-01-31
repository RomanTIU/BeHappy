package md.behappy.orm;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "diet_total")
public class DietTotal extends EntitySuperClassIdOnly {
    public int grams;

    public int kcal;

    public Macronutrients macronutrients;

    public enum Macronutrients {
        PROTEINS, FIBRE, FATS, CARBOHYDRATE
    }
}
