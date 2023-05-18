package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.conventer.InvoiceConverter;
import nl.inventory_management.business.conventer.LocationConverter;
import nl.inventory_management.business.domain.Invoice;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.exception.LocationNotFoundException;
import nl.inventory_management.business.interfaces.GetInvoiceUseCase;
import nl.inventory_management.business.interfaces.GetLocationUseCase;
import nl.inventory_management.repository.InvoiceRepository;
import nl.inventory_management.repository.LocationRepository;
import nl.inventory_management.repository.entity.InvoiceEntity;
import nl.inventory_management.repository.entity.LocationEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Transactional
public class GetInvoiceUseCaseImpl implements GetInvoiceUseCase {
    private final InvoiceRepository invoiceRepository;
    @Override
    public List<Invoice> getAllInvoices() {
        List<InvoiceEntity> entities = invoiceRepository.findAll();
        return entities.stream()
                .map(InvoiceConverter::convertToModel).toList();
    }
    @Override
    public List<String> getStatisticByByProductId(Long id,String start,String end) {
        LocalDateTime startDate;
        LocalDateTime endDate;
        try{
            startDate = LocalDateTime.parse(start);
            endDate = LocalDateTime.parse(end);
        }catch (Exception e){
            return Stream.of(
                            "Pls"
                            , " Select"
                            , "Another"
                            , "Dates")
                    .collect(Collectors.toList());
        }


        List<Invoice> allInvoices = invoiceRepository.findAll()
                .stream().map(InvoiceConverter::convertToModel).toList();
        return getInfo(allInvoices,id,startDate,endDate);

    }

    private List<String> getInfo(List<Invoice> invoices,Long id,LocalDateTime start, LocalDateTime end){
        try{
        String productName =invoices.stream()
                .filter(x->x.getProductId().equals(id) & x.getDate().isAfter(start) & x.getDate().isBefore(end))
                .findFirst().get().getProductName();

        Integer totalAmountOfProduct = invoices.stream()
                .filter(x->x.getProductId().equals(id)& x.getDate().isAfter(start) & x.getDate().isBefore(end))
                .map(x->x.getAmount())
                .reduce(0, (subtotal, element) -> subtotal + element);

        Integer totalAmountOfGeneral = invoices.stream()
                .filter(x-> x.getDate().isAfter(start) & x.getDate().isBefore(end))
                .map(x->x.getAmount())
                .reduce(0, (subtotal, element) -> subtotal + element);

        long totalCount = invoices.stream()
                .filter(x->x.getProductId().equals(id)& x.getDate().isAfter(start) & x.getDate().isBefore(end))
                .count();
        long totalCountGeneral = invoices.stream()
                .filter(x-> x.getDate().isAfter(start) & x.getDate().isBefore(end))
                .count();

        long amountPerTransactionProduct = totalAmountOfProduct/totalCount;

        long amountPerTransactionGeneral = totalAmountOfGeneral/totalCountGeneral;

        String difference = amountPerTransactionProduct > amountPerTransactionGeneral ?
                amountPerTransactionProduct-amountPerTransactionGeneral + " more " :
                amountPerTransactionGeneral-amountPerTransactionProduct + " less ";

        return Stream.of(
                "There are total "+ totalCount +" transactions with " + productName + " in given dates."
                        , " The total amount of the transaction is " + totalAmountOfProduct + "."
                        , "Which means there are " + amountPerTransactionProduct + " items per transaction."
                        , "That means its " + difference+ "than general average")
                .collect(Collectors.toList());}
        catch (Exception e){
            return Stream.of(
                            "nothing"
                            , " found"
                            , "with given"
                            , "data")
                    .collect(Collectors.toList());}
        }


    }


