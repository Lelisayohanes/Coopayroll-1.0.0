package com.lelisay.CooPayroll10.coremodule.subscription.invoice;

import java.util.Optional;

public interface IInvoiceService {
    Invoice addNewInvoice(Invoice invoice);

    Invoice updateInvoiceBilling(Invoice newInvoice);

    Invoice getInvoiceWithCompanyId(Long companyId);

    void approveInvoiceByAdmin(Invoice companyInvoice);

    Optional<Invoice> findById(Long id);
}
