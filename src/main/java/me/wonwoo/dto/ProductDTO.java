package me.wonwoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by wonwoo on 2017. 2. 19..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "customer")
public class ProductDTO {
  private Integer id;
  private String productName;
  private CustomerDTO customer;

}
