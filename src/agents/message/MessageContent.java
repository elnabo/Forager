package agents.message;

import agents.AgentInfo;

public interface MessageContent 
{
	Class<MessageContent> getReplyClass();
}
