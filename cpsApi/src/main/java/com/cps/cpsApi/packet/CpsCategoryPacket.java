package com.cps.cpsApi.packet;

import com.cps.common.model.GenericBaseResponse;
import com.cps.cpsApi.dto.CpsCategoryDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 카테고리 등록
 *
 * @date 2024-09-09
 */

@Data
public class CpsCategoryPacket {

    public static class CategoryInfo {

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class CategoryRequest {
            @NotBlank(message = "category 확인")
            private String category;
            @NotBlank(message = "categoryName 확인")
            private String categoryName;
            private int categoryRank;
            private String apiType;
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<CpsCategoryDto> {}

    }
}
