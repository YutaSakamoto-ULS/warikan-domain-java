package warikan.domain.model.members;

import javax.annotation.Nonnull;

import lombok.EqualsAndHashCode;

/** 参加者。 */
@EqualsAndHashCode
public final class Member {
  /** 参加者名 */
  private final MemberName name;
  /** 支払割合 */
  private final PaymentRatio paymentRatio;

  private Member(@Nonnull MemberName name, @Nonnull PaymentRatio paymentRatio) {
    this.name = name;
    this.paymentRatio = paymentRatio;
  }

  /**
   * ファクトリメソッド。
   *
   * @param name {@link MemberName}
   * @param paymentRatio {@link PaymentRatio}
   * @param payment {@link Payment}
   * @return {@link Member}
   */
  @Nonnull
  public static Member of(@Nonnull MemberName name, @Nonnull PaymentRatio paymentRatio) {
    return new Member(name, paymentRatio);
  }

  /**
   * ファクトリメソッド。支払割合を設定する用
   *
   * @param paymentRatio {@link PaymentRatio}
   * @return {@link Member}
   */
  @Nonnull
  public Member of(@Nonnull PaymentRatio paymentRatio) {
    return new Member(this.name, paymentRatio);
  }

  /**
   * ファクトリメソッド。支払金額を設定する用
   *
   * @param paymentR {@link Payment}
   * @return {@link Member}
   */
  @Nonnull
  public Member of() {
    return new Member(this.name, this.paymentRatio);
  }

  /** getter */
  @Nonnull
  public MemberName name() {
    return this.name;
  }

  /** getter */
  @Nonnull
  public PaymentRatio paymentRatio() {
    return this.paymentRatio;
  }

//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//    if (o == null || getClass() != o.getClass()) return false;
//    Member member = (Member) o;
//    return Objects.equals(name, member.name);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(name);
//  }

  @Override
  public String toString() {
    return String.format("%s", this.name);
  }
}
