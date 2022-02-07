package warikan.domain.model.members;

import warikan.domain.model.Money;
import warikan.domain.model.party.LittleRatio;
import warikan.domain.model.party.TotalPayment;

public final class PaymentService {
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
}
