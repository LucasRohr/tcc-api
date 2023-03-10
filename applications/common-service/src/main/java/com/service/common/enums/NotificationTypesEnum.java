package com.service.common.enums;

import lombok.Getter;

@Getter
public enum NotificationTypesEnum {
    HEIR_INVITE(true) {
        @Override
        public String getMessage(String ownerName) {
            return "O prorietário " + ownerName + " está convidando você para ser seu herdeiro";
        }
    },

    ACCEPTED_HEIR_INVITE(true) {
        @Override
        public String getMessage(String invitedHeirName) {
            return invitedHeirName + " agora é herdeiro do seu legado digital";
        }
    },

    ACTIVATED_HERITAGE(false) {
        @Override
        public String getMessage(String string) {
            return "O legado digital relacionado à esta conta foi ativado";
        }
    },

    HEIR_DISINHERITANCE(true) {
        @Override
        public String getMessage(String ownerName) {
            return"O proprietário digital " + ownerName + " tirou você da lista de seus herdeiros." +
                    "<br/>Contate esta pessoa para mais informações";
        }
    };

    public abstract String getMessage(String concatText);

    private boolean hasConcat;

    NotificationTypesEnum(boolean hasConcat) {
        this.hasConcat = hasConcat;
    }
}
