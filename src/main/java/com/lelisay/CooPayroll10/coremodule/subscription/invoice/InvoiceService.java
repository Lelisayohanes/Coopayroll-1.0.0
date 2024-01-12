package com.lelisay.CooPayroll10.coremodule.subscription.invoice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService implements IInvoiceService{
    private final IInvoiceRepository invoiceRepository;

    @Override
    public Invoice addNewInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoiceBilling(Invoice newInvoice) {
        return invoiceRepository.save(newInvoice);
    }

    @Override
    public Invoice getInvoiceWithCompanyId(Long companyId) {
        return invoiceRepository.getInvoiceByCompanyProfile_Id(companyId);
    }



    @Override
    public void approveInvoiceByAdmin(Invoice companyInvoice) {
        invoiceRepository.save(companyInvoice);
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }


}
