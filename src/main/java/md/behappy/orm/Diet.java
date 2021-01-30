package md.behappy.orm;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table (name = "diet")
public class Diet extends EntitySuperClassIdOnly {

   public String name;

   public String recipe;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade({CascadeType.ALL})
    @JoinTable (name = "diet_bind_diet_quantity", joinColumns = @JoinColumn(name = "id"),
                inverseJoinColumns = @JoinColumn(name = "diet_quantity_id"))
    public Set<DietQuantity> dietQuantity;

    @OneToOne
    @Cascade({CascadeType.ALL})
    @JoinTable (name = "diet_bind_diet_total", joinColumns = @JoinColumn(name = "id"),
                inverseJoinColumns = @JoinColumn(name = "diet_total_id"))
    public DietTotal dietTotal;

}
