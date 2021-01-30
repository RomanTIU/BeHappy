package md.behappy.orm;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "regime")
public class Regime extends EntitySuperClassIdOnly {

    public String name;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinTable(name = "regime_diet", joinColumns = @JoinColumn(name = "id"),
                    inverseJoinColumns = @JoinColumn(name = "diet_id"))
    public List<Diet> diet;

    public static List<Regime> listNotDeleted() {
        return list("isDeleted", false);
    }

    public List<Diet> getDiet(){
        return diet;
    }

}
