package com.mobcomms.raising.service;

import com.mobcomms.raising.dto.CharacterDto;
import com.mobcomms.raising.dto.CharacterRegDto;
import com.mobcomms.raising.dto.mapper.CharacterMapper;
import com.mobcomms.raising.entity.CharacterEntity;
import com.mobcomms.raising.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Created by
 * Create Date :
 * 모든 캐릭터 조회, 캐릭터 등록,캐릭터 수정(admin)
 * UpdateDate :
 */
@Service
@RequiredArgsConstructor
public class CharacterService {

    private final String FIRST_VIEW_Y = "Y";
    private final String FIRST_VIEW_N = "N";

    private final CharacterRepository characterRepository;

    public List<CharacterDto> readCharacter() {

        CharacterMapper mapper = Mappers.getMapper(CharacterMapper.class);

        return mapper.toDtoList(selectCharacter());
     }

    public CharacterDto createCharacter(CharacterRegDto dto) {
        CharacterEntity entity = CharacterRegDto.toEntity(dto);
        // 임시
        entity.setRegUser("system");
        entity.setModUser("system");

        return CharacterMapper.INSTANCE.toDto(insertCharacter(entity));
    }

    protected CharacterEntity insertCharacter(CharacterEntity entity) {
        return characterRepository.saveAndFlush(entity);
    }

    protected List<CharacterEntity> selectCharacter() {
        return characterRepository.findAllByFirstViewYn(FIRST_VIEW_Y);
    }

    protected CharacterEntity selectCharacter(Long characterSeq) {
        return characterRepository.findByIdAndFirstViewYn(characterSeq, FIRST_VIEW_Y);
    }

}
