package warikan.domain.model.members;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.Validate;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import warikan.domain.model.party.WarikanResult;
import warikan.domain.model.party.WarikanResults;

/** メンバーグループ。 */
@EqualsAndHashCode
@ToString
public final class Members {
  private final List<Member> values;

  private Members(@Nonnull List<Member> values) {
    Validate.notEmpty(values, "values are empty");
    this.values = new ArrayList<>(values);
  }

  @Nonnull
  public static Members of(@Nonnull List<Member> members) {
    return new Members(members);
  }

  /**
   * 要素が含まれるかを返す。
   *
   * @param element 要素
   * @return 含まれる場合true
   */
  public boolean contains(@Nonnull Member element) {
    return values.contains(element);
  }

  /**
   * 要素数を取得する。
   *
   * @return 要素数
   */
  public int size() {
    return values.size();
  }

  /** 参加者を追加する */
  public Members addMember(Member member) {
    var currentMembers = new ArrayList<>(values);
    currentMembers.add(member);
    return new Members(currentMembers);
  }

  /** 参加者を削除する */
  public Members deleteMember(Member member) {
    var currentMembers = new ArrayList<>(values);
    currentMembers.remove(member);
    return new Members(currentMembers);
  }

  public void displayMembers() {
    for (Member member : values) {
      System.out.println(member.toString());
    }
  }

  /** 支払区分が多めの人数を取得する */
  public long sizeOfMuch() {
    return this.values
        .stream()
        .filter(member -> member.isPaymentRatio(PaymentRatio.MUCH))
        .count();
  }

  /** 支払区分がふつうの人数を取得する */
  public long sizeOfMean() {
    return this.values
        .stream()
        .filter(member -> member.isPaymentRatio(PaymentRatio.MEAN))
        .count();
  }

  /** 支払区分が少なめの人数を取得する */
  public long sizeOfLittle() {
    return this.values
        .stream()
        .filter(member -> member.isPaymentRatio(PaymentRatio.LITTLE))
        .count();
  }

  public WarikanResults calcWarikanResults(Map<PaymentRatio, Payment> paymentMap) {
    return new WarikanResults(values.stream()
            .map(member -> new WarikanResult(member, member.calcPayment(paymentMap)))
            .collect(Collectors.toList()));
  }
}
