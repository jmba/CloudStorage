package com.udacity.jwdnd.course1.cloudstorage.services.credentials;

import com.udacity.jwdnd.course1.cloudstorage.model.entities.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {
    private StatusMessageService statusMessageService;
    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialsService(StatusMessageService statusMessageService, CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.statusMessageService = statusMessageService;
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public void addCredentials(Integer credentialId, String userName, String password, String url, Integer userId) {
        CredentialForm credentials = new CredentialForm();
        credentials.setUsername(userName);
        credentials.setUrl(url);
        String encodedKey = getNewEncryptionKey();
        credentials.setKey(encodedKey);
        credentials.setPassword(encryptionService.encryptValue(password, encodedKey));
        credentials.setCredentialid(credentialId);
        credentials.setUserid(userId);
        credentialsMapper.insertCredentials(credentials);
    }

    private String getNewEncryptionKey() {
        byte[] key = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encodedKey;
    }

    private String getDecryptedPassword(String encryptedPassword) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encryptionService.decryptValue(encryptedPassword, encodedKey);
    }

    public List<CredentialForm> getAllCredentials(Integer userId) {
        return credentialsMapper.getAllCredentials(userId);
    }

    public CredentialForm getCredentials(Integer credentialId) {
        return credentialsMapper.getCredentialsById(credentialId);
    }

    public void deleteCredentials(Integer credentialId) {
        credentialsMapper.deleteCredentials(credentialId);
    }

    public void updateCredentials(CredentialForm credentials, String userName, String password, String url) {
        credentials.setUsername(userName);
        credentials.setPassword(password);
        credentials.setPassword(encryptionService.encryptValue(password, credentials.getKey()));
        credentials.setUrl(url);
        credentialsMapper.updateCredentials(credentials);
    }
}
