package com.tmkcomputers.csms.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.tmkcomputers.csms.model.ConnectorType;
import com.tmkcomputers.csms.repository.ConnectorTypeRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class ConnectorTypeSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final ConnectorTypeRepository connectorTypeRepository;

    public ConnectorTypeSeeder(ConnectorTypeRepository connectorTypeRepository) {
        this.connectorTypeRepository = connectorTypeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedConnectorTypes();
    }

    private void seedConnectorTypes() {
        if (connectorTypeRepository.count() == 0) {
            List<ConnectorType> connectorTypes = Arrays.asList(
                    new ConnectorType(null, "Type 1", "SAE J1772 (Type 1) - Commonly used in North America"),
                    new ConnectorType(null, "Type 2", "IEC 62196 (Type 2) - Commonly used in Europe"),
                    new ConnectorType(null, "CHAdeMO", "DC fast charging standard developed in Japan"),
                    new ConnectorType(null, "CCS", "Combined Charging System (CCS) - Supports both AC and DC charging"),
                    new ConnectorType(null, "Tesla", "Tesla proprietary connector for its vehicles")
            );

            connectorTypeRepository.saveAll(connectorTypes);
        }
    }
}

