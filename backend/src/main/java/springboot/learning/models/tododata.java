
package springboot.learning.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class tododata {

    @Id
    @GeneratedValue
    Long id;

    @NotBlank
    @NonNull
    String title;

    @NonNull
    Boolean iscompleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    userdata user;
}
