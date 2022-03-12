package com.informatica.InfaBlob;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobItem;
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
        if (args.length != 3)
        {
            System.out.println("Usage is java -jar <JarName> accountName containerName <list|upload|download>");
        }
        else
        {
            String accountName = args[0];
            String containerName = args[1];
            String action = args[2];

            Authenticate authenticate = new Authenticate(accountName);

            BlobServiceClient blobServiceClient;

            System.out.println("Select the Authentication Method you want to use for Azure by entering the corresponding number:");

            System.out.println("1. Authentication using Shared Key");
            System.out.println("2. Authentication using SAS Token");
            System.out.print("Option: ");

            Scanner authScanner = new Scanner(System.in);

            int authOption = Integer.parseInt(authScanner.nextLine());

            if(authOption==1)
            {
                System.out.print("Enter Account Key:");

                String accountKey = authScanner.nextLine();

                blobServiceClient = authenticate.accountKeyAuth(accountKey);
            }
            else
            {
                System.out.print("Enter the SAS Token without the '?' at the beginning:");

                String sasToken = authScanner.nextLine();

                blobServiceClient = authenticate.sasTokenAuth(sasToken);
            }

            // Get a container client object to work with the blob container
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            if(action.equalsIgnoreCase("download"))
            {
                Scanner scanner = new Scanner(System.in);

                //A particular file in a particular path in Azure Blob to Download
                System.out.println("Enter Blob Name in Azure with it's Path: ");
                String blobFileNameWithPath = scanner.nextLine();

                //The name and location of the local file created after downloading the blob
                System.out.println("Enter the name you want the file to be downloaded as locally with it's full path: ");
                String localPathWithFileName = scanner.nextLine();

                download(blobFileNameWithPath,localPathWithFileName,containerClient);
            }

            else if (action.equalsIgnoreCase("upload"))
            {
                Scanner scanner = new Scanner(System.in);

                //Path inside blob where we want to upload the file
                System.out.println("Enter Full Path in Azure Container with File Name desired for Upload: ");
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
