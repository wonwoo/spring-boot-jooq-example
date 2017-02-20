package me.wonwoo.repository;

import me.wonwoo.domain.tables.Customer;
import me.wonwoo.domain.tables.Product;
import me.wonwoo.dto.CustomerDTO;
import me.wonwoo.dto.ProductDTO;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Result;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Created by wonwoo on 2017. 2. 19..
 */
@Repository
@Transactional
public class CustomerRepository {

  private final DSLContext dslContext;

  public CustomerRepository(DSLContext dslContext) {
    this.dslContext = dslContext;
  }

  public void save(String name, String email) {
    this.dslContext.insertInto(Customer.CUSTOMER)
      .columns(Customer.CUSTOMER.NAME, Customer.CUSTOMER.EMAIL)
      .values(name, email);
  }

  public Optional<CustomerDTO> findOne(Integer seq) {
    final Map<Record, Result<Record>> recordResultMap = this.dslContext.select().from(Customer.CUSTOMER)
      .leftJoin(Product.PRODUCT)
      .on(Customer.CUSTOMER.ID.eq(Product.PRODUCT.CUSTOMER_ID))
      .where(Customer.CUSTOMER.ID.eq(seq))
      .fetch()
      .intoGroups(Customer.CUSTOMER.fields());
    return getCollect(recordResultMap).findFirst();
  }

  public Collection<CustomerDTO> findAll() {
    final Map<Record, Result<Record>> recordResultMap = this.dslContext.select().from(Customer.CUSTOMER)
      .leftJoin(Product.PRODUCT)
      .on(Customer.CUSTOMER.ID.eq(Product.PRODUCT.CUSTOMER_ID))
      .fetch()
      .intoGroups(Customer.CUSTOMER.fields());
    return getCollect(recordResultMap).collect(toList());
  }

  private Stream<CustomerDTO> getCollect(Map<Record, Result<Record>> recordResultMap) {
    return recordResultMap
      .values()
      .stream()
      .map(records -> {
        final Record3<Integer, String, String> record3 = records.into(Customer.CUSTOMER.ID, Customer.CUSTOMER.NAME, Customer.CUSTOMER.EMAIL).get(0);
        final Integer customerId = record3.value1();
        final String name = record3.value2();
        final String email = record3.value3();
        List<ProductDTO> products = records.sortAsc(Customer.CUSTOMER.ID).into(ProductDTO.class)
          .stream()
          .filter(productDTO -> productDTO.getId() != null)
          .collect(toList());
        return new CustomerDTO(customerId, name, email, products);
      });
  }
}
