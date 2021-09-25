import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.azure.storage.common.StorageSharedKeyCredential;

import java.io.*;
import java.util.Locale;

public class InfaBlob
{
    public static void main(String args[]) throws IOException
    {
        String accountName = args[0];
        String accountKey = args[1];
        String containerName = args[2];
        String blobName = args[3];

        String filePath = "reddit.png";

        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);

        String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName);

        // Create a BlobServiceClient object which will be used to create a container client
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        BlobClient blobClient = containerClient.getBlobClient(blobName);

        File downloadedFile = new File(filePath);

        blobClient.downloadToFile(filePath);
    }
}
