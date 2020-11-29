package ru.geekbrains.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SessionData implements Serializable {

  private Integer productId;

  private ProductDto productDto;

  private Integer qty;

  @JsonIgnore
  public BigDecimal getTotal() {
    return productDto.getPrice().multiply(new BigDecimal(qty));
  }
}
