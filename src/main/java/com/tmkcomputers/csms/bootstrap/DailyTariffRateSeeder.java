package com.tmkcomputers.csms.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.tmkcomputers.csms.model.DailyTariffRate;
import com.tmkcomputers.csms.repository.DailyTariffRateRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DailyTariffRateSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final DailyTariffRateRepository dailyTariffRateRepository;

    public DailyTariffRateSeeder(DailyTariffRateRepository dailyTariffRateRepository) {
        this.dailyTariffRateRepository = dailyTariffRateRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedDailyTariffRates();
    }

    private void seedDailyTariffRates() {
        if (dailyTariffRateRepository.count() == 0) {
            List<DailyTariffRate> rates = Arrays.asList(
                    new DailyTariffRate(null, LocalDate.now().minusDays(1), new BigDecimal("150.00"), "Previous day rate"),
                    new DailyTariffRate(null, LocalDate.now(), new BigDecimal("200.00"), "Today's rate"),
                    new DailyTariffRate(null, LocalDate.now().plusDays(1), new BigDecimal("250.00"), "Next day rate")
            );

            dailyTariffRateRepository.saveAll(rates);
        }
    }
}

