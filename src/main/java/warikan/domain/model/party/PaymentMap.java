package warikan.domain.model.party;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import warikan.domain.model.members.Payment;
import warikan.domain.model.members.PaymentRatio;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PaymentMap {

	protected Map<PaymentRatio, Payment> map;
	
	public Payment get(PaymentRatio ratio) {
		return map.get(ratio);
	}
}
