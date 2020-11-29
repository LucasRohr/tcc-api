package com.service.user.service;

import com.service.common.domain.User;
import com.service.common.domain.fabric.user.UserRecordModel;
import com.service.common.helpers.CryptoUtils;
import com.service.common.repository.UserRepository;
import com.service.common.service.fabric.user.SaveUserAssetService;
import com.service.user.controller.request.UpdateProfileRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaveUserAssetService saveUserAssetService;

    public void updateProfile(UpdateProfileRequest updateProfileRequest)
            throws InvalidArgumentException, ProposalException {

        User user = userRepository.findById(updateProfileRequest.getId()).get();

        user.setName(updateProfileRequest.getName());
        user.setEmail(updateProfileRequest.getEmail());

        userRepository.save(user);

        UserRecordModel userRecordModel = new UserRecordModel(
                updateProfileRequest.getId(),
                CryptoUtils.encryptSimpleString(updateProfileRequest.getCpf()),
                updateProfileRequest.getBirthday(),
                LocalDateTime.now()
        );

        saveUserAssetService.createTransaction(userRecordModel);
    }

}
