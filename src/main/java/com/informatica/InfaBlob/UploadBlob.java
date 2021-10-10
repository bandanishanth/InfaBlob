package com.informatica.InfaBlob;

import com.azure.storage.blob.BlobClient;

public class UploadBlob
{
    private final BlobClient blobClient;

    public UploadBlob(BlobClient blobClient)
    {
        this.blobClient = blobClient;
    }

    void upload(String localPathWithFileName)
    {
        blobClient.uploadFromFile(localPathWithFileName);
    }
}
