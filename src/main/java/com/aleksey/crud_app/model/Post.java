package com.aleksey.crud_app.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private String content;
    private String created;
    private String updated;
    private List<Label> labels;
}
