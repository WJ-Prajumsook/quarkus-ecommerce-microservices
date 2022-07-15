package org.wj.prajumsook.eshop.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Address {

  private Long id;
  private String street;
  private String city;
  private String zipcode;
  private String country;

}
