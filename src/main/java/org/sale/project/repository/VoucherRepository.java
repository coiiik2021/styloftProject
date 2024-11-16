package org.sale.project.repository;


import org.sale.project.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {
    Voucher findByCode(String code);


    Page<Voucher> findAll(Pageable pageable);
}
