package com.mobcomms.raising.model;

import com.mobcomms.common.model.BaseRequset;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.raising.dto.UserCharacterDto;
import com.mobcomms.raising.dto.UserCharacterRegDto;
import lombok.Data;

/*
 * Created by enliple
 * Create Date : 2024-06-19
 *
 * UpdateDate : 2024-06-19, 업데이트 내용
 */
public class UserPacket {

    public static class ReadUserCharacter{

        public static class Request{

        }

        public static class Response{
            private CharacterModel characterModel;
        }
    }


    public static class CreateUserCharacter {
        @Data
        public static class Request extends BaseRequset {
            private long userSeq;
            private UserCharacterRegDto userCharacterRegDto;
        }
        @Data
        public static class Response extends GenericBaseResponse<UserCharacterDto> {

        }
    }
}

