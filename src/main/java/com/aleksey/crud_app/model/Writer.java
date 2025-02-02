package com.aleksey.crud_app.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Writer {
    private int id;
    private String fistName;
    private String LastName;
    private List<Post> post;
}
