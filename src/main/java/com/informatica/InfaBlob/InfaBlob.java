package com.informatica.InfaBlob;

import com.azure.storage.blob.*;
import com.azure.storage.common.StorageSharedKeyCredential;

import java.io.*;
import java.util.Locale;

public class InfaBlob
{
    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            System.out.println("Usage is java -jar <JarName> accountName accountKey containerName blobFileNameWithPath localFilePathWithName");
        }
        else {
            String accountName = args[0];
            String accountKey = args[1];
            String containerName = args[2];
            String blobFileNameWithPath = args[3];
            String localFilePathWithName = args[4];

            StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);

            String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName);

            // Create a BlobServiceClient object which will be used to create a container client
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            BlobClient blobClient = containerClient.getBlobClient(blobFileNameWithPath);

            File downloadedFile = new File(localFilePathWithName);

            blobClient.downloadToFile(localFilePathWithName);
        }
    }
}
