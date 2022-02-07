package warikan.domain.model.party;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.management.modelmbean.ModelMBeanInfoSupport;

import warikan.domain.model.Money;
import warikan.domain.model.members.Member;
import warikan.domain.model.members.Members;
import warikan.domain.model.members.Payment;
import warikan.domain.model.members.PaymentRatio;

public class Party {
  private final PartyName partyName;
  private final TotalPayment totalPayment; // TODO: 請求金額用の値オブジェクトを作成。その中でMoneyを使用
  private final PartyDatetime dateTime;
  private final LittleRatio littleRatio; // TODO: 弱者控除割合用の値オブジェクトを作成。その中でMoneyを使用

  private Members members = Members.of(new ArrayList<Member>());

  // メンバー追加
  public void addMember(Member member){
    this.members.addMember(member);
  }

  public void decidePayment(){

    // 支払い区分と支払金額の対応
    Map<PaymentRatio,Payment> paymentMap = new HashMap<PaymentRatio,Payment>();

    // 多めの人の人数
    long muchNum = this.members.sizeOfMuch();
    // 普通の人の人数
    long meanNum = this.members.sizeOfMean();
    // 少なめの人の人数
    long littleNum = this.members.sizeOfLittle();
    // 合計人数
    long memberNum = muchNum + meanNum + littleNum;
    
    // 平均金額を決定
    Payment averagePayment = totalPayment.devide(memberNum);
    paymentMap.put(PaymentRatio.Mean, averagePayment);

    // 弱者控除
    Payment littlePayment = averagePayment.devide(littleRatio);
    paymentMap.put(PaymentRatio.Little, littlePayment);

    // 負担すべき差額計算
    Payment meanMembersTotalPayment = averagePayment.multiple(meanNum);
    Payment littleMembersTotalPayment = littlePayment.multiple(littleNum);
    Payment muchMembersTotalPayment = totalPayment.subtract(meanMembersTotalPayment).subtract(littleMembersTotalPayment);
    
    // 多めの人の支払金額を決定
    Payment muchPayment = muchMembersTotalPayment.devide(muchNum);
    paymentMap.put(PaymentRatio.Much,muchPayment);

    // 各メンバーに支払金額を割り振る
    this.members = this.members.stream().map(member -> member.setPayment(paymentMap.get(member.paymentRatio()))).toList();
  }

  private Party(@Nonnull PartyName partyName, TotalPayment totalPayment, PartyDatetime dateTime, LittleRatio littleRatio){
    this.partyName = partyName;
    this.totalPayment = totalPayment;
    this.dateTime = dateTime;
    this.littleRatio = littleRatio;
  }

  /**
   * ファクトリメソッド
   */
  @Nonnull
  public static Party of(@Nonnull PartyName partyName, @Nonnull TotalPayment totalPayment,
  @Nonnull PartyDatetime dateTime, @Nonnull LittleRatio littleRatio){
    return new Party(partyName, totalPayment, dateTime, littleRatio);
  }
}