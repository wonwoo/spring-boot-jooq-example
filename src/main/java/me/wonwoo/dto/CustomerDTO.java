package me.wonwoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by wonwoo on 2017. 2. 19..
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
  private Integer id;
  private String name;
  private String email;
  private Collection<ProductDTO> products = new HashSet<>();
}
