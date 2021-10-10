package com.informatica.InfaBlob;

import com.azure.storage.blob.*;
import com.azure.storage.common.StorageSharedKeyCredential;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class InfaBlob
{
    private static void download(String blobFileNameWithPath, String localPathWithFileName, BlobContainerClient containerClient)
    {
        //Get a BlobClient referring to the blob from Azure to pass to download class so that it can be downloaded locally
        BlobClient blobClient = containerClient.getBlobClient(blobFileNameWithPath);
        DownloadBlob downloadBlob = new DownloadBlob(blobClient);
        downloadBlob.download(localPathWithFileName);
    }
    public static void main(String[] args) throws IOException {
        if (args.length != 4)
        {
            System.out.println("Usage is java -jar <JarName> accountName accountKey containerName action");
        }
        else
        {
            String accountName = args[0];
            String accountKey = args[1];
            String containerName = args[2];
            String action = args[3];

            StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);

            String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName);

            // Create a BlobServiceClient object which will be used to create a container client
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();

            // Get a container client object to work with the blob container
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            if(action.equalsIgnoreCase("download"))
            {
                Scanner scanner = new Scanner(System.in);

                //A particular file in a particular path in Azure Blob to Download
                System.out.println("Enter Blob Name with Path: ");
                String blobFileNameWithPath = scanner.nextLine();

                //The name and location of the local file created after downloading the blob
                System.out.println("Enter Local File Name to download blob with it's path:");
                String localPathWithFileName = scanner.nextLine();

                download(blobFileNameWithPath,localPathWithFileName,containerClient);
            }
        }
    }
}
