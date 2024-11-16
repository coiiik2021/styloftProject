package org.sale.project.service;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Voucher;
import org.sale.project.repository.VoucherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherService {

    VoucherRepository voucherRepository;

    public Voucher saveVoucher(Voucher voucher) {
        if(voucher.getId() == null || findByCode(voucher.getCode()) != null) {
            return voucherRepository.save(voucher);
        }
        return null;
    }

    public Voucher findByCode(String  code) {
        return voucherRepository.findByCode(code);
    }

    public Page<Voucher> getAllVoucher(Pageable pageable) {
        return voucherRepository.findAll(pageable);
    }


    public Voucher findById(String id) {
        return voucherRepository.findById(id).orElse(null);
    }

    public void deleteVoucher(String id){
        Voucher voucher = findByCode(id);
        if(voucher != null) {
            voucher.setActive(false);
            voucherRepository.save(voucher);
        }
    }



}
