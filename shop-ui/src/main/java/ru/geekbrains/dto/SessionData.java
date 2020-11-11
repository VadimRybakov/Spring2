package ru.geekbrains.dto;

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

  public BigDecimal getTotal() {
    return productDto.getPrice().multiply(new BigDecimal(qty));
  }
}
