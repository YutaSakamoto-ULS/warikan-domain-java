package warikan.domain.model.members;

import java.math.BigDecimal;

import javax.annotation.Nonnull;

import warikan.domain.model.Money;

/** 支払い金額 */
public final class Payment {
  /** 支払い金額 */
  private final Money value;

  private Payment(@Nonnull Money value) {
    this.value = value;
  }

  /**
   * ファクトリメソッド。
   *
   * @param payment 少なめの支払金額。
   * @return {@link Payment}
   */
  @Nonnull
  public static Payment of(@Nonnull Money payment) {
    return new Payment(payment);
  }

  /** getter */
  @Nonnull
  public Money value() {
    return this.value;
  }

  public Money times(BigDecimal factor){
    return this.value.times(factor);
  }

  public Money times(long factor){
    return this.value.times(factor);
  }
  
  public String toString(){
    return  String.format("%s円",this.value.amount());
  }

}