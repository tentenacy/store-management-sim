package com.tenutz.storemngsim.web.api.storemngsim.dto.optiongroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OptionGroupOptionMappersResponse {

    private List<OptionGroupOptionMapper> optionGroupOptionMappers;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OptionGroupOptionMapper {

        private String storeCode;
        private String optionCode;
        private String optionName;
        private String imageUrl;
        private Integer price;
        private Boolean use;
        private Integer priority;

        @JsonIgnore
        private String imageName;

        public OptionGroupOptionMapper(String storeCode, String optionCode, String optionName, String imageName, Integer price, String use, Integer priority) {
            this.storeCode = storeCode;
            this.optionCode = optionCode;
            this.optionName = optionName;
            this.imageName = imageName;
            this.price = price;
            this.use = useYn(use);
            this.priority = priority;
        }

        public Boolean useYn(String use) {
            if (!StringUtils.hasText(use) || use.equals("Y")) {
                return true;
            } else if(use.equals("N")) {
                return false;
            } else {
                return null;
            }
        }
    }
}
