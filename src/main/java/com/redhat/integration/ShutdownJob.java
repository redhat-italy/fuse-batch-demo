package com.redhat.integration;

import org.apache.camel.Exchange;
import org.apache.camel.spi.InflightRepository;

import java.util.Collection;

public class ShutdownJob implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
      //  DetachedShutdown ds = new DetachedShutdown(exchange.getContext());
      //  Thread t = new Thread(ds);
      //  t.run();
    }
}
