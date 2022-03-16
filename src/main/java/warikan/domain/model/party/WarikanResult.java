package warikan.domain.model.party;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import warikan.domain.model.members.Member;
import warikan.domain.model.members.Payment;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class WarikanResult {

	protected Member member;
	
	protected Payment payment;
}
