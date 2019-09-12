package uk.gov.dhsc.htbhf.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import uk.gov.dhsc.htbhf.page.PageName;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class ToggleConfiguration {

    private Map<String, Boolean> toggles;
    private ObjectMapper objectMapper;

    public ToggleConfiguration(String featureToggleJson) {
        objectMapper = new ObjectMapper();
        toggles = loadToggles(featureToggleJson);
    }

    private Map<String, Boolean> loadToggles(String featureToggleJson) {
        if (StringUtils.isBlank(featureToggleJson)) {
            log.info("No toggles JSON found in environment variable FEATURE_TOGGLES defaulting to no toggles");
            return new HashMap<>();
        }
        return toggles = readToggleJson(featureToggleJson);
    }

    private Map<String, Boolean> readToggleJson(String featureToggleJson) {
        try {
            Map<String, Boolean> toggleMap = objectMapper.readValue(featureToggleJson, new TypeReference<Map<String, Boolean>>() {
            });
            log.info("Using toggles: {}", toggleMap);
            return toggleMap;
        } catch (Exception e) {
            log.error("Problem occurred reading in the toggles JSON [" + featureToggleJson + "], defaulting to no toggles", e);
            return new HashMap<>();
        }
    }

    public Map<String, Boolean> getAllToggles() {
        return toggles;
    }

    public boolean isEnabled(String toggle) {
        return toggles.getOrDefault(toggle, false);
    }

    public boolean isPageEnabled(PageName pageName) {
        Optional<String> toggle = pageName.getToggle();
        return toggle.map(this::isEnabled).orElse(true);
    }
}
