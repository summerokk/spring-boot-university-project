package com.att.university.request.group;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupAddRequest {
    private String name;
    private Integer facultyId;
}
