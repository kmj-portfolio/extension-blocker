package com.example.extensionblocker.dto.ui;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCustomExtensionForm {
    @NotBlank(message = "공백이 아닌 확장자 이름을 입력해주세요.")
    @Size(min = 1, max = 20, message = "확장자 이름은 최대 20자 까지 입력 가능하니다.")
    String extensionName;
}
