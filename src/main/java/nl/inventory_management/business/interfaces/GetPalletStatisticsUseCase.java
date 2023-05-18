package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.domain.Product;

import java.util.HashMap;

public interface GetPalletStatisticsUseCase {
    HashMap<Product, Integer> getTotalProductsByProduct();
    HashMap<Location, Integer> getTotalProductsByLocation();
}
