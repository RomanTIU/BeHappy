package md.behappy.orm;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "diet_quantity")
public class DietQuantity extends EntitySuperClassIdOnly {

    public String name;

    public String value;

}
