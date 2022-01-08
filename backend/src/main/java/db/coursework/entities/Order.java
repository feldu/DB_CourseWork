package db.coursework.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import db.coursework.entities.enums.OrderCaste;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "order")
@EqualsAndHashCode(exclude = "futureJobTypes")
@ToString(exclude = "futureJobTypes")
@NoArgsConstructor
@Table(name = "\"order\"")
public class Order {

    @NotNull
    @Column(name = "is_processing")
    boolean isProcessing;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    OrderCaste caste;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "predeterminer_id")
    Human human;

    @NotNull
    @Min(value = 1)
    @Column(name = "human_number")
    int humanNumber;

    @JsonIgnoreProperties(value = "orders")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "future_job_type_order",
            joinColumns = {
                    @JoinColumn(name = "order_id", referencedColumnName = "id",
                            nullable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "future_job_type_id", referencedColumnName = "id",
                            nullable = false)})
    private List<FutureJobType> futureJobTypes = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    public Order(@NotNull Human human, @NotNull @Min(value = 1) int humanNumber, @NotNull OrderCaste caste) {
        this.human = human;
        this.humanNumber = humanNumber;
        this.caste = caste;
    }
}
