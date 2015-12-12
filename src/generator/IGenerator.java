package generator;

import javax.servlet.ServletContext;

import rule.AttributeCompareRule;
import rule.AttributeListRule;
import rule.AttributeOtherRule;
import rule.AttributeRangeRule;
import rule.BusinessRule;
import rule.EntityOtherRule;
import rule.InterEntityCompareRule;
import rule.TupleCompareRule;
import trigger.TriggerEvent;

public interface IGenerator {
	TriggerEvent generateTrigger(BusinessRule bs);
	TriggerEvent generateAttributeRangeRule(AttributeRangeRule arng);
	TriggerEvent generateAttributeCompareRule(AttributeCompareRule acmp);
	TriggerEvent generateAttributeListRule(AttributeListRule alis);
	TriggerEvent generateAttributeOtherRule(AttributeOtherRule aoth);
	TriggerEvent generateTupleCompareRule(TupleCompareRule tcmp);
	TriggerEvent generateInterEntityCompareRule(InterEntityCompareRule icmp);
	TriggerEvent generateEntityOtherRule(EntityOtherRule eoth);
	public ServletContext getContext();
	public void setContext(ServletContext context);

}
