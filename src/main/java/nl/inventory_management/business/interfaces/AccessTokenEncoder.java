package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.AccessToken;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
