package com.service.file.service;

import com.service.common.crypto.SymmetricKeyCrypto;
import com.service.common.domain.File;
import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.domain.fabric.file.FileAsset;
import com.service.common.exceptions.CryptoException;
import com.service.common.repository.FileRepository;
import com.service.common.service.fabric.account.GetAccountAssetByIdService;
import com.service.common.service.fabric.file.GetFileAssetByIdService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
public class GetFileByIdService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private GetBucketFileService getBucketFileService;

    @Autowired
    private GetFileAssetByIdService getFileAssetByIdService;

    @Autowired
    private GetAccountAssetByIdService getAccountAssetByIdService;

    public String getfile(Long id) throws IOException, CryptoException, InvalidArgumentException, ProposalException {
        File file = fileRepository.findById(id).get();

        List<FileAsset> fileAssets = getFileAssetByIdService.getFileAssetById(id);
        AccountAsset fileRequester = getAccountAssetByIdService.getUserAssetById(file.getOwner().getAccount().getId());

        fileAssets.sort(
                new Comparator<FileAsset>() {
                    @Override
                    public int compare(FileAsset fileA, FileAsset fileB) {
                        Long createdAtA = fileA.getCreatedAt();
                        Long createdAtB = fileB.getCreatedAt();

                        return createdAtA > createdAtB ? -1 : 1;
                    }
                }
        );

        FileAsset fileAsset = fileAssets.get(0);

        SecretKey decryptedSymmetricKey =
                SymmetricKeyCrypto.decryptKey(fileAsset.getSymmetricKey(), fileRequester);

        String fileBase = getBucketFileService.getFileFromBucket(
                file.getBucketUrl(),
                file.getType().toString().toLowerCase(),
                file.getMimeType(),
                decryptedSymmetricKey
        );

        return "data:application/" + file.getMimeType() + ";base64," + fileBase;
    }

    private AccountAsset getAccountAssetById(Long id) {
        try {
            return getAccountAssetByIdService.getUserAssetById(id);
        } catch (ProposalException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
