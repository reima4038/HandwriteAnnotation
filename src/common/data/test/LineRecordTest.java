package common.data.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import common.data.LineRecord;

public class LineRecordTest {

	private static final int DEFAULT_VALUE = -1;
	private LineRecord lRecord;
	
	/**
	 * 変数が初期化されているか
	 */
	@Test
	public void initializeTest() {
		lRecord = new LineRecord();
		assertEquals(lRecord.getUserID(), DEFAULT_VALUE);
		assertEquals(lRecord.getColor(), DEFAULT_VALUE);
		assertEquals(lRecord.getClickTimeStamp(), DEFAULT_VALUE);
		assertEquals(lRecord.getReleaseTimeStamp(), DEFAULT_VALUE);
		if(lRecord.getRecord() == null){
			fail("getRecordが初期化されてません");
		}
		else{
			assertTrue(lRecord.getRecord().size() == 0);
		}
	}
	
}
