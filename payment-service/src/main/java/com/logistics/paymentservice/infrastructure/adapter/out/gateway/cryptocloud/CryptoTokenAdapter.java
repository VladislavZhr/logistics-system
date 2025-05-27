package com.logistics.paymentservice.infrastructure.adapter.out.gateway.cryptocloud;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.logistics.paymentservice.application.port.out.details.ValidateCryptoTokenPort;
import com.logistics.paymentservice.infrastructure.config.crypto.CryptoCloudConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class CryptoTokenAdapter implements ValidateCryptoTokenPort {

    private final CryptoCloudConfig cryptoCloudConfig;

    @Override
    public void validateToken(String token, String invoiceId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(cryptoCloudConfig.getSecretKey());
            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT jwt = verifier.verify(token);

            String claimId = jwt.getClaim("id").asString();
            if (!invoiceId.equals(claimId)) {
                throw new RuntimeException("Invalid invoice ID in token. Expected: " + invoiceId + ", actual: " + claimId);
            }

            Date expiryDate = jwt.getExpiresAt();
            if (expiryDate == null || expiryDate.before(new Date())) {
                throw new RuntimeException("Token has expired for invoice ID: " + invoiceId);
            }

        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid token signature or structure for invoice ID: " + invoiceId, e);
        }
    }
}
