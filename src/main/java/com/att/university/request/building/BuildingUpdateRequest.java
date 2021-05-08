package com.att.university.request.building;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingUpdateRequest {
    private Integer id;
    private String address;
}
