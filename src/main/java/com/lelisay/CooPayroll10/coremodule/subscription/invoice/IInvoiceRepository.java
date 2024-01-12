package com.lelisay.CooPayroll10.coremodule.subscription.invoice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
    Invoice getInvoiceByCompanyProfile_Id(Long companyId);

}
