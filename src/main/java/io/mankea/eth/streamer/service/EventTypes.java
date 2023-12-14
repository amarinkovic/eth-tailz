package io.mankea.eth.streamer.service;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.datatypes.Event;

import java.util.HashMap;
import java.util.Map;

import static io.mankea.eth.streamer.service.NaymsDiamond.*;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

public class EventTypes {

  private static final Map<String, Event> EVENT_TYPES = Map.copyOf(new HashMap<>() {{
    put(DIVIDENDDISTRIBUTION_EVENT.getName(), DIVIDENDDISTRIBUTION_EVENT);
    put(DIVIDENDWITHDRAWN_EVENT.getName(), DIVIDENDWITHDRAWN_EVENT);
    put(ENTITYCREATED_EVENT.getName(), ENTITYCREATED_EVENT);
    put(ENTITYUPDATED_EVENT.getName(), ENTITYUPDATED_EVENT);
    put(ORDERADDED_EVENT.getName(), ORDERADDED_EVENT);
    put(ORDERCANCELLED_EVENT.getName(), ORDERCANCELLED_EVENT);
    put(ORDEREXECUTED_EVENT.getName(), ORDEREXECUTED_EVENT);
    put(ROLEGROUPUPDATED_EVENT.getName(), ROLEGROUPUPDATED_EVENT);
    put(ROLEUPDATED_EVENT.getName(), ROLEUPDATED_EVENT);
    put(SIMPLEPOLICYCANCELLED_EVENT.getName(), SIMPLEPOLICYCANCELLED_EVENT);
    put(SIMPLEPOLICYCLAIMPAID_EVENT.getName(), SIMPLEPOLICYCLAIMPAID_EVENT);
    put(SIMPLEPOLICYCREATED_EVENT.getName(), SIMPLEPOLICYCREATED_EVENT);
    put(SIMPLEPOLICYMATURED_EVENT.getName(), SIMPLEPOLICYMATURED_EVENT);
    put(SIMPLEPOLICYPREMIUMPAID_EVENT.getName(), SIMPLEPOLICYPREMIUMPAID_EVENT);
    put(SUPPORTEDTOKENADDED_EVENT.getName(), SUPPORTEDTOKENADDED_EVENT);
    put(TOKENSALESTARTED_EVENT.getName(), TOKENSALESTARTED_EVENT);
  }});

  private static final Map<String, Event> eventNamesByHash = EVENT_TYPES.values().stream()
    .collect(toMap(EventEncoder::encode, identity()));

  public static Map<String, Event> get() {
    return EVENT_TYPES;
  }

  public static Event getByHash(String eventHash) {
    return eventNamesByHash.get(eventHash);
  }
}
