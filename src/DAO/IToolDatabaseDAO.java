package DAO;

import rule.BusinessRule;
import trigger.TriggerEvent;
import database.Scheme;

public interface IToolDatabaseDAO {
	boolean authenticate(String key);
	 Scheme getTargetScheme(int id);
	 void writeDatabaseObjectsToToolDatabase(Scheme s);
	 BusinessRule getBusinessRuleFromDatabase(int id);
	 void writeTriggerToToolDatabase(TriggerEvent ts);
	 TriggerEvent getTriggerEvent(int id);

}
