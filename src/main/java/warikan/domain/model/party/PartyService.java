package warikan.domain.model.party;

import java.util.HashMap;

import warikan.domain.model.Money;
import warikan.domain.model.members.Payment;
import warikan.domain.model.members.PaymentRatio;
import warikan.domain.model.members.PaymentService;

public final class PartyService {
  public static Party decidePayment(Party party){

      // 支払い区分と支払金額の対応
      var paymentMap = new HashMap<PaymentRatio,Payment>();
  
      // 多めの人の人数
      long muchNum = party.members().sizeOfMuch();
      // 普通の人の人数
      long meanNum = party.members().sizeOfMean();
      // 少なめの人の人数
      long littleNum = party.members().sizeOfLittle();
      // 合計人数
      long memberNum = muchNum + meanNum + littleNum;
      
      // 平均金額を決定
      Payment meanPayment = PaymentService.calculateMeanMembersPayment(party.totalPayment(),memberNum);
      paymentMap.put(PaymentRatio.Mean, meanPayment);
  
      // 弱者控除
      Payment littlePayment = PaymentService.calculateLittleMembersPayment(meanPayment, party.littleRatio());
      paymentMap.put(PaymentRatio.Little, littlePayment);
  
      // 多めの人の支払金額を決定
      Money meanMembersTotalPayment = meanPayment.times(meanNum);
      Money littleMembersTotalPayment = littlePayment.times(littleNum);
      Payment muchPayment = PaymentService.calculateMuchMembersPayment(littleMembersTotalPayment, meanMembersTotalPayment, party.totalPayment(), muchNum);
      
      paymentMap.put(PaymentRatio.Much,muchPayment);
  
      // 各メンバーに支払金額を割り振る
      party.setPayment(paymentMap);

      return party;
  }
}
