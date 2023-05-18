package nl.inventory_management.controller;

import lombok.RequiredArgsConstructor;
import nl.inventory_management.business.exception.LocationNameAlreadyExistsException;
import nl.inventory_management.business.interfaces.CreateLocationUseCase;
import nl.inventory_management.business.interfaces.DeleteLocationUseCase;
import nl.inventory_management.business.interfaces.GetLocationUseCase;
import nl.inventory_management.business.interfaces.UpdateLocationUseCase;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.exception.LocationHasSomePalletsException;
import nl.inventory_management.configuration.security.isauthenticated.IsAuthenticated;
import nl.inventory_management.controller.converter.LocationConverter;
import nl.inventory_management.controller.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class LocationController {
    private final CreateLocationUseCase createLocationUseCase;
    private final GetLocationUseCase getLocationUseCase;
    private final DeleteLocationUseCase deleteLocationUseCase;
    private final UpdateLocationUseCase updateLocationUseCase;
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PostMapping("/save")
    public ResponseEntity<CreateLocationResponse> createLocation(@RequestBody @Valid CreateLocationRequest createLocationRequest)  {
        Location location = Location.builder()
                .name(createLocationRequest.getName())
                .address(createLocationRequest.getAddress())
                .maxPallet(createLocationRequest.getMaxPallet())
                .build();

        Location responseEntity = createLocationUseCase.createLocation(location);
        CreateLocationResponse response = LocationConverter.locationToCreateLocationResponse(responseEntity);

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<CreateLocationResponse> updateLocation(@PathVariable("id") long id,@RequestBody @Valid UpdateLocationRequest request) {
        Location location = Location.builder()
                .id(id)
                .name(request.getName())
                .address(request.getAddress())
                .maxPallet(request.getMaxPallet())
                .build();

        Location responseEntity = updateLocationUseCase.updateLocation(location);
        CreateLocationResponse response = LocationConverter.locationToCreateLocationResponse(responseEntity);

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("/")
    public ResponseEntity<List<GetLocationResponse>> getAllLocations(){

        List<Location> responseEntityList = getLocationUseCase.getAllLocations();
        List<GetLocationResponse> response = responseEntityList.stream().map(LocationConverter::locationToGetLocationResponse).toList();

        return ResponseEntity.ok(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<GetLocationResponse> getLocation(@PathVariable Long id) {

        Location responseEntity = getLocationUseCase.getLocationById(id);
        GetLocationResponse response = LocationConverter.locationToGetLocationResponse(responseEntity);
            return ResponseEntity.ok(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteLocation(@PathVariable Long id) {

        DeleteResponse response = deleteLocationUseCase.deleteLocation(id);
            return ResponseEntity.ok(response);
    }

}
