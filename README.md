## INFA BLOB

This is a command line based test case to connect to an Azure Blob Container

The utility is a simple JAR file containing the required dependencies

#### Usage

The tool can be invoked via command line as follows:

```
java -jar InfaBlob-jar-with-dependencies.jar AZURE_ACCOUNT_NAME CONTAINER_NAME OPERATION
```

### Authentication

Prior to invoking any of the operations one needs to provide the credentials to allow the utility to successfully connect to Azure Blob

There are two ways to Authenticate with Azure Blob:

1. Using the Account Key / Shared Account Key
2. Using a SAS (Shared Access Signature) Token - Enter the SAS Token without the '?' at the beginning

Example : If the SAS Token is that Azure Gives from the Azure Cloud Console is : "?sv=2020-08-04..."

Then the value to be provided as input is : "sv=2020-08-04..."  



### Operations

The operation option can be the following options:

1. Download - Download blob contents to local file system
2. Upload - Upload file from local filesystem to blob
3. List - List the entries in the blob container

When using any of the options above, one may get the prompts as follows:

- ***Enter Blob Name in Azure with its Path:*** This is a path to the actual file within the Blob Container.

For Example : If there is a file within the container (not inside any particular folder) we can just give the file name as : *foo.tx*

However, if the same "foo.txt" is within a folder you would give the value as : *folderName/foo.txt*

Even if the file is buried somewhere deep inside many folders within, as long as you give the full path to the file inside the container the file can be fetched

- ***Enter Local File Name to download blob with its path:***

Similar to the above but just give the full path of where you want the file in your local filesystem with the desired file name at the end

Example: If you want the file to be downloaded in the folder *C:\TEST* with the name *foo.txt*, then the value to give for the above would be : *C:\TEST\foo.txt*

#### Proxy Options

To make use of a proxy with this JAR, simply set the following proxy flags when invoking the utility with Java

```java -Djava.net.useSystemProxies=true -Dhttps.proxyHost=<HostName> -Dhttps.proxyPort=<port> -Dhttps.proxyUser=username -Dhttps.proxyPassword=password <Usage Command>```