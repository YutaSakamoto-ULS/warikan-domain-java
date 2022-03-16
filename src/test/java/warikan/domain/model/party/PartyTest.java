package warikan.domain.model.party;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import warikan.domain.model.Money;
import warikan.domain.model.members.Member;
import warikan.domain.model.members.MemberName;
import warikan.domain.model.members.Members;
import warikan.domain.model.members.Payment;
import warikan.domain.model.members.PaymentRatio;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PartyTest {
	@Test
	public void testWarikan() {
		var shirahama = Member.of(MemberName.of("shirahama"), PaymentRatio.MUCH);
		var sakamoto = Member.of(MemberName.of("sakamoto"), PaymentRatio.MEAN);
		var imuta = Member.of(MemberName.of("imuta"), PaymentRatio.LITTLE);
		var ishii = Member.of(MemberName.of("ishii"), PaymentRatio.LITTLE);
		var party = Party.of(PartyName.of("白濱さん主催D5お疲れパーティ"), 
				TotalPayment.of(Money.of(10000, Money.JPY)), 
				PartyDatetime.of(LocalDateTime.now()), 
				LittleRatio.of(BigDecimal.valueOf(120)), 
				Members.of(List.of(shirahama, sakamoto, imuta, ishii)));
		var warikanResults = party.calcWarikanResults();
		assertThat(warikanResults, is(equalTo(new WarikanResults(List.of(
				new WarikanResult(shirahama, Payment.of(Money.of(1500, Money.JPY))),
				new WarikanResult(sakamoto, Payment.of(Money.of(2500, Money.JPY))),
				new WarikanResult(imuta, Payment.of(Money.of(3000, Money.JPY))),
				new WarikanResult(ishii, Payment.of(Money.of(3000, Money.JPY))))))));
	}
}