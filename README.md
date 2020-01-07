# S3ClientSideEncyption Usage
S3ClientSideEncyption 

If we want to encrypt the data before sending to S3(Client Side) , Client Side Encryption feature of S3 can be used.  There are multiple ways we can do it. This is a proof of concept to encrypt the data , send to S3 and decrypt  after  retrieving  it. Throughout the transit and storage , data is in encrypted form . 

# How S3 CSE Encryption Works
1.	Customer Master Key(CMK) is created either using AWS Console or Programmatically 
2.	S3 AWS SDK uses Customer Master Key to  create data keys in 2 forms a) Plan text b) Encrypted text using CMK.
3.	S3 AWS SDK uses data key to encrypt the  data and send to S3.
4.	S3 AWS SDK also sets the encrypted data key in S3 object itself.

# How S3 CS Decryption works
1.	S3 AWS SDK retrieves the S3 object along with encrypted data key.
2.	S3 AWS SDK uses CMK to decrypt the data key.
3.	S3 AWS SDK decrypts the data using data key.

#  Steps

Step1 :  Create Customer Master Key.  It can be done by 2 ways 
       a) Login to AWS console and create Customer Master Key  (Below URL is for us east region)
       https://console.aws.amazon.com/kms/home?p=kms&cp=bn&ad=c&region=us-east-1#/kms/keys
       b) Programmatically  using the following Code com.mycompany.CustomerMasterKey

Step2: Encrypt the data using Customer Master Key (in Turn it uses Data key)  and send to S3. 
	Code: com.mycompany.S3Uploader

Step3: Get the data from S3 and decrypt(in turn it uses data key stored in S3 and Customer Master key to decrypt data key) .
	Code : com.mycompany.S3Downloader

# note 

Replace ASW access  key and secret key on AWSUtility with your own access key and secret key . Refer to AWS docs for best practices

https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html

BasicAWSCredentials awsCreds = new BasicAWSCredentials("XXXX", "YYYY");
