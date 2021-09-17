package mrvladimort.pet.sbmjms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseMessage implements Serializable {

    private static final long serialVersionUID = -6336642290577579464L;

    private UUID id;
    private String name;
}
