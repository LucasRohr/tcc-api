package com.service.common.helpers;

import com.service.common.domain.fabric.FabricUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

@Component
public class FabricUserDeserializer {

    private String fabricUserDirectory;

    public FabricUserDeserializer(@Value("${fabric.userDirectoryPath}") final String fabricUserDirectory) {
        this.fabricUserDirectory = fabricUserDirectory;
    }

    public FabricUser readFabricUser(final String affiliation, final String username) throws Exception {
        final String filePath = fabricUserDirectory + "/" + affiliation + "/" + username + ".ser";
        final File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        final FileInputStream fileStream = new FileInputStream(filePath);
        final ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
        final FabricUser fabricUserModel = (FabricUser) objectInputStream.readObject();

        objectInputStream.close();
        fileStream.close();

        return fabricUserModel;
    }
}

