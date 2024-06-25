package com.mobcomms.raising.dto.packet;

import com.mobcomms.common.model.BaseRequset;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.raising.dto.CharacterDto;
import com.mobcomms.raising.dto.SaveResDto;
import com.mobcomms.raising.dto.UserCharacterDto;
import com.mobcomms.raising.dto.UserCharacterRegDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
            private CharacterDto characterDto;
        }
    }


    public static class CreateUserCharacter {
        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Request extends BaseRequset {
            private long userSeq;
            private UserCharacterRegDto userCharacterRegDto;
        }
        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<UserCharacterDto> {

        }
    }

    public static class Save {
        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Request extends BaseRequset {
        }

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class Response extends GenericBaseResponse<SaveResDto> {
        }
    }
}

