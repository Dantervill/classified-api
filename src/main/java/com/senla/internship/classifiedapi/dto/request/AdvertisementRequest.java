package com.senla.internship.classifiedapi.dto.request;

import com.senla.internship.classifiedapi.model.advertisement.Condition;
import com.senla.internship.classifiedapi.model.advertisement.Brand;
import com.senla.internship.classifiedapi.model.advertisement.Type;
import com.senla.internship.classifiedapi.bean.validation.constraints.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * The {@code AdvertisementRequest} class is used for obtaining data during
 * the advertisement management processes. The class is associated with the
 * {@link com.senla.internship.classifiedapi.model.advertisement.Advertisement}
 * model object.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementRequest {
    @NotNull(message = "Type should not be null.")
    @NotEmpty(message = "Type should not be empty.")
    @ValueOfEnum(enumClass = Type.class, message = "Type is not valid.")
    private String type;

    @NotNull(message = "Title should not be null.")
    @NotEmpty(message = "Title should not be empty.")
    private String title;

    @NotNull(message = "Condition should not be null.")
    @NotEmpty(message = "Condition should not be empty.")
    @ValueOfEnum(enumClass = Condition.class, message = "Condition is not valid.")
    private String condition;

    @NotNull(message = "Brand should not be null.")
    @NotEmpty(message = "Brand should not be empty.")
    @ValueOfEnum(enumClass = Brand.class, message = "Brand is not valid.")
    private String brand;

    @NotNull(message = "Description should not be null.")
    @NotEmpty(message = "Description should not be empty.")
    private String description;

    @NotNull(message = "Price should not be null.")
    @DecimalMin(value = "1.0", message = "Price should be greater or equal to 1.00.")
    @DecimalMax(value = "1000000.0", message = "Price should be less or equal to 1000000.00")
    private BigDecimal price;

    @Valid
    @NotNull(message = "Location should not be null")
    private LocationRequest location;
}
