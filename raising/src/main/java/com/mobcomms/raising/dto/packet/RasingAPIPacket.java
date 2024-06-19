package com.mobcomms.raising.dto.packet;

import com.mobcomms.raising.dto.CharacterDto;
import com.mobcomms.raising.dto.GameDto;

//TODO Sample
public class RasingAPIPacket{

    public static class SetCharacter{
        public static class Requset extends CharacterDto {
            private long userSeq;
            private long clientCode;
        }

        public static class Response extends CharacterDto {

        }
    }

    public static class GetGameInfo{
        public static class Requset  {
            private long userSeq;
            private long clientCode;
        }

        public static class Response{
            private GameDto gameDto;
            private CharacterDto characterDto;
        }
    }
}
