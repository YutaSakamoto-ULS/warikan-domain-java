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
  

  /** 支払区分が多めの人数を取得する */
  public long sizeOfMuch() {
    return members.sizeOfMuch();
  }

  /** 支払区分がふつうの人数を取得する */
  public long sizeOfMean() {
      return members.sizeOfMean();
  }

  /** 支払区分が少なめの人数を取得する */
  public long sizeOfLittle() {
      return members.sizeOfLittle();
  }
  
  public WarikanResults calcWarikanResults() {
      var paymentMap = calcPaymentMap();
      return members.calcWarikanResults(paymentMap); // 各メンバーの支払金額を更新する
  }
  
  public Map<PaymentRatio, Payment> calcPaymentMap() {
      long muchNum = sizeOfMuch(); // 多めの人の人数
      long meanNum = sizeOfMean(); // 普通の人の人数
      long littleNum = sizeOfLittle(); // 少なめの人の人数
      long memberNum = muchNum + meanNum + littleNum; // 合計人数

      var meanPayment = totalPayment.divide(memberNum); // 平均金額
      var littlePayment = meanPayment.times(littleRatio.amount()); // 弱者控除
      var muchPayment = totalPayment.subtract(meanPayment.times(meanNum)).subtract(littlePayment.times(littleNum)).divide(muchNum); // 多めの人の支払金額
      return Map.of(PaymentRatio.MEAN, Payment.of(meanPayment), // 支払い区分と支払金額の対応
            PaymentRatio.LITTLE, Payment.of(littlePayment),
            PaymentRatio.MUCH, Payment.of(muchPayment));
  }
}
