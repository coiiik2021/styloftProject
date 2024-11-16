package org.sale.project.dto.response;
import java.beans.PropertyEditorSupport;

public class CustomDoubleEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.trim().isEmpty()) {
            setValue(null); // Nếu không có giá trị, trả về null
        } else {
            try {
                // Thử chuyển đổi giá trị
                setValue(Double.parseDouble(text));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Giá trị không hợp lệ cho Discount Value: " + text);
            }
        }

    }
}

