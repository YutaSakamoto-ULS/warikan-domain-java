package warikan.domain.model.party;

import java.util.Map;
import javax.annotation.Nonnull;
import warikan.domain.model.members.Members;
import warikan.domain.model.members.Payment;
import warikan.domain.model.members.PaymentRatio;

/** 飲み会 */
public class Party {
  /** 飲み会名 */
  private final PartyName partyName;
  /** 請求金額 */
  private final TotalPayment totalPayment;
  /** 開催日時 */
  private final PartyDatetime dateTime;
  /** 弱者控除割合 */
  private final LittleRatio littleRatio;

  /** 参加者のリスト */
  private final Members members;

  private Party(
      @Nonnull PartyName partyName,
      TotalPayment totalPayment,
      PartyDatetime dateTime,
      LittleRatio littleRatio,
      Members members) {
    this.partyName = partyName;
    this.totalPayment = totalPayment;
    this.dateTime = dateTime;
    this.littleRatio = littleRatio;
    this.members = members;
  }

  /** ファクトリメソッド */
  @Nonnull
  public static Party of(
      @Nonnull PartyName partyName,
      @Nonnull TotalPayment totalPayment,
      @Nonnull PartyDatetime dateTime,
      @Nonnull LittleRatio littleRatio,
      @Nonnull Members members) {
    return new Party(partyName, totalPayment, dateTime, littleRatio, members);
  }

  /** 支払い金額を表示する */
  public void display() {
    System.out.println(
        String.format("%s (開催日：%s)の割り勘表", this.partyName.asString(), this.dateTime.format()));
    this.members.displayMembers();
  }
  

  public WarikanResults calcWarikanResults() {
	  long muchNum = members.sizeOf(PaymentRatio.MUCH); // 多めの人の人数
      long meanNum = members.sizeOf(PaymentRatio.MEAN); // 普通の人の人数
      long littleNum = members.sizeOf(PaymentRatio.LITTLE); // 少なめの人の人数
      long memberNum = members.size(); // 合計人数

      var meanPayment = totalPayment.divide(memberNum); // 普通の人の支払金額 = 平均金額 = 請求金額 / 合計人数
      var littlePayment = meanPayment.times(littleRatio.amount()); // 少な目の人の支払金額 = 平均金額 * 弱者控除割合
      var muchPayment = totalPayment.subtract(meanPayment.times(meanNum)).subtract(littlePayment.times(littleNum)).divide(muchNum); // 多めの人の支払金額 = (請求金額 - 普通の人の支払金額合計 -少な目の人の支払金額合計) / 多めの人の人数  
      var paymentMap =  new PaymentMap(Map.of( // 支払い区分と支払金額の対応
            PaymentRatio.MEAN, Payment.of(meanPayment), 
            PaymentRatio.LITTLE, Payment.of(littlePayment),
            PaymentRatio.MUCH, Payment.of(muchPayment)));
      return members.calcWarikanResults(paymentMap);
  }
}
