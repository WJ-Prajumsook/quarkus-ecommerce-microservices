package org.wj.prajumsook.eshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Customer {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String telephone;

}
