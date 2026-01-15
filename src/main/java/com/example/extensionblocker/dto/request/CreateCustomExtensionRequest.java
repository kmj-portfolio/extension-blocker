package com.example.extensionblocker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "커스텀 확장자 생성 요청")
public record CreateCustomExtensionRequest(
        @Schema(description = "확장자 이름에는 영문, 숫자, -. _가 허용됩니다.")
        @NotBlank(message = "공백이 아닌 확장자 이름을 입력해주세요.")
        @Size(min = 1, max = 20, message = "확장자 이름은 최대 20자 까지 입력 가능하니다.")
        String extensionName
) {
}
