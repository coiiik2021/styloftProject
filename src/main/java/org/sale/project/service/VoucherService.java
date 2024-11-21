package org.sale.project.service;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Voucher;
import org.sale.project.mapper.VoucherMapper;
import org.sale.project.repository.VoucherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherService {

    VoucherRepository voucherRepository;
    VoucherMapper voucherMapper;

    public Voucher saveVoucher(Voucher voucher) {
        if(voucher.getId() == null || findByCode(voucher.getCode()) != null) {
            return voucherRepository.save(voucher);
        }
        return null;
    }

    public void active(String id){
        Voucher voucher = findById(id);
        voucher.setActive(!voucher.getActive());
        voucherRepository.save(voucher);

    }



    public void update(Voucher voucherUpdate){
        Voucher voucher = findById(voucherUpdate.getId());
        System.out.println("Update");
        if(voucher != null) {
            voucherMapper.updateVoucher(voucher, voucherUpdate);
            voucherRepository.save(voucher);
        }
    }

    public Voucher findByCode(String  code) {
        Voucher voucher = voucherRepository.findByCode(code);
        if(voucher != null) {
            if (voucher.getActive()) {
                if (voucher.getEndDate().isBefore(LocalDate.now())) {
                    voucher.setActive(false);
                    return null;// Đặt active = false nếu ngày hết hạn đã qua
                } else {
                    return voucher;
                }
            }
        }
        return null;
    }

    public Page<Voucher> getAllVoucher(Pageable pageable) {
        // Lấy tất cả các voucher từ repository
        Page<Voucher> vouchers = voucherRepository.findAll(pageable);

        // Kiểm tra ngày hết hạn của từng voucher và cập nhật trạng thái nếu hết hạn
        vouchers.getContent().forEach(voucher -> {
            if (voucher.getEndDate().isBefore(LocalDate.now())) {
                voucher.setActive(false);  // Đặt active = false nếu ngày hết hạn đã qua
            }
        });

        // Trả về Page<Voucher> đã được cập nhật
        return vouchers;
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
