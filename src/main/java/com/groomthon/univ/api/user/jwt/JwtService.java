package com.groomthon.univ.api.user.jwt;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class JwtService {

    private final String secretKey;
    private final long expirationTime;


}
