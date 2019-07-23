package com.gobravery.format;

import java.util.List;
import java.util.Map;

import com.gobravery.format.poi.excel.read.ReadExcelUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    public void testRead(){
    	List<Map<String,Object>> res=ReadExcelUtils.read("src/main/resources/simple/read-t1.xls", "src/main/resources/simple/read-t1.xml");
    	for(Map<String,Object> r:res){
    		System.out.println("-->"+r);
    	}
    }
}
