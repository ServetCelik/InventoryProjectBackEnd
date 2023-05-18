package nl.inventory_management.controller;

import lombok.RequiredArgsConstructor;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.domain.Pallet;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.business.interfaces.*;
import nl.inventory_management.configuration.security.isauthenticated.IsAuthenticated;
import nl.inventory_management.controller.converter.PalletConverter;
import nl.inventory_management.controller.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/pallet")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class PalletController {
    private final CreatePalletUseCase createPalletUseCase;
    private final GetPalletUseCase getPalletUseCase;
    private final DeletePalletUseCase deletePalletUseCase;
    private final GetLocationUseCase getLocationUseCase;
    private final GetProductUseCase getProductUseCase;
    private final UpdatePalletUseCase updatePalletUseCase;
    private final GetPalletStatisticsUseCase getPalletStatisticsUseCase;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreatePalletResponse> createPallet(@RequestBody @Valid CreatePalletRequest createPalletRequest) {
        Location loc = getLocationUseCase.getLocationByName(createPalletRequest.getLocationName());
        Product product = getProductUseCase.getProductByName(createPalletRequest.getProductName());
        Pallet pallet = Pallet.builder()
                .amount(createPalletRequest.getAmount())
                .location(loc)
                .product(product)
                .build();

        Pallet responseEntity = createPalletUseCase.createPallet(pallet);
        CreatePalletResponse response = PalletConverter.palletToCreatePalletResponse(responseEntity);

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DEPOT_WORKER","ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<CreatePalletResponse> updatePallet(@PathVariable("id") long id,@RequestBody @Valid UpdatePalletRequest updatePalletRequest) {
        Location loc = getLocationUseCase.getLocationByName(updatePalletRequest.getLocationName());
        Product product = getProductUseCase.getProductByName(updatePalletRequest.getProductName());
        Pallet pallet = Pallet.builder()
                .id(id)
                .amount(updatePalletRequest.getAmount())
                .location(loc)
                .product(product)
                .build();

        Pallet responseEntity = updatePalletUseCase.updatePallet(pallet);
        CreatePalletResponse response = PalletConverter.palletToCreatePalletResponse(responseEntity);

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DEPOT_WORKER","ROLE_ADMIN"})
    @GetMapping("/")
    public ResponseEntity<List<GetPalletResponse>> getAllPallets(){

        List<Pallet> responseEntityList = getPalletUseCase.getAllPallets();
        List<GetPalletResponse> response = responseEntityList.stream().map(PalletConverter::palletToGetPalletResponse).toList();

        return ResponseEntity.ok(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DEPOT_WORKER","ROLE_ADMIN"})
    @GetMapping("/byProduct/")
    public ResponseEntity<List<TotalAmountOfProduct>> getAmountByProduct() {
        List<TotalAmountOfProduct> list = getPalletStatisticsUseCase.getTotalProductsByProduct()
                .entrySet().stream().map(x->TotalAmountOfProduct.builder()
                        .product(x.getKey().getName()).amount(x.getValue()).build()).toList();

        return ResponseEntity.ok(list);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DEPOT_WORKER","ROLE_ADMIN"})
    @GetMapping("/byLocation/")
    public ResponseEntity<List<TotalProductInLocation>> getAmountByLocation() {
        List<TotalProductInLocation> list = getPalletStatisticsUseCase.getTotalProductsByLocation()
                .entrySet().stream().map(x->TotalProductInLocation.builder()
                        .location(x.getKey().getName()).amount(x.getValue()).build()).toList();

        return ResponseEntity.ok(list);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DEPOT_WORKER","ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<GetPalletResponse> getPallet(@PathVariable Long id) {
        Pallet responseEntity = getPalletUseCase.getPalletById(id);
        GetPalletResponse response = PalletConverter.palletToGetPalletResponse(responseEntity);
        return ResponseEntity.ok(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DEPOT_WORKER","ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deletePallet(@PathVariable Long id) {
        DeleteResponse response = deletePalletUseCase.deletePallet(id);

        return ResponseEntity.ok(response);
    }
}