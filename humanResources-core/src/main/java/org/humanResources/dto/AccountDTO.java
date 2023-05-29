package org.humanResources.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@EqualsAndHashCode
//@Value
//@Builder
@AllArgsConstructor
@Data
public class AccountDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotEmpty
    private Set<Long> roleIds;

    public AccountDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
