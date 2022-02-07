package warikan.domain.model.party;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

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
    Payment meanPayment = Payment.calculateMeanMembersPayment(totalPayment,memberNum);
    paymentMap.put(PaymentRatio.Mean, meanPayment);

    // 弱者控除
    Payment littlePayment = Payment.calculateLittleMembersPayment(meanPayment, littleRatio);
    paymentMap.put(PaymentRatio.Little, littlePayment);

    // 多めの人の支払金額を決定
    Money meanMembersTotalPayment = meanPayment.times(meanNum);
    Money littleMembersTotalPayment = littlePayment.times(littleNum);
    Payment muchPayment = Payment.calculateMuchMembersPayment(littleMembersTotalPayment, meanMembersTotalPayment, totalPayment, muchNum);
    
    paymentMap.put(PaymentRatio.Much,muchPayment);

    // 各メンバーに支払金額を割り振る
    this.members = this.members.setPayment(paymentMap);
  }

  /**
   * 支払い金額表示
   */
  public void display(){
    System.out.println(String.format("%s (開催日：%s)の割り勘表",this.partyName.asString(),this.dateTime.format()));
    this.members.displayMembers();
    
  }
}