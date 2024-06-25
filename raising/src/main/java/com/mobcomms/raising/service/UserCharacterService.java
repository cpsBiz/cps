package com.mobcomms.raising.service;

import com.mobcomms.raising.dto.UserCharacterDto;
import com.mobcomms.raising.dto.UserCharacterRegDto;
import com.mobcomms.raising.entity.CharacterEntity;
import com.mobcomms.raising.entity.UserCharacterEntity;
import com.mobcomms.raising.entity.UserCharacterPK;
import com.mobcomms.raising.repository.UserCharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;


/*
 * Created by
 * Create Date :
 * 유저 캐릭터 조회, 유저 캐릭터 등록
 * UpdateDate :
 */
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

    public UserCharacterDto updateUserCharacter(Long userSeq, Long oldCharacterSeq, Long newCharacterSeq) {
        UserCharacterEntity userCharacterEntity =
                userCharacterRepository.findByIdUserSeqAndIdCharacterSeq(userSeq, oldCharacterSeq);

        UserCharacterEntity changedUserCharacterEntity = null;
        if(newCharacterSeq != oldCharacterSeq) {
            this.deleteUserCharacter(userCharacterEntity);

            changedUserCharacterEntity = new UserCharacterEntity();
            changedUserCharacterEntity.setId(new UserCharacterPK(userSeq, newCharacterSeq));
            changedUserCharacterEntity.setExp(userCharacterEntity.getExp());
            changedUserCharacterEntity.setLevel(userCharacterEntity.getLevel());
            changedUserCharacterEntity.setCharacterNickName(userCharacterEntity.getCharacterNickName());
            changedUserCharacterEntity.setRegUser(userSeq.toString());
            changedUserCharacterEntity.setModUser(userSeq.toString());

            changedUserCharacterEntity = this.insertUserCharacter(changedUserCharacterEntity);
        }

        CharacterEntity characterEntity = characterService.selectCharacter(newCharacterSeq);
        return UserCharacterDto.of(changedUserCharacterEntity, characterEntity);
    }


    public void deleteUserCharacter(UserCharacterEntity entity) {
        userCharacterRepository.delete(entity);
    }

    protected UserCharacterEntity selectUserCharacter(Long userSeq, Long characterSeq) {
        return userCharacterRepository.findByIdUserSeqAndIdCharacterSeq(userSeq, characterSeq);
    }

    protected UserCharacterEntity insertUserCharacter(UserCharacterEntity entity) {
        return userCharacterRepository.saveAndFlush(entity);
    }



}
