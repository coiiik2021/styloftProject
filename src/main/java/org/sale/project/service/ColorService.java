package org.sale.project.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Color;
import org.sale.project.entity.ProductVariant;
import org.sale.project.repository.ColorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ColorService {
    ColorRepository colorRepository;

    public List<Color> findAll() {
        return colorRepository.findAll();
    }
    public Color findById(String id) {
        return colorRepository.findById(id).orElse(null);
    }

    public Color saveColor(Color color) {
        return colorRepository.save(color);
    }

    public void deleteById(String id) {
        colorRepository.deleteById(id);
    }

    public Color findByName(String name) {
        return colorRepository.findByName(name);
    }

    public Page<Color> findAll(Pageable pageable) {
        return colorRepository.findAll(pageable);
    }

    public List<Color> findByProductVariant(ProductVariant productVariant) {
        return colorRepository.findAllByProductVariants(productVariant);
    }
}
