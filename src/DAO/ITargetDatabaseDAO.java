package DAO;

import rule.BusinessRule;
import trigger.TriggerEvent;
import database.Scheme;

public interface ITargetDatabaseDAO {
	 Scheme getAllTables(Scheme s);
	 Scheme getAllColumns(Scheme s);
	 void writeTriggerToTargetDatabase(TriggerEvent ts);
}
