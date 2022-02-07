package warikan.domain.model.members;

import java.math.BigDecimal;

import javax.annotation.Nonnull;

import warikan.domain.model.Money;
import warikan.domain.model.party.LittleRatio;
import warikan.domain.model.party.TotalPayment;

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

  /**
   * 多めの人の支払金額を計算する
   * 
   * @param littleMembersTotalPayment 少なめの人の支払合計金額。
   * @param meanMembersTotalPayment   ふつうの人の支払合計金額。
   * @param TotalPayment              請求金額。
   * @param sizeOfTotal               参加者人数。
   * @return Money 多めの人の支払金額
   */

  public static Payment calculateMuchMembersPayment(Money littleMembersTotalPayment, Money meanMembersTotalPayment,
      TotalPayment TotalPayment, long sizeOfTotal) {
    var muchMembersTotalPayment = TotalPayment.subtract(meanMembersTotalPayment).subtract(littleMembersTotalPayment);
    var muchMemberPayment = muchMembersTotalPayment.divide(sizeOfTotal);
    return Payment.of(muchMemberPayment);
  }

  /**
   * ふつうの人の支払金額を計算する
   * 
   * @param totalPayment 請求金額。
   * @param sizeOf       参加者人数。
   * @return Money ふううの人の支払金額
   */
  public static Payment calculateMeanMembersPayment(TotalPayment totalPayment, long sizeOf) {
    return Payment.of(totalPayment.divide(sizeOf));
  }

  /**
   * 少なめ人の支払金額を計算する
   * 
   * @param meanPayment ふつうの人の請求金額。
   * @param littleRatio    弱者控除割合
   * @return Money 少なめ人の支払金額
   */
  public static Payment calculateLittleMembersPayment(Payment meanPayment, LittleRatio littleRatio) {
    return Payment.of(meanPayment.times(littleRatio.amount()));
  }
  
  public String toString(){
    return  String.format("%s円",this.value.amount());
  }

}