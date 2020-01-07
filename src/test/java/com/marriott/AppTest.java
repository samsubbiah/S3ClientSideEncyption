package com.mycompany;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    /*@Test
    public void testMasterKeyCreation(){

        CustomerMasterKey cmk=new CustomerMasterKey();

        cmk.createCustomerMasterKey();
    }*/

    @Test
    public void testS3CSEUpload(){

        S3Uploader uploader=new S3Uploader();

        uploader.uploadToS3UsingEncryption();
    }

    @Test
    public void testS3CSEDownload(){

        S3Downloader downloader=new S3Downloader();

        downloader.downloadFromS3AndDecrypt();
    }
}
