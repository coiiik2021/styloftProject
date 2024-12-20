package org.sale.project.service;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Size;
import org.sale.project.repository.SizeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SizeService {
    SizeRepository sizeRepository;

    public List<Size> findAll() {
        return sizeRepository.findAll();
    }
    public Size findById(String id) {
        return sizeRepository.findById(id).orElse(null);
    }
    public void deleteById(String id) {
        sizeRepository.deleteById(id);
    }


    public void saveSize(Size size) {
        if(size.getId() != null || findByName(size.getName()) == null) {
            sizeRepository.save(size);
        }
    }

    public Size findByName(String name) {
        return sizeRepository.findByName(name);
    }

    public Page<Size> findAll(Pageable pageable) {
        return sizeRepository.findAll(pageable);
    }

//    public Size findByProductId(String productId) {
//        return sizeRepository.findByProducVariantsId(productId);
//    }
}
