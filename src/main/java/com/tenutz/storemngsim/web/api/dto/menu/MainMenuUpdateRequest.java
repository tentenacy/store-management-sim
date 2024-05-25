package com.tenutz.storemngsim.web.api.dto.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainMenuUpdateRequest {

    private MultipartFile image;

    @NotEmpty
    private String menuName;
    @NotNull
    private Integer price;
    private Integer discountedPrice;
    private Integer additionalPackagingPrice;
    @NotEmpty
    private String packaging;
    @NotNull
    private Boolean outOfStock;
    @NotNull
    private Boolean use;
    @NotNull
    private Boolean ingredientDisplay;
    private String mainMenuNameKor;
    @NotEmpty
    private String highlightType;
    private String showDateFrom;
    private String showDateTo;
    private String showTimeFrom;
    private String showTimeTo;
    private String showDayOfWeek;
    private String eventDateFrom;
    private String eventDateTo;
    private String eventTimeFrom;
    private String eventTimeTo;
    private String eventDayOfWeek;
    private String memoKor;
    private String ingredientDetails;

    @JsonIgnore
    private String imageName;

    @JsonIgnore
    private String imageUrl;
}
