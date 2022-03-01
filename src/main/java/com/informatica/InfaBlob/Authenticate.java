package com.informatica.InfaBlob;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;

import java.util.Locale;

public class Authenticate
{
    public String accountName;

    public Authenticate(String accountName)
    {
        this.accountName = accountName;
    }

    public BlobServiceClient accountKeyAuth(String accountKey)
    {
        String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", this.accountName);

        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);

        // Create a BlobServiceClient object which will be used to create a container client
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();

        return blobServiceClient;
    }

    //Takes a SAS Token Without the '?'
    public BlobServiceClient sasTokenAuth(String sasToken)
    {
        String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", this.accountName);
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(endpoint + "?" + sasToken)
                .buildClient();
        return blobServiceClient;
    }
}
