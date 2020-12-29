package gos.integration.cap.iot.configuration;

public class Configuration {
	private String from;
	private String topic;
	private Queues queues;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public Queues getQueues() {
		return queues;
	}
	public void setQueues(Queues queues) {
		this.queues = queues;
	}
}
