package nl.inventory_management.controller;

import lombok.RequiredArgsConstructor;
import nl.inventory_management.business.domain.Invoice;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.interfaces.*;
import nl.inventory_management.configuration.security.isauthenticated.IsAuthenticated;
import nl.inventory_management.controller.converter.InvoiceConverter;
import nl.inventory_management.controller.converter.LocationConverter;
import nl.inventory_management.controller.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class InvoiceController {
    private final GetInvoiceUseCase getInvoiceUseCase;


    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("/")
    public ResponseEntity<List<GetInvoiceResponse>> getAllInvoices(){

        List<Invoice> responseEntityList = getInvoiceUseCase.getAllInvoices();
        List<GetInvoiceResponse> response = responseEntityList.stream().map(InvoiceConverter::InvoiceToGetInvoiceResponse).toList();

        return ResponseEntity.ok(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("/{id}/{start}/{end}")
    public ResponseEntity<List<String>> getInvoiceStatisticByProductId(@PathVariable Long id, @PathVariable String start, @PathVariable String end) {


        List<String> response = getInvoiceUseCase.getStatisticByByProductId(id,start,end);
            return ResponseEntity.ok(response);
    }


}
