package org.example.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Builder.Default
    private int id = generateId();

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private String phone;

    private static int counter = 1;

    private static int generateId() {
        return counter++;
    }
}
