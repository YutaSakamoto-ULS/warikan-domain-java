package warikan.domain.model.party;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Nonnull;

import warikan.domain.model.members.Member;
import warikan.domain.model.members.Members;
import warikan.domain.model.members.Payment;
import warikan.domain.model.members.PaymentRatio;

public class Party {
  private final PartyName partyName;
  private final TotalPayment totalPayment;
  private final PartyDatetime dateTime;
  private final LittleRatio littleRatio;

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

  // 支払い金額設定
  public void setPayment(Map<PaymentRatio,Payment> paymentMap){
    this.members.setPayment(paymentMap);
  }

  public TotalPayment totalPayment(){
    return this.totalPayment;
  }

  public LittleRatio littleRatio(){
    return this.littleRatio;
  }

  public Members members() {
    return this.members;
  }


  /**
   * 支払い金額表示
   */
  public void display(){
    System.out.println(String.format("%s (開催日：%s)の割り勘表",this.partyName.asString(),this.dateTime.format()));
    this.members.displayMembers();
    
  }
}