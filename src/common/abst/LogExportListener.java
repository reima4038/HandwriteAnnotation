package common.abst;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import common.data.SessionStatus;
import common.util.FileOutput;

public class LogExportListener extends WindowAdapter{
	public void windowClosing(WindowEvent e) {
		// ログの書き込み
		FileOutput.output(SessionStatus.getInstance().getExportRecords());
	}
}
