package com.informatica.InfaBlob;

import com.azure.storage.blob.BlobClient;

import java.io.File;

public class DownloadBlob
{
    private final BlobClient blobClient;

    public DownloadBlob(BlobClient blobClient)
    {
        this.blobClient = blobClient;
    }

    void download(String localPathWithFileName)
    {
        File localFile = new File(localPathWithFileName);

        this.blobClient.downloadToFile(localPathWithFileName);
    }
}
