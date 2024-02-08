package org.javaacadmey.metro.metro.components;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TicketOffice {
    private Map<LocalDateTime, BigDecimal> cashier = new HashMap<>();

    public void addCheck(LocalDateTime dateTime, Double cost) {
        if (cashier.containsKey(dateTime)) {
            BigDecimal oldNumber = cashier.get(dateTime);
            cashier.put(dateTime, oldNumber.add(BigDecimal.valueOf(cost)));
        } else {
            cashier.put(dateTime, BigDecimal.valueOf(cost));
        }
    }
}
