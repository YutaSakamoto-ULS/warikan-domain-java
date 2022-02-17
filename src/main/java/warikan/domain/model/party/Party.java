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

  /** 各メンバーの支払金額を更新する */
  public Party updatePayment(Map<PaymentRatio, Payment> paymentMap) {
    return of(
        this.partyName,
        this.totalPayment,
        this.dateTime,
        this.littleRatio,
        this.members.updatePayment(paymentMap));
  }

  /** getter */
  public TotalPayment totalPayment() {
    return this.totalPayment;
  }

  /** getter */
  public LittleRatio littleRatio() {
    return this.littleRatio;
  }

  /** getter */
  public Members members() {
    return this.members;
  }

  /** 支払い金額を表示する */
  public void display() {
    System.out.println(
        String.format("%s (開催日：%s)の割り勘表", this.partyName.asString(), this.dateTime.format()));
    this.members.displayMembers();
  }
}
