package com.bbl.app.events;

import org.json.JSONException;

import com.bbl.app.domain.GameCmd;

import io.nadron.event.Events;
import io.nadron.event.NetworkEvent;
import io.nadron.event.impl.DefaultEvent;

@SuppressWarnings("serial")
public class CustomEvent extends DefaultEvent {

	private EventData source;

	@Override
	public EventData getSource() {
		return source;
	}

	public void setSource(EventData source) {
		this.source = source;
	}

	public static NetworkEvent networkEvent(GameCmd cmd, Object data) throws JSONException {
		EventData source = new EventData();
		source.setCmd(cmd.getCode());
		source.setData(data);
		return Events.networkEvent(source);
	}
}
