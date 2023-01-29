package aprendendo.api.toDoList.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import aprendendo.api.toDoList.model.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tasks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name não pode ser null")
    private String name;

    private boolean completed;

    private LocalDate creationDate;

    private LocalDate completedDate;
    
    @ManyToOne
    @JoinColumn(referencedColumnName = "id",columnDefinition = "user_id")
    private User user;

    public UserDTO getUser() {
        return user.toDTO();
    }
}
