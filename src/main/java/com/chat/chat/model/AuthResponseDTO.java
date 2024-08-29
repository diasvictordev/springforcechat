package com.chat.chat.model;

import java.time.LocalDateTime;

public record AuthResponseDTO (Long userid, String token, LocalDateTime expirationTime){
}
