package org.wj.prajumsook.eshop.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Product {

  private Long id;
  private Category category;
  private String status;
  private String title;
  private String description;
  private String imageUrl;
  private BigDecimal price;

}
