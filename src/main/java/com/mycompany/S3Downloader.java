package com.mycompany;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.CreateKeyResult;
import com.amazonaws.services.s3.AmazonS3Encryption;
import com.amazonaws.services.s3.AmazonS3EncryptionClientBuilder;
import com.amazonaws.services.s3.model.CryptoConfiguration;
import com.amazonaws.services.s3.model.KMSEncryptionMaterialsProvider;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class S3Downloader {

    private static final Logger logger = LoggerFactory.getLogger(S3Downloader.class);

    public void downloadFromS3AndDecrypt() {

        String bucketName = "mycodedeploybucketsubbu";
        String keyName = "myCustomKey";
        Regions clientRegion = Regions.US_EAST_1;
        String kms_cmk_id = "df41bb56-3961-438a-a16b-a5e35750be04";

        try {
            BasicAWSCredentials awsCreds = AWSUtility.getBasicCredetials();

            // Create the encryption client.
            KMSEncryptionMaterialsProvider materialProvider = new KMSEncryptionMaterialsProvider(kms_cmk_id);
            CryptoConfiguration cryptoConfig = new CryptoConfiguration()
                    .withAwsKmsRegion(RegionUtils.getRegion(clientRegion.toString()));
            AmazonS3Encryption encryptionClient = AmazonS3EncryptionClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .withEncryptionMaterials(materialProvider)
                    .withCryptoConfiguration(cryptoConfig)
                    .withRegion(clientRegion).build();

            // Download the object. The downloaded object is still encrypted.
            // Decrypt and read the object and close the input stream.
            S3Object downloadedObject = encryptionClient.getObject(bucketName, keyName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(downloadedObject.getObjectContent()));

            StringBuffer buff = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buff.append(line);
            }

            logger.info("Downloaded and decypted content from S3-------->"+ buff.toString());

    }
        catch(
    AmazonServiceException e)

    {
        // The call was transmitted successfully, but Amazon S3 couldn't process
        // it, so it returned an error response.
        logger.error("Amazon service exception-------->" + e);
    } catch(
    SdkClientException e)

    {
        // Amazon S3 couldn't be contacted for a response, or the client
        // couldn't parse the response from Amazon S3.
        logger.error("SdkClientException-------->" + e);
    }
        catch(
                IOException e)

        {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            logger.error("IOException-------->" + e);
        }


}

}