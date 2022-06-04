package ru.maxima.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maxima.demo.model.Account;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private Long id;
    private String email;

    public static AccountDto from(Account entity) {
        return AccountDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .build();
    }

    public static List<AccountDto> fromList(List<Account> entities) {
        return entities.stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }
}
