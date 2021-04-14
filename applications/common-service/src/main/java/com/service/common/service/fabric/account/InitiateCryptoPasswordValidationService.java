package com.service.common.service.fabric.account;

import com.service.common.component.fabric.ChannelClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InitiateCryptoPasswordValidationService {

    private ChannelClient channelClient;

    public InitiateCryptoPasswordValidationService(ChannelClient channelClient) {
        this.channelClient = channelClient;
    }
}
