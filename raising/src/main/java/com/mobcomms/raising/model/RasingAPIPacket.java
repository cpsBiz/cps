package com.mobcomms.raising.model;

//TODO Sample
public class RasingAPIPacket{

    public static class SetCharacter{
        public static class Requset extends CharacterModel{
            private long userSeq;
            private long clientCode;
        }

        public static class Response extends CharacterModel{

        }
    }

    public static class GetGameInfo{
        public static class Requset  {
            private long userSeq;
            private long clientCode;
        }

        public static class Response{
            private GameModel gameModel;
            private CharacterModel characterModel;
        }
    }
}
