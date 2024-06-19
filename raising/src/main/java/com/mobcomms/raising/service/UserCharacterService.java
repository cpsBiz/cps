package com.mobcomms.raising.service;

import com.mobcomms.raising.dto.UserCharacterDto;
import com.mobcomms.raising.dto.UserCharacterRegDto;
import com.mobcomms.raising.entity.CharacterEntity;
import com.mobcomms.raising.entity.UserCharacterEntity;
import com.mobcomms.raising.entity.UserCharacterPK;
import com.mobcomms.raising.repository.UserCharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCharacterService {

    private final CharacterService characterService;

    private final UserCharacterRepository userCharacterRepository;

    public List<UserCharacterDto> readUserCharacter(Long userSeq) {
        return userCharacterRepository.findAllByIdUserSeq(userSeq).stream()
                .map(entity ->
                        UserCharacterDto.of(entity, characterService.selectCharacter(entity.getId().getCharacterSeq())))
                .collect(Collectors.toList());
    }

    public UserCharacterDto createUserCharacter(Long userSeq, UserCharacterRegDto dto) {
        UserCharacterEntity userCharacterEntity = UserCharacterRegDto.toEntity(dto);
        userCharacterEntity.setId(new UserCharacterPK(userSeq, dto.characterSeq()));
        userCharacterEntity.setRegUser("system");
        userCharacterEntity.setModUser("system");

        userCharacterEntity = this.insertUserCharacter(userCharacterEntity);
        CharacterEntity characterEntity =
                characterService.selectCharacter(userCharacterEntity.getId().getCharacterSeq());

        return UserCharacterDto.of(userCharacterEntity, characterEntity);
    }

    protected UserCharacterEntity insertUserCharacter(UserCharacterEntity entity) {
        return userCharacterRepository.saveAndFlush(entity);
    }

}
