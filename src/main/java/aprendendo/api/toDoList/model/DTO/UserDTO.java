package aprendendo.api.toDoList.model.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
@Accessors(chain = true)
public class UserDTO {
    private Long id;

    private String name;
    
    private String email;
}
