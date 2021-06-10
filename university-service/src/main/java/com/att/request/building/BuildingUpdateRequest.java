package com.att.request.building;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingUpdateRequest {
    @NotNull
    private Integer id;

    @NotBlank(message = "{building.address}")
    private String address;
}
