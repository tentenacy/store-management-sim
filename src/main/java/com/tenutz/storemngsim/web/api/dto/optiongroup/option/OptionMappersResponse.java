package com.tenutz.storemngsim.web.api.dto.optiongroup.option;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionMappersResponse {

    private List<OptionMapper> optionMappers;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class OptionMapper {

        private String optionGroupCode;
        private String optionName;
        private boolean toggleSelect;
        private boolean required;
        private Integer priority;

        public OptionMapper(String optionGroupCode, String optionName, String toggleSelect, String required, Integer priority) {
            this.optionGroupCode = optionGroupCode;
            this.optionName = optionName;
            this.toggleSelect = toggleYn(toggleSelect);
            this.required = mustSelectYn(required);
            this.priority = priority;
        }

        public boolean mustSelectYn(String mustSelectYn) {
            return StringUtils.hasText(mustSelectYn) && mustSelectYn.equals("Y");
        }

        public boolean toggleYn(String toggleYn) {
            return StringUtils.hasText(toggleYn) && toggleYn.equals("Y");
        }
    }
}
