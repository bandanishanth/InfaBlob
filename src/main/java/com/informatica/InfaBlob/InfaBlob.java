package com.informatica.InfaBlob;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobItem;
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

    private static void upload(String blobUploadPathWithName, String localPathWithFileName, BlobContainerClient containerClient)
    {
        //Get a Blob Client with the BlobPathAndName
        BlobClient blobClient = containerClient.getBlobClient(blobUploadPathWithName);

        UploadBlob uploadBlob = new UploadBlob(blobClient);

        uploadBlob.upload(localPathWithFileName);
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
                System.out.println("Enter Local File Name to download blob with it's path: ");
                String localPathWithFileName = scanner.nextLine();

                download(blobFileNameWithPath,localPathWithFileName,containerClient);
            }

            else if (action.equalsIgnoreCase("upload"))
            {
                Scanner scanner = new Scanner(System.in);

                //Path inside blob where we want to upload the file
                System.out.println("Enter Path in Container with File Name for Upload: ");
                String blobUploadPathWithName = scanner.nextLine();

                //Local File Path and Name to Upload
                System.out.println("Enter Local File's Name and Path to be uploaded to Blob: ");
                String localPathWithFileName = scanner.nextLine();

                upload(blobUploadPathWithName, localPathWithFileName, containerClient);
            }

            else if(action.equalsIgnoreCase("list"))
            {
                System.out.println("Listing Blobs in container " + containerName + ":");

                for(BlobItem blobItem : containerClient.listBlobs())
                {
                    System.out.println(blobItem.getName());
                }
            }

            else
            {
                System.out.println("Invalid Action");
            }
        }
    }
}
