package com.aleksey.crud_app.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Label {
    private int id;
    private String name;
    private Status status;
}
