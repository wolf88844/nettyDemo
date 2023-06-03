package org.example.session;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Session {
    private String userId;
    private String username;
}
