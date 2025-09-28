package it.ddalpra.acme.erpApp.item.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

/**
 * DTO for the Item entity from logisticApp.
 * Using Lombok's @Data for boilerplate code (getters, setters, toString, etc.).
 */
@Data
public class Item {

    private UUID id;
    private String codeItem;
    private String description;
    private String codeFamily;
    private String barcode;
    private BigDecimal weight;
    private BigDecimal volume;
    private UnitOfMeasure unitOfMeasure;
    private LocalDateTime creationDate;
    private String creationUser;
    private LocalDateTime modificationDate;
    private String modificationUser;

}